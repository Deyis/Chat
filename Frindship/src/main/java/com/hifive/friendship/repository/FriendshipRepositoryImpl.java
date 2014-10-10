package com.hifive.friendship.repository;

import com.hifive.common.repository.AbstractRepository;
import com.hifive.friendship.model.Friendship;

public class FriendshipRepositoryImpl extends AbstractRepository<Friendship> implements FriendshipRepository {

    @Override
    public Friendship getByUserId(Long userId) {
        return getSingleByNamedQuery("Friendship.findByUserId", "userId", userId);
    }
}
