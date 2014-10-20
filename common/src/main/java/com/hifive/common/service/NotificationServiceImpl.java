package com.hifive.common.service;

import com.hifive.common.model.Notification;
import com.hifive.common.repository.NotificationRepository;
import com.hifive.common.util.NotificationResolver;
import com.hifive.common.web.request.NotificationAnswerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    private List<NotificationResolver> resolvers = new ArrayList<>();

    @Override
    public void addResolver(NotificationResolver resolver) {
        resolvers.add(resolver);
    }

    @Override
    public void proceedAnswer(NotificationAnswerRequest answerRequest) {
        Notification notification = notificationRepository.findById(answerRequest.getNotificationId());
        notification.setAccepted(answerRequest.getAccepted());
        applyResolvers(Arrays.asList(notification));
    }

    private void applyResolvers(List<Notification> notifications) {
        notifications.parallelStream().forEach( n -> {
            resolvers.stream().forEach( r-> r.resolve(n));
            n.setPending(Boolean.FALSE);
        });
    }
}
