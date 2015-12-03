package ru.bpal.reminder.rest.data.entity.db;

import java.sql.Timestamp;

/**
 * Created by serg on 28.10.15.
 */
public class MessageEntity extends AbstractDbEntity {

    public String phone;

    public String email;

    public String firstName;
    public String middleName;
    public String lastName;

    public String textMessageTemplateUid;
    public String datetime;

    public String fullText;

    public String consumerUid;
    public String entepriseUid;

    public String status;

    public String resultCode;
    public String resultMessage;

    public Timestamp eventDate;
    public Timestamp eventDateString;

}

