package cn.chenhaonee.hostelWorld.controller;

import cn.chenhaonee.hostelWorld.dao.MemberRepository;
import cn.chenhaonee.hostelWorld.domain.PersonalInfo;
import cn.chenhaonee.hostelWorld.domain.RoomForClient;
import cn.chenhaonee.hostelWorld.domain.TTO;
import cn.chenhaonee.hostelWorld.exception.NoEnoughBalanceException;
import cn.chenhaonee.hostelWorld.exception.NoSuchUserException;
import cn.chenhaonee.hostelWorld.exception.NoSuchVisaCardException;
import cn.chenhaonee.hostelWorld.exception.VisaCardCheckFailureException;
import cn.chenhaonee.hostelWorld.model.Inn.Room;
import cn.chenhaonee.hostelWorld.model.Member.Member;
import cn.chenhaonee.hostelWorld.model.common.OrderBill;
import cn.chenhaonee.hostelWorld.service.MemberCardService;
import cn.chenhaonee.hostelWorld.service.MemberService;
import cn.chenhaonee.hostelWorld.service.OrderService;
import com.sun.org.apache.bcel.internal.generic.DASTORE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.dc.pr.PRError;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by carlos on 2017/3/14.
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberCardService memberCardService;

    @RequestMapping(method = RequestMethod.POST, value = "/join")
    public String becomeAMember(@RequestParam(value = "cardNum") String cardNum,
                                @RequestParam(value = "validDate") String validDate,
                                @RequestParam(value = "cvv") String cvv,
                                @RequestParam(value = "money") int money,
                                ModelAndView modelAndView,
                                HttpSession session) {
        if (money<1000)
            return "redirect:/join/joinAsMember";
        String passwordHash = (String) session.getAttribute("passwordHash");
        String username = (String) session.getAttribute("username");
        try {
            Member member = memberService.addNewMember(username, passwordHash, cardNum, validDate, cvv, money);
        } catch (VisaCardCheckFailureException e) {
            e.printStackTrace();
        } catch (NoSuchVisaCardException e) {
            e.printStackTrace();
        } catch (NoEnoughBalanceException e) {
            e.printStackTrace();
        }
        session.removeAttribute("passwordHash");
        return "redirect:/member/customerIndex";
    }

    @RequestMapping(value = "/customerIndex")
    public String indexPage() {
        return "/customerIndex";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/vitiate")
    public String stopMembership(HttpSession session) {
        String username = (String) session.getAttribute("username");
        try {
            memberService.vitiateMembership(username);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}/charge")
    public String addMoneyToAccount(@PathVariable(value = "id") String id,
                                    @RequestParam(value = "money") double money) {
        try {
            memberService.chargeMoney(id, money);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        } catch (NoEnoughBalanceException e) {
            e.printStackTrace();
        }
        return "**上一个界面**";
    }

    @RequestMapping(value = "/checkForRoom")
    public String checkForRoom() {
        return "**寻找房间的界面";
    }

    /**
     * 订房
     *
     * @param hotelId
     * @param roomId
     * @param inDate
     * @param outDate
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bookOne", produces = "application/json")
    public boolean bookOne(@RequestParam(value = "hotelId") String hotelId,
                           @RequestParam(value = "roomId") Long roomId,
                           @RequestParam(value = "inDate") String inDate,
                           @RequestParam(value = "outDate") String outDate,
                           HttpSession session) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
        Date in = null;
        Date out = null;
        try {
            in = simpleDateFormat.parse(inDate);
            out = simpleDateFormat.parse(outDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String username = (String) session.getAttribute("username");
        orderService.makeAnOrder(username, hotelId, roomId, in, out);
        return true;
    }

/*    @RequestMapping(value = "/myOrderPage")
    public String myOrderPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<OrderBill> orderBills = memberService.getAllMyOrders(username);
        model.addAttribute("orderBills", orderBills);
        return "/customerReservation";
    }

    @RequestMapping(value = "/myStaticsPage")
    public String myStaticsPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<OrderBill> orderBills = memberService.getAllMyOrders(username);
        model.addAttribute("orderBills", orderBills);
        return "/customerHistory";
    }*/

    /**
     * 查看订单
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/myOrderData")
    public List<OrderBill> myOrderPage(HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<OrderBill> orderBills = memberService.getAllMyOrders(username);
        orderBills = orderBills.stream().map(orderBill -> {
            Room room = orderBill.getRoom();
            orderBill.setRoom(new RoomForClient(room.getId(), room.getRoomName(), room.getRoomType(), 0l));
            return orderBill;
        }).collect(Collectors.toList());
        return orderBills;
    }

    @ResponseBody
    @RequestMapping(value = "/myOrderData/roomType")
    public List<TTO> myRoomType(HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<OrderBill> orderBills = memberService.getAllMyOrders(username);

/*        List<String> rooms = orderBills.stream().map(orderBill -> orderBill.getRoom().getRoomType()).collect(Collectors.toList());
        List<TTO> ttos = parse(rooms);
        return ttos;*/
        List<TTO> ttos = new ArrayList<>();
        ttos.add(new TTO("大床房", 1));
        ttos.add(new TTO("双产", 2));
        ttos.add(new TTO("杀人啦", 3));
        return ttos;
    }

    @ResponseBody
    @RequestMapping(value = "/myOrderData/innDiff")
    public List<TTO> myRoomWhere(HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<OrderBill> orderBills = memberService.getAllMyOrders(username);

      /*  List<String> inns = orderBills.stream().map(orderBill -> orderBill.getInn()).collect(Collectors.toList());
        List<TTO> ttos = parse(inns);
        return ttos;*/

        List<TTO> ttos = new ArrayList<>();
        ttos.add(new TTO("京城1", 1));
        ttos.add(new TTO("京城2", 2));
        ttos.add(new TTO("京城3", 3));
        ttos.add(new TTO("京城4", 14));
        return ttos;
    }

    @ResponseBody
    @RequestMapping(value = "/myOrderData/orders")
    public List<TTO> myRoomMonth(HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<OrderBill> orderBills = memberService.getAllMyOrders(username);
/*        // TODO: 2017/3/20 月份
        List<String> rooms = orderBills.stream().map(orderBill -> orderBill.getRoom().getRoomType()).collect(Collectors.toList());
        List<TTO> ttos = parse(rooms);*/

        List<TTO> ttos = new ArrayList<>();
        ttos.add(new TTO("1月", 1));
        ttos.add(new TTO("2月", 1));
        ttos.add(new TTO("3月", 1));
        ttos.add(new TTO("4月", 1));
        ttos.add(new TTO("5月", 1));
        ttos.add(new TTO("6月", 1));
        ttos.add(new TTO("7月", 1));
        ttos.add(new TTO("8月", 1));
        ttos.add(new TTO("9月", 1));
        ttos.add(new TTO("10月", 1));
        ttos.add(new TTO("11月", 1));
        ttos.add(new TTO("12月", 1));
        return ttos;
    }

    /**
     * 查看个人信息
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/info")
    public PersonalInfo info(HttpSession session) {
        String username = (String) session.getAttribute("username");
        Member member = memberService.findOne(username);
        //String cardNum, String level, int marks, double costTotal, double balance, String visaNum
        PersonalInfo personalInfo = new PersonalInfo(member.getMemberCard().getId(), memberCardService.getLevel(member.getMemberCard().getSumCost()),
                memberCardService.getDiscountInString(member.getMemberCard().getSumCost()),
                member.getMemberCard().getCurrentMarks(),
                member.getMemberCard().getSumCost(), member.getMemberCard().getBalance(), member.getVisaCard().getCardNum());
        return personalInfo;
    }

    /**
     * 更换银行卡
     *
     * @param cardNum
     * @param session
     */
    @ResponseBody
    @RequestMapping(value = "/updateCard")
    public void info(@RequestParam(value = "cardNum") String cardNum,
                     HttpSession session) {
        String username = (String) session.getAttribute("username");
        memberService.updateCardNum(username, cardNum);
    }


    private List<TTO> parse(List<String> list) {
        List<TTO> ttos = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            String s = list.get(i);
            int count = 0;
            for (int j = i + 1; j < list.size(); j++) {
                String next = list.get(j);
                if (s.equals(next)) {
                    count++;
                    list.remove(j);
                    j--;
                }
                ttos.add(new TTO(s, count));
            }
        }
        return ttos;
       /* int num = 0;
        for (String s : list) {
            if (s.equals(raw))
                num++;
        }
        return num;*/
    }
}
