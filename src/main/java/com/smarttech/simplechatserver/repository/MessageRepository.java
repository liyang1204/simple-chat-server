package com.smarttech.simplechatserver.repository;

import com.smarttech.simplechatserver.domain.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {

    List<MessageEntity> findAllByRoomName(String roomName);
}
