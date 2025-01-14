package org.viberrbackend.Message;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.viberrbackend.Message.DTO.MessageRequest;

@RestController
@RequestMapping("api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/{userId}")
    public void createMessage(@PathVariable String userId, @RequestBody MessageRequest messageDto) {
        messageService.addMessage(messageDto, userId);
    }
}
