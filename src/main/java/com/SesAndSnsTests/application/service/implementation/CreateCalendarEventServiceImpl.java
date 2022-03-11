package com.SesAndSnsTests.application.service.implementation;

import com.SesAndSnsTests.application.service.CreateCalendarEventService;
import com.SesAndSnsTests.domain.model.IcsEvent;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class CreateCalendarEventServiceImpl implements CreateCalendarEventService {

    public Calendar createEventByDate(IcsEvent icsEvent) {
        //Create an iCal4J Calendar
        Calendar c = new Calendar();

        //"EN" is the language.
        c.add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        c.add(Version.VERSION_2_0);
        c.add(CalScale.GREGORIAN);

        c.add(createSingleEvent(icsEvent.getDate(), icsEvent.getTimeBoxInHours(), icsEvent.getSummary(), icsEvent.getDescription()));
        return c;
    }

    private VEvent createSingleEvent(Date date, int timeBox, String summary, String description) {
        //Create a new calendar event that starts on 5th March 2021 at midday Australian Eastern Daylight Savings Time and goes for 1 hour.
        VEvent vEvent = new VEvent();
        java.time.ZonedDateTime now = java.time.ZonedDateTime.now();

        //Create a unique ID for the event
        String uidTimestamp = java.time.format.DateTimeFormatter
                .ofPattern("yyyyMMdd'T'hhmmssXX")
                .format(now);
        //In the real world this could be a number from a generated sequence:
        String uidSequence = "/" + (int) Math.ceil(Math.random() * 1000);
        vEvent.add(new Uid(uidTimestamp + uidSequence));

        //Add a start datetime and a duration
        TimeZoneRegistry tzReg = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = tzReg.getTimeZone(TimeZone.getDefault().getID());
        try {
            String formattedEndpointDate = new SimpleDateFormat("yyyyMMdd'T'hhmmssXX").format(date);
            vEvent.add(new DtStart(String.valueOf(new DateTime(formattedEndpointDate, timezone))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        vEvent.add(new Duration(java.time.Duration.ofHours(timeBox)));

        //Add title and description
        vEvent.add(new Summary(summary));
        vEvent.add(new Description(description));
        return vEvent;
    }

}
