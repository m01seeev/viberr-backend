package org.viberrbackend.Attachment;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AttachmentRepository extends MongoRepository<AttachmentModel, String> {
    AttachmentModel findByMessageId(String messageId);
}
