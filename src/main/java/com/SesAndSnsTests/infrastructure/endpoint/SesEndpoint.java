package com.SesAndSnsTests.infrastructure.endpoint;

import com.SesAndSnsTests.application.service.SesService;
import com.SesAndSnsTests.domain.model.SesEndpointReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping(EndpointMaps.SES_ENDPOINT)
public class SesEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(SesEndpoint.class);
    private SesService sesService;

    @Autowired
    public SesEndpoint(SesService sesService) {
        this.sesService = sesService;
    }

    @Operation(summary = "Send an ses message")
    @PostMapping
    public void sendSes(@RequestBody SesEndpointReq sesEndpointReq) throws MessagingException {
        LOGGER.info("Starting to process the endpoint '{}' with the method '{}' with the following variable: {}", "/ses", "POST", sesEndpointReq.getEmail().toString());
        sesService.sendEmailWithAttachment(sesEndpointReq.getEmail(), sesEndpointReq.isHasCalendarEvent(), sesEndpointReq.getCalendar());
    }
}
