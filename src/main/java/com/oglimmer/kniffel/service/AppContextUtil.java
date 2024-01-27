package com.oglimmer.kniffel.service;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Helps to make the Spring context available in non-Spring classes, in this case the KniffelRules service.
 */
@Configuration
public class AppContextUtil {

    private static ApplicationContext applicationContext;

    public AppContextUtil(ApplicationContext applicationContext) {
        AppContextUtil.applicationContext = applicationContext;
    }

    public static KniffelRules getKniffelRules() {
        return applicationContext.getBean(KniffelRules.class);
    }

}
