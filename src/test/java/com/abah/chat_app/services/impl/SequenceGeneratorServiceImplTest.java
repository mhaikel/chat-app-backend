package com.abah.chat_app.services.impl;

import com.abah.chat_app.model.Chat;
import com.abah.chat_app.model.DatabaseSequence;
import com.abah.chat_app.util.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SequenceGeneratorServiceImplTest {

    @InjectMocks
    private SequenceGeneratorServiceImpl sequenceGeneratorService;

    @Mock
    private MongoOperations mongoOperations;

    @Test
    void givenValidQueryResultForSequenceGeneration_whenGenerateSequence_thenReturnGeneratedSequence() {
        String mockedSequenceName = Chat.SEQUENCE_NAME;
        DatabaseSequence mockedCounterSeq = TestData.buildSequence();
        Query mockedQuery = TestData.buildQuery();
        UpdateDefinition mockedUpdateDefinition = TestData.buildUpdateDefinition();
        FindAndModifyOptions mockedOptions = TestData.buildFindAndModifyOptions();

        when(mongoOperations.findAndModify(eq(mockedQuery), eq(mockedUpdateDefinition), eq(mockedOptions), any()))
                .thenReturn(mockedCounterSeq);

        int sequence = sequenceGeneratorService.generateSequence(mockedSequenceName);

        assertTrue(sequence > 0);
        assertEquals(mockedCounterSeq.getSeq(), sequence);
    }
}