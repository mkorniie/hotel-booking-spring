package ua.mkorniie.controller.service.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
