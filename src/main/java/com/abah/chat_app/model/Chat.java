package com.abah.chat_app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Transient
    public static final String SEQUENCE_NAME = "chat_sequence";

    @Id
    private int chatId;
    private String firstUserName;
    private String secondUserName;
    private List<Message> messageList;

}
