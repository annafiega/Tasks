package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.service.TemplateSelector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)

public class EmailSchedulerTest {

    @InjectMocks
    EmailScheduler emailScheduler;

    @Mock
    SimpleEmailService simpleEmailService;

    @Mock
    AdminConfig adminConfig;

    @Mock
    TaskRepository taskRepository;

    @Test
    public  void shouldSendInformationEmail(){
        Mail mail = new Mail("test@test","Tasks: Once a day email", "Currently in database you got: 1 task");
        TemplateSelector template = TemplateSelector.SCHEDULED_MAIL;

        when(taskRepository.count()).thenReturn(1L);
        when(adminConfig.getAdminMail()).thenReturn("test@test");


        //then
        emailScheduler.sendInformationEmail();

        Mockito.verify(simpleEmailService,times(1)).send(mail, template);

    }

}