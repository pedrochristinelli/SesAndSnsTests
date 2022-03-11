package com.SesAndSnsTests.domain.model;

public class SesEndpointReq {
    private Email email;
    private boolean hasCalendarEvent;
    private IcsEvent calendar;

    public SesEndpointReq() {
    }

    public SesEndpointReq(Email email, boolean hasCalendarEvent, IcsEvent calendar) {
        this.email = email;
        this.hasCalendarEvent = hasCalendarEvent;
        this.calendar = calendar;
    }

    public boolean isHasCalendarEvent() {
        return hasCalendarEvent;
    }

    public void setHasCalendarEvent(boolean hasCalendarEvent) {
        this.hasCalendarEvent = hasCalendarEvent;
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

    public SesEndpointReq(Email email) {
        this.email = email;
    }
}
