package com.ecom.Shopping.Cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @GetMapping("/product")
    public String product() {
        return "product";
       
}
    @GetMapping("/view_product")
    public String view_product() {
        return "view_product";
}
}
