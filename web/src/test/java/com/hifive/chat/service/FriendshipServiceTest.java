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

        FriendshipTest friendshipTest = new FriendshipTest();
        friendshipTest.createFriendship(firstUser, secondUser);

        Assert.assertTrue(friendshipTest.isFriendshipExists(firstUser, secondUser));
        Assert.assertTrue(friendshipTest.isFriendshipExists(secondUser, firstUser));

        friendshipRepository.remove(friendshipTest.getFriendship().getId());
    }

    @Test
    public void getFriends() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        FriendshipTest friendshipTest = new FriendshipTest();
        friendshipTest.createFriendship(firstUser, secondUser);

        List<User> friends= friendshipService.getFriends(secondUser.getId());
        Assert.assertTrue(friends.contains(firstUser));

        friendshipRepository.remove(friendshipTest.getFriendship().getId());
    }

    @Test
    public void removeFriends() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        FriendshipTest friendshipTest = new FriendshipTest();
        friendshipTest.createFriendship(firstUser, secondUser);

        friendshipService.removeFriends(firstUser, Arrays.asList(secondUser.getId()));

        Assert.assertFalse(friendshipTest.isFriendshipExists(firstUser, secondUser));
        Assert.assertFalse(friendshipTest.isFriendshipExists(secondUser, firstUser));
    }

    private class FriendshipTest {

        private Friendship friendship;

        public void createFriendship(User user1, User user2) {
            List<Friendship> friendships = createFriendshipForUsers(user1, user2);
            Assert.assertEquals(1, friendships.size());
            friendship = friendships.get(0);
        }

        public boolean isFriendshipExists(User user1, User user2) {
            List<Friendship> friendships = friendshipRepository.getByUserId(user1.getId());
            return !friendships.isEmpty() && isFriendshipBetweenUsers(friendships.get(0), user1, user2);
        }

        public Friendship getFriendship() {
            return friendship;
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
}
