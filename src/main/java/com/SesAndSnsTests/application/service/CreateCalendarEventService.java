package com.SesAndSnsTests.application.service;

import com.SesAndSnsTests.domain.model.IcsEvent;
import net.fortuna.ical4j.model.Calendar;

public interface CreateCalendarEventService {
    Calendar createEventByDate(IcsEvent icsEvent);
}
