package com.SesAndSnsTests.domain.model;

public class SesSimpleToMultipleAddressesReq {
    private Email email;
    private String[] addresses;

    public SesSimpleToMultipleAddressesReq(Email email, String[] addresses) {
        this.email = email;
        this.addresses = addresses;
    }

    public SesSimpleToMultipleAddressesReq() {
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }
}
