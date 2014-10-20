package com.hifive.friendship.model;

import com.hifive.common.model.AbstractModel;
import com.hifive.security.model.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "friendship")
@NamedQueries({
    @NamedQuery(name = "Friendship.findByUserId",
            query = "select f from Friendship f where f.user.id = :userId or f.friend.id = :userId"),
    @NamedQuery(name = "Friendship.delete",
            query = "delete Friendship where (user.id = :userId and friend.id in (:ids)) or (friend.id = :userId and user.id in (:ids))")
})
public class Friendship extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friend_id")
    private User friend;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
