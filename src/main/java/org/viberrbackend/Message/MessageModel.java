package org.viberrbackend.Message;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
public class MessageModel {
    @Id
    private String id;
    private String sender;
    private String chat;
    private String message;
    private LocalDateTime timestamp;
}
