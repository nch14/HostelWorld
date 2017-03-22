package cn.chenhaonee.hostelWorld.controller;

import cn.chenhaonee.hostelWorld.domain.InnDetailClient;
import cn.chenhaonee.hostelWorld.domain.RoomDetailClient;
import cn.chenhaonee.hostelWorld.service.InnOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/19.
 */
@Controller
@RequestMapping(value = "/innOwner")
public class InnOwnerController {

    @Autowired
    private InnOwnerService innOwnerService;

    /**
     * 客栈加盟申请页面
     *
     * @return
     */
    @RequestMapping(value = "/applyPage")
    public String applyPageForAInn() {
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "/applyConfirm", method = RequestMethod.GET)
    public boolean applyRequestForAInn(@RequestParam(value = "hostelName") String hostelName,
                                       @RequestParam(value = "hostelTel") String hostelTel,
                                       @RequestParam(value = "hostelAdd") String hostelAdd,
                                       @RequestParam(value = "hostelDesc") String hostelDesc,
                                       @RequestParam(value = "hostelEmail") String hostelEmail,
                                       @RequestParam(value = "wideBed") String wideBed,
                                       @RequestParam(value = "doubleBed") String doubleBed,
                                       @RequestParam(value = "seaBed") String seaBed,
                                       @RequestParam(value = "hostelCard") String hostelCard,
                                       HttpSession session) {
        String passwordHash = (String) session.getAttribute("passwordHash");
        String username = (String) session.getAttribute("username");
        innOwnerService.addNewOwner(username, passwordHash, hostelName, hostelTel, hostelAdd, hostelDesc, hostelEmail,
                wideBed, doubleBed, seaBed, hostelCard);
        session.removeAttribute("passwordHash");
        return true;
    }

    @RequestMapping(value = "/home")
    public String innHomePage() {
        return "/hostelToday";
    }

    /**
     * 发布计划页面
     *
     * @return
     */
    @RequestMapping(value = "/makePlanPage")
    public String makePlanPage() {
        return "";
    }

    @RequestMapping(value = "/makePlanConfirm", method = RequestMethod.POST)
    public String makePlanRequest() {
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "/checkIn/1")
    public boolean checkInWithBooking(@RequestParam(value = "no") String no,
                                      @RequestParam(value = "name") List<String> name,
                                      @RequestParam(value = "payInCard") boolean payInCard) {
        innOwnerService.checkIn(no, name, payInCard);
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/checkIn/two")
    public String checkInWithoutBooking(@RequestParam(value = "fromDate") String fromDate,
                                        @RequestParam(value = "toDate") String toDate,
                                        @RequestParam(value = "roomName") String roomName,
                                        @RequestParam(value = "memberUserName", required = false) String memberUserName,
                                        @RequestParam(value = "payInCard", required = false) boolean payInCard,
                                        @RequestParam(value = "name") String name, HttpSession session) {
        String username = (String) session.getAttribute("username");
        Long roomId = innOwnerService.findRoomId(username, roomName);
        String inn = innOwnerService.findInnId(username);

        String[] names = name.split(";");
        List<String> nameList = Arrays.asList(names);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date from = null;
        Date to = null;
        try {
            from = sdf.parse(fromDate);
            to = sdf.parse(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String orderId = innOwnerService.checkIn(username, memberUserName, inn, roomId, from, to, nameList, payInCard);
        return orderId;
    }

    @ResponseBody
    @RequestMapping(value = "/checkOut")
    public void checkOut(@RequestParam(value = "no") String no) {
        innOwnerService.checkOut(no);
    }

    /**
     * 获取可入住的房型
     *
     * @param type
     * @param fromDate
     * @param toDate
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/roomName")
    public List<String> getRoomName(@RequestParam(value = "type") String type,
                                    @RequestParam(value = "fromDate") String fromDate,
                                    @RequestParam(value = "toDate") String toDate,
                                    HttpSession session) {
        String username = (String) session.getAttribute("username");
        try {
            return innOwnerService.findRooms(username, type, fromDate, toDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/forPrice")
    public List<Double> findPrice(HttpSession session) {
        String username = (String) session.getAttribute("username");
        return innOwnerService.findPrice(username);
    }

    @ResponseBody
    @RequestMapping(value = "/updatePrice")
    public boolean updatePrice(@RequestParam(value = "widePrice") double widePrice,
                               @RequestParam(value = "doublePrice") double doublePrice,
                               @RequestParam(value = "seaPrice") double seaPrice,
                               HttpSession session) {
        String username = (String) session.getAttribute("username");
        innOwnerService.setPrice(username, widePrice, doublePrice, seaPrice);
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/forInfo")
    public InnDetailClient findInfo(HttpSession session) {
        String username = (String) session.getAttribute("username");
        return innOwnerService.getInfo(username);
    }

    @ResponseBody
    @RequestMapping(value = "/updateInfo", method = RequestMethod.GET)
    public boolean updateInfo(@RequestParam(value = "hostelName") String hostelName,
                              @RequestParam(value = "hostelTel") String hostelTel,
                              @RequestParam(value = "hostelAdd") String hostelAdd,
                              @RequestParam(value = "hostelDesc") String hostelDesc,
                              @RequestParam(value = "hostelEmail") String hostelEmail,
                              @RequestParam(value = "wideBed") String wideBed,
                              @RequestParam(value = "doubleBed") String doubleBed,
                              @RequestParam(value = "seaBed") String seaBed,
                              @RequestParam(value = "hostelCard") String hostelCard,
                              HttpSession session) {
        String username = (String) session.getAttribute("username");
        innOwnerService.updateInfo(username, hostelName, hostelTel, hostelAdd, hostelDesc, hostelEmail,
                wideBed, doubleBed, seaBed, hostelCard);
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public List<RoomDetailClient> updateInfo(HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<RoomDetailClient> rooms = innOwnerService.findRooms(username);
        return rooms;
    }


}
