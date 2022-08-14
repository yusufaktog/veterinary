package com.aktog.yusuf.veterinary.controller;


import com.aktog.yusuf.veterinary.dto.request.create.CreateLoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/${api.version}")
public class LoginController {

    @Value("${api.version}")
    private String apiVersion;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("owner", new CreateLoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("createLoginRequest") CreateLoginRequest createLoginRequest) {
        return "redirect:/" + apiVersion + "/owner";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/" + apiVersion;
    }

}
