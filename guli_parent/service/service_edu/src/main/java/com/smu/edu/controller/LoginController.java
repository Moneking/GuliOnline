package com.smu.edu.controller;

import com.smu.utils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/user")
public class LoginController {
    @PostMapping("/login")
    public Result login(){
        return Result.success().data("token","admin");
    }

    @GetMapping("/info")
    public Result info(){
        return Result.success().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
