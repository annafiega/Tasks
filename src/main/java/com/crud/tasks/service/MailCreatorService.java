package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.controller.TaskController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    AdminConfig adminConfig;

    @Autowired
    DbService service;

    @Autowired
    CompanyConfig companyConfig;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality =new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with TrelloAccount");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_goal", companyConfig.getCompanyGoal());
        context.setVariable("company_email", companyConfig.getCompanyEmail());
        context.setVariable("company_phone", companyConfig.getCompanyPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config",adminConfig );
        context.setVariable("application_functionality",functionality );
        ;

        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildTrelloScheduledMail (String message){
        List<String> functionality =new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with TrelloAccount");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("preview_message", "Your daily tasks");
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_goal", companyConfig.getCompanyGoal());
        context.setVariable("company_email", companyConfig.getCompanyEmail());
        context.setVariable("company_phone", companyConfig.getCompanyPhone());
        context.setVariable("show_button", true);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config",adminConfig );
        context.setVariable("application_functionality",functionality );
        context.setVariable("task_list", service.getAllTasks());
        context.setVariable("goodbye_message", "The TaskCrudApp Team"  );

        return templateEngine.process("mail/trello-scheduler-mail", context);

    }
}