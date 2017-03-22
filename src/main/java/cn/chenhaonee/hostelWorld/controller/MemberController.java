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
import cn.chenhaonee.hostelWorld.util.Util;
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
        if (money < 1000)
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

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/charge")
    public boolean addMoneyToAccount(@RequestParam(value = "money") double money, HttpSession session) {
        String username = (String) session.getAttribute("username");
        try {
            memberService.chargeMoney(username, money);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        } catch (NoEnoughBalanceException e) {
            e.printStackTrace();
        }
        return true;
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

    @ResponseBody
    @RequestMapping(value = "/cancelOne", produces = "application/json")
    public boolean cancelOne(@RequestParam(value = "orderId") String orderId) {
        orderService.cancelAnOrder(orderId);
        return true;
    }


    @RequestMapping(value = "/myOrderPage")
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
    }

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
        List<String> roomTypes = orderBills.stream().map(orderBill -> orderBill.getRoom().getRoomType()).collect(Collectors.toList());
        return Util.parse(roomTypes);
    }

    @ResponseBody
    @RequestMapping(value = "/myOrderData/innDiff")
    public List<TTO> myRoomWhere(HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<OrderBill> orderBills = memberService.getAllMyOrders(username);
        List<String> innDiff = orderBills.stream().map(orderBill -> orderBill.getInn()).collect(Collectors.toList());
        return Util.parse(innDiff);
    }

    @ResponseBody
    @RequestMapping(value = "/myOrderData/orders")
    public List<TTO> myRoomMonth(HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<OrderBill> orderBills = memberService.getAllMyOrders(username);
        List<String> orders = orderBills.stream().map(orderBill -> {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
            return sdf.format(orderBill.getArrivalDate());
        }).collect(Collectors.toList());
        return Util.parse(orders);
    }

    /**
     * 查看个人信息
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/info")
    public PersonalInfo info(HttpSession session,
                             @RequestParam(value = "username", required = false) String username) {
        if (username == null)
            username = (String) session.getAttribute("username");
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


}
