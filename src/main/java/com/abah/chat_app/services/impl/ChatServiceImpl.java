package com.abah.chat_app.services.impl;

import com.abah.chat_app.exceptions.ChatNotFoundException;
import com.abah.chat_app.exceptions.EmptyChatException;
import com.abah.chat_app.model.Chat;
import com.abah.chat_app.model.Message;
import com.abah.chat_app.repository.ChatRepository;
import com.abah.chat_app.services.ChatService;
import com.abah.chat_app.services.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Chat addChat(Chat chat) {
        chat.setChatId(sequenceGeneratorService.generateSequence(Chat.SEQUENCE_NAME));
        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> findAllChats() throws EmptyChatException {
        return Optional.ofNullable(chatRepository.findAll())
                .orElseThrow(EmptyChatException::new);
    }

    @Override
    public Chat getById(int id) throws ChatNotFoundException {
        return Optional.ofNullable(chatRepository.findById(id).get())
                .orElseThrow(ChatNotFoundException::new);
    }

    @Override
    public HashSet<Chat> getChatByFirstUserName(String username) throws ChatNotFoundException {
        return Optional.ofNullable(chatRepository.getChatByFirstUserName(username))
                .orElseThrow(ChatNotFoundException::new);
    }

    @Override
    public HashSet<Chat> getChatBySecondUserName(String username) throws ChatNotFoundException {
        return Optional.ofNullable(chatRepository.getChatBySecondUserName(username))
                .orElseThrow(ChatNotFoundException::new);
    }

    @Override
    public HashSet<Chat> getChatByFirstUserNameOrSecondUserName(String username) throws ChatNotFoundException {
        HashSet<Chat> chatByFirstUser = chatRepository.getChatByFirstUserName(username);
        HashSet<Chat> chatBySecondUser = chatRepository.getChatBySecondUserName(username);

        chatBySecondUser.addAll(chatByFirstUser);

        if (chatByFirstUser.isEmpty() && chatBySecondUser.isEmpty()) {
            throw new ChatNotFoundException();
        } else if (chatBySecondUser.isEmpty()) {
            return chatByFirstUser;
        } else {
            return chatBySecondUser;
        }
    }

    @Override
    public HashSet<Chat> getChatByFirstUserNameAndSecondUserName(String firstUserName, String secondUserName) throws ChatNotFoundException {
        HashSet<Chat> chat = chatRepository.getChatByFirstUserNameAndSecondUserName(firstUserName, secondUserName);
        HashSet<Chat> chat1 = chatRepository.getChatBySecondUserNameAndFirstUserName(firstUserName, secondUserName);
        if (chat.isEmpty() && chat1.isEmpty()) {
            throw new ChatNotFoundException();
        } else if (chat.isEmpty()) {
            return chat1;
        } else {
            return chat;
        }
    }

    @Override
    public Chat addMessage(Message add, int chatId) throws ChatNotFoundException {
        Optional<Chat> chat=chatRepository.findById(chatId);
        Chat abc=chat.get();

        if(abc.getMessageList()==null){
            List<Message> msg=new ArrayList<>();
            msg.add(add);
            abc.setMessageList(msg);
            return chatRepository.save(abc);
        }else{
            List<Message> rates=abc.getMessageList();
            rates.add(add);
            abc.setMessageList(rates);
            return chatRepository.save(abc);
        }
    }

}
