package com.hifive.common.repository;

import com.hifive.common.model.Notification;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepositoryImpl extends AbstractRepository<Notification> implements NotificationRepository {

    @Override
    public Class<Notification> getEntityClass() {
        return Notification.class;
    }

}
