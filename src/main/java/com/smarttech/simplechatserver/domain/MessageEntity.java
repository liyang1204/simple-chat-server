package com.smarttech.simplechatserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_generator")
    @SequenceGenerator(name = "message_generator", sequenceName = "message_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "sender")
    private String sender;

    @Column(name = "room_name")
    private String roomName;

}
