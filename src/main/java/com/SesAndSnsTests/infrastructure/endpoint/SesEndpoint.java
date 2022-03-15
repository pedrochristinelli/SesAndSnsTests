package com.SesAndSnsTests.infrastructure.endpoint;

import com.SesAndSnsTests.application.service.SesService;
import com.SesAndSnsTests.domain.model.Email;
import com.SesAndSnsTests.domain.model.SesSimpleToMultipleAddressesReq;
import com.SesAndSnsTests.domain.model.SesWithCalendarEndpointReq;
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

    @Operation(summary = "Send an ses message without attachments")
    @PostMapping("/simple")
    public void sendSesSimpleEmail(@RequestBody Email email) throws MessagingException {
        LOGGER.info("Starting to process the endpoint '{}' with the method '{}' with the following request body: {}", "/ses/simple", "POST", email.toString());
        sesService.sendSimpleEmail(email);
    }

    @Operation(summary = "Send an ses message without attachments to a list of emails")
    @PostMapping("/simple-multiple-adresses")
    public void sendSesSimpleEmailToMultipleAddresses(@RequestBody SesSimpleToMultipleAddressesReq sesSimpleToMultipleAddressesReq) throws MessagingException {
        LOGGER.info("Starting to process the endpoint '{}' with the method '{}' with the following request body: {}", "/ses/simple", "POST", sesSimpleToMultipleAddressesReq.toString());
        sesService.sendSimpleEmailToMultipleAddresses(sesSimpleToMultipleAddressesReq.getEmail(), sesSimpleToMultipleAddressesReq.getAddresses());
    }

    @Operation(summary = "Send an ses message")
    @PostMapping("/withCalendar")
    public void sendSesWithCalendar(@RequestBody SesWithCalendarEndpointReq sesWithCalendarEndpointReq) throws MessagingException {
        LOGGER.info("Starting to process the endpoint '{}' with the method '{}' with the following request body: {}", "/ses/withCalendar", "POST", sesWithCalendarEndpointReq);
        sesService.sendEmailWithAttachment(sesWithCalendarEndpointReq.getEmail(), sesWithCalendarEndpointReq.getCalendar());
    }
}
