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
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    public void sendEmailWithAttachment(Email email, boolean hasCalendar, IcsEvent icsEvent) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setPriority(1);

        if (hasCalendar) {
            BodyPart messageBodyPartText = new MimeBodyPart();
            messageBodyPartText.setText(email.getText());

            DataSource source;
            Calendar calendar = createCalendarEventService.createEventByDate(icsEvent);
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
        } else {
            mimeMessageHelper.setText(email.getText());
        }

        javaMailSender.send(message);
    }
}
