package com.testing.two.application.controller;

import com.testing.two.application.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Basic View resolving controller
 */
@Controller
@RequestMapping("/page")
public class BasicController {

    @Autowired
    private BaseService baseService;

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerException(NullPointerException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exception");
        modelAndView.addObject("code", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("message", "You got a NullPointerException. Bad dev...");

        return modelAndView;
    }

    @GetMapping(value = URLConstants.NEW_PAGE)
    public ModelAndView openNewPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("newpage");

        modelAndView.addObject("h1Message",baseService.getH1Message());
        modelAndView.addObject("h3Message",baseService.getH3Message());

        return modelAndView;
    }
}
