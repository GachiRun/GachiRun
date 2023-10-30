package com.gachi.gachirunpj.controller;

import com.gachi.gachirunpj.auth.PrincipalDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping
public class IndexController {

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("message", "Hello 되니?");
        } else {
            model.addAttribute("message", "Hello" + principal.getName());
        }

        return "loginForm.html";
    }

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("message", "Info");
        return "info";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", "Hello" + principal.getName());
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello Admin, " + principal.getName());
        return "admin";
    }

    //회원가입//
    @GetMapping("/signUp")
    public String signUp() {
        return "signUp.html";
    }

    //아이디, 비밀번호 찾기 뷰 출력
    @GetMapping("/findIdPwForm")
    public String findIdPwForm()
    {
        return "findIdPwForm.html";
    }

    @GetMapping("/loginSuccess")
    public String login(@AuthenticationPrincipal PrincipalDetails principal, HttpSession session,Model model) {
        session.setAttribute("EmpDto", principal.getEmpDto()); // 세션 적용
       model.addAttribute("EmpDto", principal.getEmpDto());
        return "/Main";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "loginForm.html";
    }

    @RequestMapping("/loginFail")
    public String loginFail(Model model) {
        model.addAttribute("msg","로그인에 실패하셨습니다.");
        return "loginForm.html";
    }



}
