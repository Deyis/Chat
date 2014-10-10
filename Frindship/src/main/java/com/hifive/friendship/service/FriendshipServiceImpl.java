package com.hifive.friendship.service;

import com.hifive.friendship.model.Friendship;
import com.hifive.friendship.repository.FriendshipRepository;
import com.hifive.security.model.User;
import com.hifive.security.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Friendship getFriends(Long userId) {
        return friendshipRepository.getByUserId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    @Override
    public void addFriends(Long userId, List<Long> userIds) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        friendshipRepository.addFiends(user, userRepository.getUsersByIds(userIds));
    }

    @Override
    public void removeFriends(Long userId, List<Long> userIds) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        friendshipRepository.removeFriends(user, userRepository.getUsersByIds(userIds));
    }
}
