package com.hifive.chat.service;

import com.hifive.chat.tool.AbstractTest;
import com.hifive.friendship.model.Friendship;
import com.hifive.friendship.repository.FriendshipRepository;
import com.hifive.friendship.service.FriendshipService;
import com.hifive.security.model.User;
import com.hifive.security.service.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Transactional
public class FriendshipServiceTest extends AbstractTest {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addFriend() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        List<Friendship> friendships = createFriendshipForUsers(firstUser, secondUser);

        Assert.assertEquals(1, friendships.size());
        Friendship friendship = friendships.get(0);

        Assert.assertTrue(isFriendshipBetweenUsers(friendship, firstUser, secondUser));

        friendshipRepository.remove(friendship.getId());
    }

    @Test
    public void checkIsFriendInAnotherDirection() {

        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        createFriendshipForUsers(firstUser, secondUser);

        List<Friendship> friendships = friendshipRepository.getByUserId(secondUser.getId());
        Assert.assertEquals(1, friendships.size());

        Friendship friendship = friendships.get(0);
        Assert.assertTrue(isFriendshipBetweenUsers(friendship, firstUser, secondUser));

        friendshipRepository.remove(friendship);
    }

    @Test
    public void getFriends() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        Friendship friendship = createFriendshipForUsers(firstUser, secondUser).get(0);

        List<User> friends= friendshipService.getFriends(secondUser.getId());
        Assert.assertTrue(friends.contains(firstUser));

        friendshipRepository.remove(friendship);
    }

    @Test
    public void removeFriends() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        createFriendshipForUsers(firstUser, secondUser);

        friendshipService.removeFriends(firstUser, Arrays.asList(secondUser.getId()));

        List<Friendship> friendships = friendshipRepository.getByUserId(firstUser.getId());
        Assert.assertTrue(friendships.isEmpty());
    }

    @Test
    public void isFriendsRemovedInSecond() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        createFriendshipForUsers(firstUser, secondUser);

        friendshipService.removeFriends(firstUser, Arrays.asList(secondUser.getId()));

        List<Friendship> friendships = friendshipRepository.getByUserId(secondUser.getId());
        Assert.assertTrue(friendships.isEmpty());
    }


    private List<Friendship> createFriendshipForUsers(User user, User friend) {
        friendshipService.addFriends(user, Arrays.asList(friend.getId()));
        return friendshipRepository.getByUserId(user.getId());
    }

    private boolean isFriendshipBetweenUsers(Friendship friendship, User user1, User user2) {
        return friendship.getUser().equals(user1) && friendship.getFriend().equals(user2)
                || friendship.getUser().equals(user2) && friendship.getFriend().equals(user1);
    }
}
