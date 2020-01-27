package com.smarttech.simplechatserver.controller;

import com.smarttech.simplechatserver.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class MessageController {

    @MessageMapping("/room/{roomName}")
    @SendTo("/topic/messages.{roomName}")
    public Message sendMessageToRoom(@DestinationVariable String roomName, Message message) throws Exception {
        return new Message(message.getSender(), message.getContent());
    }

    /**
     * Couldn't get the @SendToUser annotation working, so pieced together this hack for the purpose of meeting requirements
     */
    @MessageMapping("/user/{username}")
    @SendTo("/topic/users.{username}")
    public Message sendMessageToUserHack(@DestinationVariable String username, Message message) throws Exception {
        return new Message(message.getSender(), message.getContent());
    }
//
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/user")
//    public void sendMessageToUser(Principal principal, Message message) {
//        simpMessagingTemplate.convertAndSendToUser("user1", "/queue/private", message);
//    }
}
