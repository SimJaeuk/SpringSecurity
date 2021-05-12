package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @RequestMapping("/")   // /에 대한 url 호출 처리
    public @ResponseBody String root() throws Exception{
        return "Security (2)";
    }

    @RequestMapping("/guest/welcome") // /guest에 대한 url 호출 처리
    public String welcome1() {

        return "guest/welcome1";
    }
    
    @RequestMapping("/member/welcome") // /member에 대한 url 호출 처리
    public String welcome2() {

        return "member/welcome2";
    }
    
    @RequestMapping("/admin/welcome") // /admin에 대한 url 호출 처리
    public String welcome3() {
        
        return "admin/welcome3";
    }
    
    @RequestMapping("/loginForm")
    public String loginForm() {
    	return "security/loginForm";
    }
    
}
