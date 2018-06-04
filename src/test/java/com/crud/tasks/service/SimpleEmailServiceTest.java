package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MailCreatorService mailCreatorService;

    //@Ignore
    @Test
    public void shouldSendEmail() {
        // Given
        Mail mail = new Mail("test@test.com", TrelloService.SUBJECT, "Test Message");
        MimeMessagePreparator mimeMailMessage = simpleEmailService.createMimeMessage(mail, TemplateSelector.TRELLO_CARD_MAIL);

        // When
        javaMailSender.send(mimeMailMessage);

        // Then
        verify(javaMailSender, times(1)).send(mimeMailMessage);

    }
}