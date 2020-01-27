package com.smarttech.simplechatserver.controller;

import com.smarttech.simplechatserver.domain.MessageEntity;
import com.smarttech.simplechatserver.service.PostgreSQLMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/messages")
@Slf4j
public class PostgreSQLMessageController {

    private final PostgreSQLMessageService postgreSQLMessageService;

    public PostgreSQLMessageController(PostgreSQLMessageService postgreSQLMessageService) {
        this.postgreSQLMessageService = postgreSQLMessageService;
    }

    @GetMapping
    public List<MessageEntity> getMessagesForRoom(@RequestParam String roomName) {
        log.info("Getting all messages for room name: {}", roomName);
        return postgreSQLMessageService.getAllMessagesForChatRoom(roomName);
    }

    @PostMapping
    public void saveMessageToPostgreSQL(@RequestParam String sender,
                                        @RequestParam String roomName,
                                        @RequestParam String content) {
        log.info("Saving message with content: {} from sender: {} to room: {}", content, sender, roomName);
        postgreSQLMessageService.saveMessage(sender, roomName, content);
        return;
    }
}
