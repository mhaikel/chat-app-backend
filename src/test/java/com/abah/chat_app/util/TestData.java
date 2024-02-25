package com.abah.chat_app.util;

import com.abah.chat_app.model.Chat;
import com.abah.chat_app.model.DatabaseSequence;
import com.abah.chat_app.model.Message;
import com.abah.chat_app.model.User;
import org.bson.Document;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TestData {

    public static Chat buildChat() {
        final Chat chat = new Chat();
        chat.setFirstUserName("user1");
        chat.setSecondUserName("user2");
        chat.setMessageList(Collections.singletonList(buildMessage()));
        return chat;
    }

    private static Message buildMessage() {
        final Message message = new Message();
        message.setSenderEmail("user1");
        message.setReplymessage("message replied");
        message.setTime(new Date());
        return message;
    }

    public static DatabaseSequence buildSequence() {
        DatabaseSequence seq = new DatabaseSequence();
        seq.setId("seq_id");
        seq.setSeq(1);
        return seq;
    }

    public static Query buildQuery() {
        return new Query();
    }

    public static UpdateDefinition buildUpdateDefinition() {
        return new UpdateDefinition() {
            @Override
            public Boolean isIsolated() {
                return true;
            }

            @Override
            public Document getUpdateObject() {
                return new Document();
            }

            @Override
            public boolean modifies(String s) {
                return false;
            }

            @Override
            public void inc(String s) {

            }

            @Override
            public List<ArrayFilter> getArrayFilters() {
                return null;
            }
        };
    }

    public static FindAndModifyOptions buildFindAndModifyOptions() {
        return new FindAndModifyOptions();
    }

    public static User buildUser() {
        User user = new User();
        user.setUserName("testUser");
        return user;
    }
}
