package org.viberrbackend.Message;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends MongoRepository<MessageModel, String> {
    List<MessageModel> findAllByChat(String chatId);
    Optional<MessageModel> findFirstByChatOrderByTimestampDesc(String chatId);
}
