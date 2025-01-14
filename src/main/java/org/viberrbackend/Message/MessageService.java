package org.viberrbackend.Message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viberrbackend.Chat.ChatModel;
import org.viberrbackend.Chat.ChatRepository;
import org.viberrbackend.Chat.ChatService;
import org.viberrbackend.Message.DTO.MessageRequest;
import org.viberrbackend.User.UserModel;
import org.viberrbackend.User.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatService chatService;

    public List<MessageModel> getAllMessagesByChat(String chatId) {
        return messageRepository.findAllByChat(chatId);
    }

    public MessageModel addMessage(MessageRequest messageDto, String userId) {
        MessageModel messageModel = new MessageModel();
        UserModel userModel = userRepository.findByUsername(messageDto.getUsername());
        Optional<ChatModel> chatModel = getChat(userId, userModel.getId());
        if (chatModel.isPresent()) {
            messageModel.setChat(chatModel.get().getId());
        } else {
            ChatModel newChat = chatService.createChat(userModel.getId(), userId);
            messageModel.setChat(newChat.getId());
        }
        messageModel.setMessage(messageDto.getMessage());
        messageModel.setSender(userId);
        messageModel.setTimestamp(LocalDateTime.now());
        return messageRepository.save(messageModel);
    }

    public Optional<ChatModel> getChat(String userId, String secondUserId) {
        return chatRepository.findChatByUserIds(userId, secondUserId);
    }
}
