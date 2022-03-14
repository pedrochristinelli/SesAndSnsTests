package com.SesAndSnsTests.application.service;

public interface SnsService {
    void sendSmsMessage(String message, String phone);

    void sendSmsTopicMessage(String message, String topic);

    void createSmsTopic(String topic, String[] endpoints);

    void addNumberToTopic(String number, String topic);

    void removeNumberFromTopic(String number, String topic);
}
