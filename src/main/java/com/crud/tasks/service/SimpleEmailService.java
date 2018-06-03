package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import static com.crud.tasks.service.TemplateSelector.SCHEDULED_MAIL;
import static com.crud.tasks.service.TemplateSelector.TRELLO_CARD_MAIL;


@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail, TemplateSelector template){
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail, template));
            LOGGER.info("Email has been sent.");
        }catch (MailException e){
            LOGGER.error("Failed to process email sending: ", e.getMessage(),e);
        }
    }

    public MimeMessagePreparator createMimeMessage(final Mail mail,TemplateSelector template ){
        return mimeMessage-> {
            MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(getHTMLForTemplate(mail.getMessage() ,template),true);
        };
    }

    public String getHTMLForTemplate(String message, TemplateSelector template){
    if(template==TRELLO_CARD_MAIL){
        return mailCreatorService.buildTrelloCardEmail(message);
    } else if (template==SCHEDULED_MAIL){
        return mailCreatorService.buildTrelloScheduledMail(message);
    }
    return null;
    }


    private SimpleMailMessage createMailMessage(final Mail mail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        return mailMessage;
    }
}
