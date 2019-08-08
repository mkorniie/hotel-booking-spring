package ua.mkorniie.controller.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import ua.mkorniie.model.config.WebSecurityConfig;

@ConditionalOnBean(WebSecurityConfig.class)
public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String name, String password );
}
