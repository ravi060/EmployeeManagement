package com.rt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
@Data
@ConfigurationProperties(prefix="employee.module")
@EnableConfigurationProperties
public class AppConfigProperties {
    private Map<String, String> messages;
}
