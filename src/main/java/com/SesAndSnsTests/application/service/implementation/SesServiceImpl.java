package com.SesAndSnsTests.application.service.implementation;

import com.SesAndSnsTests.application.service.CreateCalendarEventService;
import com.SesAndSnsTests.application.service.SesService;
import com.SesAndSnsTests.domain.model.Email;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.util.Date;

@Service
public class SesServiceImpl implements SesService {
    private JavaMailSender javaMailSender;
    private CreateCalendarEventService createCalendarEventService;

    @Autowired
    public SesServiceImpl(JavaMailSender javaMailSender, CreateCalendarEventService createCalendarEventService) {
        this.javaMailSender = javaMailSender;
        this.createCalendarEventService = createCalendarEventService;
    }

    @Override
    public void sendEmailWithAttachment(Email email) {
        try {
            sendSimpleEmail(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendSimpleEmail(Email email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setPriority(1);

        BodyPart messageBodyPartText = new MimeBodyPart();
        messageBodyPartText.setText(email.getText());


        DataSource source;
        Calendar calendar = createCalendarEventService.createEventByDate();
        InputStream targetStream = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, out);
            source = new ByteArrayDataSource(new ByteArrayInputStream(out.toByteArray()), "text/calendar");

            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("event.ics");

            multipart.addBodyPart(messageBodyPartText);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            message.saveChanges();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (targetStream != null) {
                try {
                    targetStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        javaMailSender.send(message);
    }
}
