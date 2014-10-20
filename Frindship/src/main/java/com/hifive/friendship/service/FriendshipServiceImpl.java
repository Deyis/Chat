package com.hifive.friendship.service;

import com.hifive.common.service.NotificationService;
import com.hifive.friendship.model.Friendship;
import com.hifive.friendship.model.FriendshipNotification;
import com.hifive.friendship.repository.FriendshipRepository;
import com.hifive.security.model.User;
import com.hifive.security.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;


    @Override
    public List<User> getFriends(Long userId) {
        List<Friendship> friendships = friendshipRepository.getByUserId(userId);
        return friendships.stream().map( friendship ->
                    friendship.getUser().getId().equals(userId)? friendship.getFriend() : friendship.getUser()
                ).collect(Collectors.toList());
    }

    @Override
    public void addFriends(User user, List<Long> userIds) {
        friendshipRepository.addFriends(user, userRepository.getUsersByIds(userIds));
    }

    @Override
    public void removeFriends(User user, List<Long> userIds) {
        friendshipRepository.removeFriends(user, userIds);
    }


    @PostConstruct
    protected void postConstruct () {
        notificationService.addResolver(notification -> {
            if( !(notification instanceof FriendshipNotification) || !notification.getAccepted()) {
                return;
            }
            friendshipRepository.addFriend(((FriendshipNotification) notification).getFrom(), ((FriendshipNotification) notification).getTo());
        });
    }
}
