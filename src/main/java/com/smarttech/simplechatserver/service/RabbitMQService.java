package com.smarttech.simplechatserver.service;

import java.util.List;

public interface RabbitMQService {

    List<String> getAllRoomNames() throws Exception;
}
