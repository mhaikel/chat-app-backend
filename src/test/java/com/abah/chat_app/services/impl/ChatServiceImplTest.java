package com.abah.chat_app.services.impl;

import com.abah.chat_app.exceptions.ChatNotFoundException;
import com.abah.chat_app.exceptions.EmptyChatException;
import com.abah.chat_app.model.Chat;
import com.abah.chat_app.model.Message;
import com.abah.chat_app.repository.ChatRepository;
import com.abah.chat_app.services.SequenceGeneratorService;
import com.abah.chat_app.util.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    @InjectMocks
    private ChatServiceImpl chatService;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Test
    void givenValidChatObject_whenAddChat_thenReturnSavedChat() {
        Chat mockedChat = TestData.buildChat();

        when(sequenceGeneratorService.generateSequence(eq(Chat.SEQUENCE_NAME))).thenReturn(2);
        when(chatRepository.save(any(Chat.class))).thenReturn(mockedChat);

        Chat addedChat = chatService.addChat(mockedChat);
        List<Message> messages = addedChat.getMessageList();

        assertNotNull(addedChat);
        assertEquals(mockedChat.getChatId(), addedChat.getChatId());
        assertEquals(mockedChat.getMessageList().size(), messages.size());
        verify(chatRepository, times(1)).save(mockedChat);
        verify(sequenceGeneratorService, times(1)).generateSequence(Chat.SEQUENCE_NAME);
    }

    @Test
    void givenExistingChats_whenFindAllChats_thenReturnAllChats() throws EmptyChatException {
        List<Chat> mockedChats = Collections.singletonList(TestData.buildChat());

        when(chatRepository.findAll()).thenReturn(mockedChats);

        List<Chat> allChats = chatService.findAllChats();

        assertNotNull(allChats);
        assertTrue(allChats.size() > 0);
        assertEquals(mockedChats.size(), allChats.size());
        assertEquals(mockedChats.get(0).getChatId(), allChats.get(0).getChatId());
        verify(chatRepository, times(1)).findAll();
    }

    @Test
    void givenEmptyChats_whenFindAllChats_thenThrowEmptyChatException() throws EmptyChatException {
        List<Chat> mockedChats = null;

        when(chatRepository.findAll()).thenReturn(mockedChats);

        assertThrows(EmptyChatException.class, () -> chatService.findAllChats());
    }

    @Test
    void givenExistingChat_whenGetChatById_thenReturnChat() throws ChatNotFoundException {
        Chat mockedChat = TestData.buildChat();

        when(chatRepository.findById(anyInt())).thenReturn(Optional.of(mockedChat));

        Chat retrievedChat = chatService.getById(mockedChat.getChatId());

        assertNotNull(retrievedChat);
        assertEquals(mockedChat.getChatId(), retrievedChat.getChatId());
        assertTrue(retrievedChat.getMessageList().size() > 0);
        assertEquals(mockedChat.getMessageList().size(), retrievedChat.getMessageList().size());
        verify(chatRepository, times(1)).findById(eq(mockedChat.getChatId()));
    }
}