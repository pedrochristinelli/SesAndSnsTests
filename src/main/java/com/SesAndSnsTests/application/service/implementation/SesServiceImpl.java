package com.SesAndSnsTests.application.service.implementation;

import com.SesAndSnsTests.application.service.CreateCalendarEventService;
import com.SesAndSnsTests.application.service.SesService;
import com.SesAndSnsTests.domain.model.Email;
import com.SesAndSnsTests.domain.model.IcsEvent;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class SesServiceImpl implements SesService {
    private final JavaMailSender javaMailSender;
    private final CreateCalendarEventService createCalendarEventService;

    @Autowired
    public SesServiceImpl(JavaMailSender javaMailSender, CreateCalendarEventService createCalendarEventService) {
        this.javaMailSender = javaMailSender;
        this.createCalendarEventService = createCalendarEventService;
    }

    @Override
    public void sendEmailWithAttachment(Email email, IcsEvent icsEvent) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setPriority(1);

        BodyPart messageBodyPartText = new MimeBodyPart();
        messageBodyPartText.setText(email.getText());

        DataSource source;
        Calendar calendar = createCalendarEventService.createEventByDate(icsEvent);

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            calendarOutputter.output(calendar, out);
            source = new ByteArrayDataSource(new ByteArrayInputStream(out.toByteArray()), "text/calendar");

            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPartCalendar = new MimeBodyPart();
            messageBodyPartCalendar.setDataHandler(new DataHandler(source));
            messageBodyPartCalendar.setFileName("event.ics");

            multipart.addBodyPart(messageBodyPartText);
            multipart.addBodyPart(messageBodyPartCalendar);
            message.setContent(multipart);
            message.saveChanges();
        } catch (Exception e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }

    @Override
    public void sendSimpleEmail(Email email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setPriority(1);
        mimeMessageHelper.setText(email.getText());

        javaMailSender.send(message);
    }

    @Override
    public void sendSimpleEmailToMultipleAddresses(Email email, String[] addresses) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setPriority(1);
        mimeMessageHelper.setText(email.getText());

        for (String address: addresses) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
        }

        javaMailSender.send(message);
    }
}
