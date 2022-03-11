package com.SesAndSnsTests.application.service;

import com.SesAndSnsTests.domain.model.Email;
import com.SesAndSnsTests.domain.model.IcsEvent;

import javax.mail.MessagingException;

public interface SesService {
    void sendEmailWithAttachment(Email email, boolean hasCalendar, IcsEvent icsEvent) throws MessagingException;
}
