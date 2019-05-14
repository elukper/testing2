package com.testing.two.application.service;

import com.testing.two.application.components.BaseComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    @Autowired
    private BaseComponent baseComponent;

    public String getH1Message() {
        return baseComponent.getH1Text();
    }

    public String getH3Message() {
        return baseComponent.getH3Text();
    }
}
