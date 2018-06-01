package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CompanyConfig {
    @Value("TaskCrudAppCreator")
    private String companyName;
    @Value ("Our goal is to make the world a better place!")
    private String companyGoal;
    @Value("task@crud.com")
    private String companyEmail;
    @Value ("744489230")
    private String companyPhone;
}
