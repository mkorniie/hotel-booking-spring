package ua.mkorniie.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.mkorniie.dao.UserRepository;
import ua.mkorniie.model.config.WebSecurityConfig;

@ConditionalOnBean(WebSecurityConfig.class)
@Service
public class HotelUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public HotelUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new HotelUserDetails(userRepository.findByName(s)
                 .orElseThrow(IllegalArgumentException::new));
    }
}
