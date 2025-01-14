package org.viberrbackend.Message.DTO;

import lombok.Data;
import org.viberrbackend.Attachment.DTO.AttachmentResponse;

@Data
public class MessageResponse {
    private String id;
    private String sender;
    private String chat;
    private String message;
    private String timestamp;
    private AttachmentResponse attachment;
}
