package com.hifive.chat.repository;

import com.hifive.chat.model.Queue;
import com.hifive.security.model.User;

public interface QueueRepository extends BaseRepository<Queue> {

    Queue pull(String lang);

    void push(User user, String lang);

}
