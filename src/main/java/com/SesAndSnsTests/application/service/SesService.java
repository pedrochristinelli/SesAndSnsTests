package com.SesAndSnsTests.application.service;

import com.SesAndSnsTests.domain.model.Email;
import com.SesAndSnsTests.domain.model.IcsEvent;

import javax.mail.MessagingException;

public interface SesService {
    void sendEmailWithAttachment(Email email, IcsEvent icsEvent) throws MessagingException;

    void sendSimpleEmail(Email email) throws MessagingException;

    void sendSimpleEmailToMultipleAddresses(Email email, String[] addresses) throws MessagingException;
}
