package com.abah.chat_app.integration;

import com.abah.chat_app.model.Chat;
import com.abah.chat_app.repository.ChatRepository;
import com.abah.chat_app.util.Mapper;
import com.abah.chat_app.util.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatControllerIT extends BaseIT{

    private final String API_URL = "/chats";

    @Autowired
    private ChatRepository chatRepository;

    @AfterEach
    void cleanUp() {
        chatRepository.deleteAll();
    }

    @Test
    void givenValidaChatRequest_whenAddChat_thenChatAddedSuccessfully() throws Exception {
        Chat chatReq = TestData.buildChat();

        MvcResult mvcResult = mockMvc.perform(post(API_URL + "/add")
                .content(Mapper.serialize(chatReq))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType());

        Chat response = Mapper.deSerialize(mvcResult.getResponse().getContentAsString(), Chat.class);

        assertNotNull(response);
        assertEquals(chatReq.getFirstUserName(), response.getFirstUserName());
        assertEquals(chatReq.getSecondUserName(), response.getSecondUserName());
    }
}
