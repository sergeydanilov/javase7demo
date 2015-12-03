package ru.bpal.generator.builder;

import org.junit.Before;
import org.junit.Test;
import ru.bpal.reminder.rest.data.entity.db.*;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by serg on 29.10.15.
 */
public class SmsQueueItemBuilderTest {

    @Before
    public void setUp() throws Exception {
        CleanUpDataBaseBuilder.cleanUp();
    }


    @Test
    public void testCreateBPalLogin() throws Exception {


        String consumerUid = UUID.randomUUID().toString();
        String entepriseUid = UUID.randomUUID().toString();
        String textMessageTemplateUid = UUID.randomUUID().toString();

        MessageEntity entity = SmsQueueItemBuilder.createBPalLogin(consumerUid, entepriseUid, textMessageTemplateUid);

        assertNotNull(entity.id);
    }
}