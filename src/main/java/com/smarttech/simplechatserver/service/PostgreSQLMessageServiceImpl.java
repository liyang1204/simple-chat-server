package com.smarttech.simplechatserver.service;

import com.smarttech.simplechatserver.domain.MessageEntity;
import com.smarttech.simplechatserver.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostgreSQLMessageServiceImpl implements PostgreSQLMessageService {

    private final MessageRepository messageRepository;

    public PostgreSQLMessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageEntity> getAllMessagesForChatRoom(String roomName) {
        return (List<MessageEntity>) messageRepository.findAllByRoomName(roomName);
    }

    public void saveMessage(String sender, String roomName, String content) {
        MessageEntity messageEntity = MessageEntity.builder()
                .sender(sender).roomName(roomName).messageContent(content).build();
        messageRepository.save(messageEntity);
        return;
    }
}
