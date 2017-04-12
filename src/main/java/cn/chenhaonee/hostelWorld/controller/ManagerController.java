package cn.chenhaonee.hostelWorld.controller;

import cn.chenhaonee.hostelWorld.domain.BinaryData;
import cn.chenhaonee.hostelWorld.domain.TTODouble;
import cn.chenhaonee.hostelWorld.model.Member.Member;
import cn.chenhaonee.hostelWorld.model.common.HostelRequest;
import cn.chenhaonee.hostelWorld.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by nichenhao on 2017/3/21.
 */
@RequestMapping(value = "/manager")
@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "/request")
    public List<HostelRequest> getAllRequests() {
        return managerService.getAllRequests();
    }

    @RequestMapping(value = "/request/confirm")
    public boolean confirm(@RequestParam(value = "id") Long id) {
        managerService.confirm(id);
        return true;
    }

    @RequestMapping(value = "/request/deny")
    public boolean deny(@RequestParam(value = "id") Long id) {
        managerService.deny(id);
        return true;
    }

    @RequestMapping(value = "/statics/roomType")
    public List<BinaryData> roomType() {
        return managerService.roomType();
    }

    @RequestMapping(value = "/statics/innSales")
    public List<BinaryData> innSales() {
        return managerService.innSales();
    }

    @RequestMapping(value = "/allUsers")
    public List<Member> allUsers() {
        return managerService.allUsers();
    }

    @RequestMapping(value = "/moneyToReturn")
    public List<TTODouble> moneyToReturn() {
        return managerService.moneyToReturn();
    }

    @RequestMapping(value = "/moneyToReturn/return")
    public boolean returnMoney(@RequestParam(value = "innId") String innId,
                               @RequestParam(value = "money") double money) {
        managerService.returnMoney(innId, money);
        return true;
    }
}
