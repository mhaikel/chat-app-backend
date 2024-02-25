package com.abah.chat_app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String senderEmail;
    private Date time = new Date(System.currentTimeMillis());
    private String replymessage;
}
