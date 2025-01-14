package org.viberrbackend.Chat;

import lombok.Data;
import org.viberrbackend.Message.DTO.MessageResponse;

import java.util.List;

@Data
public class ChatResponse {
    private String username;
    private String name;
    private String lastUpdate;
    private List<MessageResponse> messages;
    private String picRef;
}
