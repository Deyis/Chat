package com.hifive.friendship.repository;

import com.hifive.common.repository.AbstractRepository;
import com.hifive.friendship.model.Friendship;
import com.hifive.security.model.User;
import org.springframework.stereotype.Repository;
import java.util.Arrays;
import java.util.List;

@Repository
public class FriendshipRepositoryImpl extends AbstractRepository<Friendship> implements FriendshipRepository {

    @Override
    public Friendship getByUserId(Long userId) {
        return getSingleByNamedQuery("Friendship.findByUserId", "userId", userId);
    }

    @Override
    public void addFriends(User user, List<User> friends) {
        getByUserId(user.getId()).getFriends().addAll(friends);
        em.flush();
    }

    @Override
    public void addFriend(User user, User friend) {
        addFriends(user, Arrays.asList(friend));
    }

    @Override
    public void removeFriends(User user, List<User> friends) {
        getByUserId(user.getId()).getFriends().removeAll(friends);
        em.flush();
    }

    @Override
    public void removeFriend(User user, User friend) {
        removeFriends(user, Arrays.asList(friend));
    }


    @Override
    public Class<Friendship> getEntityClass() {
        return Friendship.class;
    }
}
