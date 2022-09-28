package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppInfoConfig {
    @Value("${info.app.name}")
    private String infoAppName;

    @Value("${info.company.name}")
    private String infoCompanyName;

    @Value("${info.company.email}")
    private String infoCompanyEmail;

    @Value("${info.company.phone}")
    private String infoCompanyPhone;
}
