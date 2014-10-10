package com.hifive.friendship.repository;

import com.hifive.common.repository.BaseRepository;
import com.hifive.friendship.model.Friendship;

public interface FriendshipRepository extends BaseRepository<Friendship> {

    public Friendship getByUserId(Long userId);
}
