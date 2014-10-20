package com.hifive.chat.service;

import com.hifive.chat.tool.AbstractTest;
import com.hifive.security.model.User;
import com.hifive.security.service.UserRepository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@Transactional
public class UserServiceTest extends AbstractTest {

    @Autowired
    private UserRepository userService;

    private final static String ROLE_USER = "ROLE_USER";
    private final static String ADMIN_USER_NAME = "admin";

    @Test
    public void loadUserByName() {
        User user = (User) userService.loadUserByUsername(ADMIN_USER_NAME);
        Assert.assertNotNull(user);
        Assert.assertEquals("admin", user.getUsername());
    }

    @Test
    public void grantRole() {
        User user = (User) userService.loadUserByUsername(ADMIN_USER_NAME);
        Assert.assertTrue(!isUserHasRole(user, ROLE_USER));

        userService.grantRole(user.getId(), ROLE_USER);

        user = (User) userService.loadUserByUsername(ADMIN_USER_NAME);
        Assert.assertTrue(isUserHasRole(user, ROLE_USER));

        userService.deleteRole(user, ROLE_USER);

        user = (User) userService.loadUserByUsername(ADMIN_USER_NAME);
        Assert.assertTrue(!isUserHasRole(user, ROLE_USER));
    }

    @Test
    public void deleteRole() {
        User user = (User) userService.loadUserByUsername(ADMIN_USER_NAME);
        userService.grantRole(user.getId(), ROLE_USER);

        user = (User) userService.loadUserByUsername(ADMIN_USER_NAME);
        Assert.assertTrue(isUserHasRole(user, ROLE_USER));

        userService.deleteRole(user, ROLE_USER);

        user = (User) userService.loadUserByUsername(ADMIN_USER_NAME);
        Assert.assertTrue(!isUserHasRole(user, ROLE_USER));
    }

    @Test
    public void registerUser() {
        User user = userService.registerUser("newUser", "newPass");

        Assert.assertNotNull(user);

        userService.remove(user);
    }

    @Test
    public void registerUserWithRole() {
        User user = userService.registerUser("newUser", "newPass", ROLE_USER);

        Assert.assertTrue(isUserHasRole(user, ROLE_USER));

        userService.remove(user);
    }

    @Test
    public void getUsersByIds() {
        List<User> users = userService.getUsersByIds(Arrays.asList(new Long(1), new Long(2)));
        Assert.assertEquals(2, users.size());
        Assert.assertTrue(users.stream().allMatch(u -> u.getId() == 1 || u.getId() == 2 ));
    }



    private boolean isUserHasRole(User user, String role) {
        return user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(role));
    }

}
