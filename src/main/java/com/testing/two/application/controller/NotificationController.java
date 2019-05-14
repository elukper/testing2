package com.testing.two.application.controller;

import com.testing.two.application.dto.PartnerNotificationRequest;
import com.testing.two.application.dto.PartnerNotificationResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @RequestMapping(value = "/accept/napster/notification", method = RequestMethod.POST)
    public PartnerNotificationResponse acceptRequest(@RequestBody PartnerNotificationRequest request) {
        PartnerNotificationResponse response = new PartnerNotificationResponse();

        response.setStatus(200);
        response.setMessage("Received request, type "+request.getType()+" for GUID "+request.getGuid());
        return response;
    }
}
