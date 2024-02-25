package com.abah.chat_app.services;

import com.abah.chat_app.exceptions.ChatNotFoundException;
import com.abah.chat_app.exceptions.EmptyChatException;
import com.abah.chat_app.model.Chat;
import com.abah.chat_app.model.Message;

import java.util.HashSet;
import java.util.List;

public interface ChatService {

    public Chat addChat(Chat chat);

    List<Chat> findAllChats() throws EmptyChatException;

    Chat getById(int id)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatBySecondUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserNameOrSecondUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserNameAndSecondUserName(String firstUserName, String secondUserName)  throws ChatNotFoundException;

    Chat addMessage(Message add, int chatId)  throws ChatNotFoundException;
}
