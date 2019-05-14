package com.testing.two.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is the global exception handler for controllers. Any unhandled
 */
@ControllerAdvice
public class BasicGlobalExceptionHandler extends AbstractHandlerExceptionResolver {

    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exception");
        modelAndView.addObject("code", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("message", e.getMessage());

        return modelAndView;
    }
}
