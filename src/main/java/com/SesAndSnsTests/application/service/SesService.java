package com.SesAndSnsTests.application.service;

import com.SesAndSnsTests.domain.model.Email;

public interface SesService {
    void sendEmailWithAttachment(Email email);
}
