package com.hifive.chat.repository;

import com.hifive.chat.model.User;

public interface UserRepository extends BaseRepository<User> {

    public User getUserById(Long id);
}
