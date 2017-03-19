package cn.chenhaonee.hostelWorld.controller;

import cn.chenhaonee.hostelWorld.model.User;
import cn.chenhaonee.hostelWorld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by nichenhao on 2017/3/13.
 */
@Controller
@RequestMapping(value = "/join")
public class JoinController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "")
    public String joinPage(HttpSession httpSession) {
        return "/join";
    }

    @RequestMapping(value = "/checkForUsername", produces = "application/json")
    public Boolean checkForUsername(@RequestParam(value = "username") String username) {
        boolean result = userService.userIsExisting(username);
        return result;
    }

    @RequestMapping(value = "/common")
    public ModelAndView join(@RequestParam(value = "username") String username,
                       @RequestParam(value = "password") String password,
                       @RequestParam(value = "passwordConfirm") String passwordConfirm,
                       HttpSession session,
                       Model model) {
        boolean result = userService.userIsExisting(username);
        if (result){
            session.setAttribute("errorJoin",true);
            session.setAttribute("errorJoinType","该用户名已存在，请重试");
            return new ModelAndView("redirect:/join");
        }

        String passwordHash;
        if (password.equals(passwordConfirm)) {
            passwordHash = new BCryptPasswordEncoder().encode(password);
            session.setAttribute("username", username);
            session.setAttribute("passwordHash", passwordHash);
            session.removeAttribute("errorJoin");
            session.removeAttribute("errorJoinType");
            return new ModelAndView("redirect:/join/chooseType");
        }else {
            session.setAttribute("errorJoin",true);
            session.setAttribute("errorJoinType","密码不一致，请重试");
            return new ModelAndView("redirect:/join");
        }
    }
    @RequestMapping(value = "/chooseType")
    public String chooseType(){
        return "/registerChoose";
    }

    @RequestMapping(value = "/joinAsMember")
    public String joinAsMember(){
        return "/joinAsMember";
    }
}
