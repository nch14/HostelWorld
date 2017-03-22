package cn.chenhaonee.hostelWorld.controller;

import cn.chenhaonee.hostelWorld.model.Inn.InnOwner;
import cn.chenhaonee.hostelWorld.model.Msg;
import cn.chenhaonee.hostelWorld.service.InnOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/13.
 */
@Controller
public class HomeController {

    @Autowired
    private InnOwnerService ownerService;

    @RequestMapping("/")
    public String index(Model model) {
        Msg msg = new Msg("测试标题", "测试内容", "额外信息，只对管理员显示");
        model.addAttribute("msg", msg);
        return "index";
    }


    @RequestMapping("/home")
    public String getHomePage(HttpSession session) {
        SecurityContextImpl context = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        List<SimpleGrantedAuthority> list = (List<SimpleGrantedAuthority>) context.getAuthentication().getAuthorities();
        String role = list.get(0).getAuthority();

        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();
        session.setAttribute("username", username);

        switch (role) {
            case "Member":
                return "redirect:/member/customerIndex";
            case "Inn":
                InnOwner innOwner = ownerService.findOne(username);
                if (innOwner.getInn() == null)
                    return "redirect:/noHostel.html";
                else
                    return "redirect:/innOwner/home";
            case "Manager":
                return "redirect:/managerAppli.html";
            default:
                return "/";
        }

    }
}
