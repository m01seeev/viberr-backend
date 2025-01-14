package org.viberrbackend.Attachment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.viberrbackend.Attachment.DTO.AttachmentRequest;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;
    @PostMapping("/{userId}")
    public void createAttachment(@PathVariable String userId, @RequestBody AttachmentRequest attachmentDto) {
        attachmentService.AddAttachment(attachmentDto, userId);
    }
}
