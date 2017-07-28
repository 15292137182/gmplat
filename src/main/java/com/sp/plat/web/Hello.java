package com.sp.plat.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Went on 2017/7/25.
 */
@Controller
@RequestMapping("/")
public class Hello {

    @RequestMapping("/helloworld.do")
    public String hello(){
        System.out.println("hello world");
        return "index";
    }
}
