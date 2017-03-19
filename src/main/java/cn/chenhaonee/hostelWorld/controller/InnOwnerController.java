package cn.chenhaonee.hostelWorld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nichenhao on 2017/3/19.
 */
@Controller
@RequestMapping(value = "/inn")
public class InnOwnerController {

    /**
     * 客栈加盟申请页面
     * @return
     */
    @RequestMapping(value = "/applyPage")
    public String applyPageForAInn(){
        return "";
    }


    @RequestMapping(value = "/applyConfirm",method = RequestMethod.POST)
    public String applyRequestForAInn(){
        return "";
    }

    /**
     * 发布计划页面
     * @return
     */
    @RequestMapping(value = "/makePlanPage")
    public String makePlanPage(){
        return "";
    }

    @RequestMapping(value = "/makePlanConfirm",method = RequestMethod.POST)
    public String makePlanRequest(){
        return "";
    }

}
