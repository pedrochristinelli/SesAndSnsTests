package com.SesAndSnsTests.domain.model;

public class SnsSmsEndpointReq {
    private String message;
    private String phoneNumber;

    public SnsSmsEndpointReq(String message, String phoneNumber) {
        this.message = message;
        this.phoneNumber = phoneNumber;
    }

    public SnsSmsEndpointReq() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
