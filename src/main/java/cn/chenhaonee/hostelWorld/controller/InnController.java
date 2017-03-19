package cn.chenhaonee.hostelWorld.controller;

import cn.chenhaonee.hostelWorld.domain.InnForClient;
import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import cn.chenhaonee.hostelWorld.model.Inn.Room;
import cn.chenhaonee.hostelWorld.service.InnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/20.
 */
@RequestMapping(value = "/inn")
@RestController
public class InnController {

    @Autowired
    private InnService innService;

    @RequestMapping(value = "/list", produces = "application/json")
    public List<InnForClient> getInnList() {
        return innService.getInnList();
    }

    @RequestMapping(value = "/roomList", produces = "application/json")
    public List<Room> getRoomList(@RequestParam(value = "inn")Long inn,
                                  @RequestParam(value = "start")String start,
                                  @RequestParam(value = "end")String end){

    }
}