package iducs.springboot.kchboard.controller;

import iducs.springboot.kchboard.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("")
    public String getHome(HttpSession session){
        if ((session.getAttribute("login")) == null || (session.getAttribute("isadmin")) == null){
            return "home/index";
        }
        else if (((Member) session.getAttribute("login")).getBlock() == 1) {
            return "members/memberlimit";
        }
        else {
            return "home/index";
        }
    }
}
