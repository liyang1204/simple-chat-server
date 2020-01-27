package com.smarttech.simplechatserver.service;

import com.smarttech.simplechatserver.domain.MessageEntity;

import java.util.List;

public interface PostgreSQLMessageService {

    List<MessageEntity> getAllMessagesForChatRoom(String roomName);

    void saveMessage(String sender, String roomName, String content);
}
