package ua.pt.tqs.HomeWorkTQS.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebQualityAirController {
    @GetMapping("/home")
    public String showHome() {
        return "home";
    }
}
