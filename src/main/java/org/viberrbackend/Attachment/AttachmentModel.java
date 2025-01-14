package org.viberrbackend.Attachment;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "attachments")
public class AttachmentModel {
    @Id
    private String id;
    private String type;
    private String messageId;
    private String fileRef;
}
