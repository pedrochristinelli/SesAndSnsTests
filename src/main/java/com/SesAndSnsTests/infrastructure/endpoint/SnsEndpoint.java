package com.SesAndSnsTests.infrastructure.endpoint;

import com.SesAndSnsTests.application.service.SnsService;
import com.SesAndSnsTests.domain.model.SnsCreateTopicEndpointReq;
import com.SesAndSnsTests.domain.model.SnsSmsEndpointReq;
import com.SesAndSnsTests.domain.model.SnsTopicEndpointReq;
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

    @Operation(summary = "Send a single sms message")
    @PostMapping("/sms")
    public void sendSnsSmsMessage(@RequestBody SnsSmsEndpointReq message){
        LOGGER.info("Starting to process the endpoint '{}' with the method '{}' with the following request body: {}", "/ses", "POST", message);
        snsService.sendSmsMessage(message.getMessage(), message.getPhoneNumber());
    }

    @Operation(summary = "Send an sns topic message")
    @PostMapping("/topic")
    public void sendSnsTopicMessage(@RequestBody SnsTopicEndpointReq message){
        LOGGER.info("Starting to process the endpoint '{}' with the method '{}' with the following request body: {}", "/ses", "POST", message);
        snsService.sendSmsTopicMessage(message.getMessage(), message.getTopic());
    }

    @Operation(summary = "Create an sns topic")
    @PostMapping("/create-topic")
    public void createSnsTopic(@RequestBody SnsCreateTopicEndpointReq req){
        LOGGER.info("Starting to process the endpoint '{}' with the method '{}' with the following request body: {}", "/ses", "POST", req);
        snsService.createSmsTopic(req.getTopicName(), req.getTopicEndpoints());
    }
}

