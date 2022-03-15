package com.SesAndSnsTests.domain.model;

public class SesWithCalendarEndpointReq {
    private Email email;
    private IcsEvent calendar;

    public SesWithCalendarEndpointReq() {
    }

    public SesWithCalendarEndpointReq(Email email, IcsEvent calendar) {
        this.email = email;
        this.calendar = calendar;
    }

    public IcsEvent getCalendar() {
        return calendar;
    }

    public void setCalendar(IcsEvent calendar) {
        this.calendar = calendar;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public SesWithCalendarEndpointReq(Email email) {
        this.email = email;
    }
}
