package com.hifive.security.service;

import com.hifive.security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserRepository extends UserDetailsService {

    public User registerUser(String userName, String password);

    public User registerUser(String userName, String password, String authority);

    public User grantRole(Long userId, String authority);

    public User grantRole(User user, String authority);


}
