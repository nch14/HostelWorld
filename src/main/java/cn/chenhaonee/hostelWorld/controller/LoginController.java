package cn.chenhaonee.hostelWorld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by nichenhao on 2017/3/13.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "SignUp")
    public String signUp(@RequestParam(value = "userName")String userName,
                         @RequestParam(value = "password")String password,
                         @RequestParam(value = "VisaNumber")String VisaNumber,
                         @RequestParam(value = "isBoy",defaultValue = "true")Boolean isBoy,
                         Model model){
        return null;
    }
}
