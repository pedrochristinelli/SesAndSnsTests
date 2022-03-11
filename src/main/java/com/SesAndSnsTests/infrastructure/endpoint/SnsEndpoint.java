package com.SesAndSnsTests.infrastructure.endpoint;

import com.SesAndSnsTests.application.service.SnsService;
import com.SesAndSnsTests.domain.model.SnsEndpointReq;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointMaps.SNS_ENDPOINT)
public class SnsEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(SnsService.class);
    private SnsService snsService;

    @Autowired
    public SnsEndpoint(SnsService snsService) {
        this.snsService = snsService;
    }

    @Operation(summary = "Send an sns message")
    @PostMapping
    public void sendSes(@RequestBody SnsEndpointReq message){
        LOGGER.info("Starting to process the endpoint '{}' with the method '{}' with the following variable: {}", "/ses", "POST", message);
        snsService.sendSmsMessage(message.getMessage(), message.getPhoneNumber());
    }
}
