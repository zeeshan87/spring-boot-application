package com.sbapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
/**
 * Main Controller to redirect to Swagger-UI
 */
public class HomeController
{

    @RequestMapping("/")
    public String home()
    {
        return "redirect:swagger-ui.html";
    }

}
