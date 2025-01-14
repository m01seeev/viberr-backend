package org.viberrbackend.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viberrbackend.Attachment.AttachmentModel;
import org.viberrbackend.Attachment.AttachmentRepository;
import org.viberrbackend.Attachment.AttachmentService;
import org.viberrbackend.Attachment.DTO.AttachmentResponse;
import org.viberrbackend.Message.MessageModel;
import org.viberrbackend.Message.MessageRepository;
import org.viberrbackend.Message.DTO.MessageResponse;
import org.viberrbackend.Profile.ProfileModel;
import org.viberrbackend.Profile.ProfileRepository;
import org.viberrbackend.User.UserModel;
import org.viberrbackend.User.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ProfileRepository profileRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;

    public List<ChatResponse> getAllChats(String userId) {
        List<ChatResponse> chats = new ArrayList<>();
        List<ChatModel> chatsById = chatRepository.findAllByUsersContains(userId);
        if (chatsById != null) {
            for (ChatModel chatModel : chatsById) {
                String secondUserId = getSecondUserId(chatModel, userId);
                assert secondUserId != null;
                ChatResponse chatResponse = buildChatResponse(secondUserId);
                Optional<MessageModel> lastMessage = messageRepository.findFirstByChatOrderByTimestampDesc(chatModel.getId());
                String update = String.valueOf(lastMessage.get().getTimestamp());
                chatResponse.setLastUpdate(update);
                List<MessageResponse> messages = mapMessages(messageRepository.findAllByChat(chatModel.getId()));
                chatResponse.setMessages(messages);
                chats.add(chatResponse);
            }
            chats.sort(Comparator.comparing((ChatResponse chat) ->
                    LocalDateTime.parse(chat.getLastUpdate(), DateTimeFormatter.ISO_DATE_TIME)
            ).reversed());
            chats.forEach(chat -> chat.setLastUpdate(chat.getLastUpdate().substring(11, 16)));
        }
        return chats;
    }

    public ChatModel createChat(String userId, String secondUserId) {
        ChatModel chatModel = new ChatModel();
        List<String> users = new ArrayList<>();
        users.add(userId);
        users.add(secondUserId);
        chatModel.setUsers(users);
        return chatRepository.save(chatModel);
    }

    private String getSecondUserId(ChatModel chatModel, String userId) {
        return chatModel.getUsers().stream().filter(id -> !id.equals(userId)).findFirst().orElse(null);
    }

    private ChatResponse buildChatResponse(String secondUserId) {
        ProfileModel secondUser = profileRepository.findByUserId(secondUserId);
        UserModel user = userRepository.findById(secondUserId).orElse(null);
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setName(secondUser.getFirstName() + " " + secondUser.getLastName());
        chatResponse.setPicRef(secondUser.getPicRef());
        assert user != null;
        chatResponse.setUsername(user.getUsername());
        return chatResponse;
    }

    private List<MessageResponse> mapMessages(List<MessageModel> messageModels) {
        List<MessageResponse> messages = new ArrayList<>();
        for (MessageModel messageModel : messageModels) {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage(messageModel.getMessage());
            messageResponse.setChat(messageModel.getChat());
            messageResponse.setSender(messageModel.getSender());
            messageResponse.setTimestamp(String.valueOf(messageModel.getTimestamp()));
            AttachmentResponse attachmentResponse = new AttachmentResponse();
            AttachmentModel attachmentModel = attachmentRepository.findByMessageId(messageModel.getId());
            if (attachmentModel != null) {
                attachmentResponse.setType(attachmentModel.getType());
                attachmentResponse.setFileRef(attachmentModel.getFileRef());
            }
            messageResponse.setAttachment(attachmentResponse);
            messageResponse.setId(messageModel.getId());
            messages.add(messageResponse);
        }
        return messages;
    }
}
