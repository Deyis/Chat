package com.hifive.friendship.service;

import com.hifive.common.service.BaseService;
import com.hifive.friendship.model.Friendship;
import com.hifive.security.model.User;

import java.util.List;

public interface FriendshipService extends BaseService {

    List<User> getFriends(Long userId);
    void addFriends(User user, List<Long> userIds);
    void removeFriends(User user, List<Long> userIds);
}
