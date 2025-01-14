package org.viberrbackend.Chat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends MongoRepository<ChatModel, String> {
    List<ChatModel> findAllByUsersContains(String userId);
    @Query("{ 'users': { $all: [?0, ?1] } }")
    Optional<ChatModel> findChatByUserIds(String userId, String secondUserId);
}
