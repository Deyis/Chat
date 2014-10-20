package com.hifive.chat.service;

import com.hifive.chat.tool.AbstractTest;
import com.hifive.common.model.Notification;
import com.hifive.common.repository.NotificationRepository;
import com.hifive.common.service.NotificationService;
import com.hifive.friendship.model.FriendshipNotification;
import com.hifive.security.model.User;
import com.hifive.security.service.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class NotificationServiceTest extends AbstractTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addNotification() {
        FriendshipNotification notification = new FriendshipNotification();
        notification.setFrom((User) userRepository.loadUserByUsername("admin"));
        notification.setTo((User) userRepository.loadUserByUsername("user"));
        notification.setText("test");
        notification.setPending(true);
        notification.setAccepted(false);

        notification = (FriendshipNotification) notificationService.addNotification(notification);

        Assert.assertNotNull(notification);

        notificationRepository.remove(notification.getId());
    }

}
