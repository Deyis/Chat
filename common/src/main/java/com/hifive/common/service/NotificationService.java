package com.hifive.common.service;

import com.hifive.common.model.Notification;
import com.hifive.common.util.NotificationResolver;
import com.hifive.common.web.request.NotificationAnswerRequest;

public interface NotificationService extends BaseService {

    void addResolver(NotificationResolver resolver);

    void proceedAnswer(NotificationAnswerRequest answerRequest);

    Notification addNotification(Notification notification);
}
