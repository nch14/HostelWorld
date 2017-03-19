package cn.chenhaonee.hostelWorld.controller;

import cn.chenhaonee.hostelWorld.exception.NoEnoughBalanceException;
import cn.chenhaonee.hostelWorld.exception.NoSuchUserException;
import cn.chenhaonee.hostelWorld.exception.NoSuchVisaCardException;
import cn.chenhaonee.hostelWorld.exception.VisaCardCheckFailureException;
import cn.chenhaonee.hostelWorld.model.Member.Member;
import cn.chenhaonee.hostelWorld.service.MemberService;
import cn.chenhaonee.hostelWorld.service.VisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by carlos on 2017/3/14.
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private VisaService visaService;

    @RequestMapping(method = RequestMethod.POST, value = "/join")
    public String becomeAMember(@RequestParam(value = "cardNum") String cardNum,
                                @RequestParam(value = "validDate") String validDate,
                                @RequestParam(value = "cvv") String cvv,
                                @RequestParam(value = "money") int money,
                                ModelAndView modelAndView,
                                HttpSession session) {
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
    public String indexPage(){
        return "/customerIndex";
    }


    @RequestMapping(method = RequestMethod.GET, value = "{id}/vitiate")
    public String stopMembership(@PathVariable(value = "id") String id) {
        try {
            memberService.vitiateMembership(id);
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

    @RequestMapping(value = "{id}/checkForRoom")
    public String checkForRoom() {
        return "**寻找房间的界面";
    }

    @RequestMapping(value = "{id}/bookOne", produces = "application/json")
    public String bookOne(@PathVariable(value = "id") String id,
                          @RequestParam(value = "hotelId") String hotelId,
                          @RequestParam(value = "roomType") String roomType,
                          @RequestParam(value = "inDate") Date inDate,
                          @RequestParam(value = "outDate") Date outDate) {
        return "";
    }

}
