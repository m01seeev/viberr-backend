package org.viberrbackend.Attachment.DTO;

import lombok.Data;

@Data
public class AttachmentRequest {
    private String fileRef;
    private String type;
    private String username;
}
