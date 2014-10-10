package com.hifive.security.service;

import com.hifive.security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserRepository extends UserDetailsService {

    User registerUser(String userName, String password);
    User registerUser(String userName, String password, String authority);
    User grantRole(Long userId, String authority);
    User grantRole(User user, String authority);

    List<User> getUsersByIds(List<Long> ids);


}
