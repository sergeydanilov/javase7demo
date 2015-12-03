package ru.bpal.generator.builder;

import ru.bpal.reminder.rest.data.dao.PastgresqlDao;
import ru.bpal.reminder.rest.data.entity.db.MessageEntity;
import ru.bpal.reminder.rest.data.service.SmsQueueService;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by serg on 29.10.15.
 */
public class SmsQueueItemBuilder {

    public static MessageEntity createBPalLogin(String consumerUid, String entepriseUid, String textMessageTemplateUid) {
        PastgresqlDao dao = new PastgresqlDao();

        SmsQueueService service = new SmsQueueService(dao);

        String phone = "79500650500";
        String fullText = "Это полный тескт для Сергея";
        String firstName = "Сергей";
        String middleName = "Николаевич";
        String lastName = "Кукушкин";
        String status = "NEW";
        Timestamp eventDate = new Timestamp((new Date()).getTime());


        return service.createSmsQueueItem(phone, fullText,firstName, middleName, lastName,consumerUid,entepriseUid,textMessageTemplateUid,status, eventDate);
    }
}
