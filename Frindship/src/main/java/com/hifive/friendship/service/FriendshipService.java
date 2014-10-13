package com.hifive.friendship.service;

import com.hifive.common.service.BaseService;
import com.hifive.friendship.model.Friendship;

import java.util.List;

public interface FriendshipService extends BaseService {

    Friendship getFriends(Long userId);
    void addFriends(Long userId, List<Long> userIds);
    void removeFriends(Long userId, List<Long> userIds);
}
