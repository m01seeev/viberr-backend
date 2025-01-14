package org.viberrbackend.Attachment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viberrbackend.Attachment.DTO.AttachmentRequest;
import org.viberrbackend.Attachment.DTO.AttachmentResponse;
import org.viberrbackend.Message.DTO.MessageRequest;
import org.viberrbackend.Message.MessageModel;
import org.viberrbackend.Message.MessageService;
import org.viberrbackend.User.UserRepository;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final MessageService messageService;
    private final UserRepository userRepository;

    public AttachmentResponse getAttachmentByMessage(String messageId) {
        AttachmentResponse attachmentResponse = new AttachmentResponse();
        AttachmentModel attachmentModel = attachmentRepository.findByMessageId(messageId);
        if (attachmentModel != null) {
            attachmentResponse.setType(attachmentModel.getType());
            attachmentResponse.setFileRef(attachmentModel.getFileRef());
        }
        return attachmentResponse;
    }

    public void AddAttachment(AttachmentRequest attachmentDto, String userId) {
        AttachmentModel attachmentModel = new AttachmentModel();
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessage("Файл");
        messageRequest.setUsername(attachmentDto.getUsername());
        MessageModel messageModel = messageService.addMessage(messageRequest, userId);
        attachmentModel.setMessageId(messageModel.getId());
        attachmentModel.setFileRef(attachmentDto.getFileRef());
        attachmentModel.setType(attachmentDto.getType());
        attachmentRepository.save(attachmentModel);
    }
}
