package com.hifive.friendship.model;

import com.hifive.common.model.Notification;
import com.hifive.security.model.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notifications")
@DiscriminatorValue("FRIENDSHIP")
public class FriendshipNotification extends Notification {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User from;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    private User to;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

}
