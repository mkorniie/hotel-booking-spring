package ua.mkorniie.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.config.WebSecurityConfig;
import ua.mkorniie.model.pojo.User;

@ConditionalOnBean(WebSecurityConfig.class)
@Service
public class HotelUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByName(s);

        if (user == null) {
            throw new UsernameNotFoundException("User with name " + s + " hasn't been found.");
        }

        return new HotelUserDetails(user);
    }
}
