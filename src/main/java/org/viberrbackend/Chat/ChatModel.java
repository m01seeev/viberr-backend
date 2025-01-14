package org.viberrbackend.Chat;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "chats")
public class ChatModel {
    @Id
    private String id;
    private List<String> users;
}
