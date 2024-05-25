package com.ersoy.planing_todo.common;

import com.ersoy.planing_todo.domain.model.User;
import com.ersoy.planing_todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class BaseController {

    @Autowired
    UserRepository userRepository;

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found by this email: " + username));
            if (user != null) {
                return user;
            } else {
                throw new UsernameNotFoundException("User not found by this email: " + username);
            }
        } else {
            return null;
        }
    }


}
