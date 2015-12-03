package ru.bpal.reminder.rest.data.entity.json;

/**
 * Created by serg on 31.10.15.
 */
public class MessageEntityJson extends AbstractJsonEntity {

    public String phone;

    public String email;

    public String firstName;
    public String middleName;
    public String lastName;

    public String textMessageTemplateUid;

    public String fullText;

    public String consumerUid;
    public String entepriseUid;

    public String status;

    public String resultCode;
    public String resultMessage;

    public String eventDate;
}
