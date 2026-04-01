package org.example.expert.config;

import org.springframework.stereotype.Component;

@Component
public class WhiteList {
    public static final String[] PATHS = {"/auth/**", "/actuator/**"};
}
