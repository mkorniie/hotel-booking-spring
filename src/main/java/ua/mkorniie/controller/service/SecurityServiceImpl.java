package ua.mkorniie.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ua.mkorniie.model.config.WebSecurityConfig;

@ConditionalOnBean(WebSecurityConfig.class)
@Service
public class SecurityServiceImpl implements SecurityService {

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDetailsService userDetailsService;

    @Override
    public String findLoggedInUsername() {
        return null;
    }

    @Override
    public void autoLogin(String name, String password) {

    }
}
