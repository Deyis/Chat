package com.hifive.friendship.repository;

import com.hifive.common.repository.BaseRepository;
import com.hifive.friendship.model.Friendship;
import com.hifive.security.model.User;

import java.util.List;

public interface FriendshipRepository extends BaseRepository<Friendship> {

    Friendship getByUserId(Long userId);

    void addFriends(User user, List<User> friends);

    void addFriend(User user, User friend);

    void removeFriends(User user, List<User> friends);

    void removeFriend(User user, User friend);
}
