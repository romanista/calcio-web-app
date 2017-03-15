package com.rdiachuk.edu.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by roman.diachuk on 2/21/2017.
 */
@Controller
public class CalcioController {

    @RequestMapping("/")
    public String calcio() {
        return "calcio";
    }

    @RequestMapping("/fragments/{page}")
    public String fragmentsHandler(@PathVariable("page") final String page) {
        return "fragments/" + page;
    }
}
