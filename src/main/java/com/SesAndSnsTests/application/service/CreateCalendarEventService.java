package com.SesAndSnsTests.application.service;

import com.SesAndSnsTests.domain.model.IcsEvent;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

import java.util.Date;

public interface CreateCalendarEventService {
    Calendar createEventByDate();

    VEvent createSingleEvent(Date date);
}
