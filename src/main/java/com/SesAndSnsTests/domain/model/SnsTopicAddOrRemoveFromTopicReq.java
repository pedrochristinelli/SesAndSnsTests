package com.SesAndSnsTests.domain.model;

public class SnsTopicAddOrRemoveFromTopicReq {
    private String number;
    private String topic;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public SnsTopicAddOrRemoveFromTopicReq() {
    }

    public SnsTopicAddOrRemoveFromTopicReq(String number, String topic) {
        this.number = number;
        this.topic = topic;
    }
}
