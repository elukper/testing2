package com.testing.two.application.controller;

import com.testing.two.application.dto.JacksonDto;
import com.testing.two.application.service.BaseService;
import com.testing.two.application.service.watchservice.Partner;
import com.testing.two.application.service.watchservice.PartnerPropertyResolver;
import com.testing.two.basic.schematype.XmlDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Basic REST Controller
 * Implements web hooks that return JSON or XML
 * The @RestController combines the @Controller and @ResponseBody annotation, so @ResponseBody doesn't have to be added to each method
 */
@RestController
@RequestMapping(value = "/jackson")
public class BasicRestController {

    @Autowired
    private BaseService baseService;

    @Autowired
    private PartnerPropertyResolver partnerPropertyResolver;

    /**
     * An API controller method which returns a JSON response
     * The "produces" in GetMapping defines that this controller method will return a JSON ResponseBody
     * @return {@link JacksonDto}
     */
    @GetMapping(value = URLConstants.JSON, produces = MediaType.APPLICATION_JSON_VALUE)
    public JacksonDto getSomeJson() {
        JacksonDto result = new JacksonDto();
        result.setRequest(baseService.getH1Message());
        result.setReason(baseService.getH3Message());

        return result;
    }

    /**
     * An API controller method which returns an XML response
     * The "produces" in GetMapping defines that this controller method will return an XML ResponseBody
     * @return
     */
    @GetMapping(value = URLConstants.XML, produces = MediaType.APPLICATION_XML_VALUE)
    public XmlDto getSomeXML() {
        XmlDto result = new XmlDto();
        result.setRequest(baseService.getH1Message());
        result.setReason(baseService.getH3Message());

        return result;
    }

    /**
     * An API controller method which returns an XML or JSON response
     * There is no "produces" in GetMapping, but the request can contain the Accept header which will define the necessary response format
     * Default is "application/xml" (if Accept header is not included)
     * The generated XML class is used as a response, but if "application/json" is passed, the Jackson converter still knows how to convert this class to a JSON response
     * @return
     */
    @GetMapping(value = URLConstants.XML_OR_JSON)
    public XmlDto getSomeResponse() {
        XmlDto result = new XmlDto();
        result.setRequest(baseService.getH1Message());
        result.setReason(baseService.getH3Message());

        return result;
    }

    @PostMapping(value = URLConstants.JSON, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public XmlDto invertFields(@RequestBody XmlDto request) {
        XmlDto response = new XmlDto();
        response.setReason(request.getRequest());
        return response;
    }

    @GetMapping(value = URLConstants.PARTNER_DATA)
    public Map<String,Partner> partners() {
        return partnerPropertyResolver.getPartnerMap();
    }
}
