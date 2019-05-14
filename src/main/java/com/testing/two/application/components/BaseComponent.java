package com.testing.two.application.components;

import org.springframework.stereotype.Component;

@Component
public class BaseComponent {

    public String getH3Text() {
        return "We have cookies . . .";
    }

    public String getH1Text() {
        return "Come to the Dark Side";
    }
}
