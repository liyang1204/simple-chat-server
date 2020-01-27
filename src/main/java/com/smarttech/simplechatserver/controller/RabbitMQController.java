package com.smarttech.simplechatserver.controller;

import com.smarttech.simplechatserver.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(path = "/api/rabbitmq")
@Slf4j
public class RabbitMQController {

    private final RabbitMQService rabbitMQService;

    public RabbitMQController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @GetMapping(path = "/rooms")
    public List<String> getAllRoomNames() throws Exception {
        return rabbitMQService.getAllRoomNames();
    }

}
