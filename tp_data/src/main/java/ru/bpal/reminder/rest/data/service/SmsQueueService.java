package ru.bpal.reminder.rest.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bpal.reminder.rest.data.TpDataConstant;
import ru.bpal.reminder.rest.data.dao.PastgresqlDao;
import ru.bpal.reminder.rest.data.entity.db.MessageEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by serg on 28.10.15.
 */
public class SmsQueueService {

    static final Logger logger = LoggerFactory.getLogger(SmsQueueService.class);

    public static final String SMS_QUUE_ALL_FILEDS_STRING = "" +
            "id, " +
            "phone,full_text," +
            "consumer_first_name,consumer_middle_name,consumer_last_name,consumer_id," +
            "enterprise_id,sms_message_template_id," +
            "status, result_code, result_message,  " +
            "event_date, " +
            "deleted, " +
            "created, " +
            "modified";

    public static final String RETURNING_SMS_QUEUE_STRING = "" +
            "returning " +
            SMS_QUUE_ALL_FILEDS_STRING;


    public static final String SQL_INSERT_SMS_QUEUE_TEMPLATE = "insert into " +
            "bpr_sms_queue" +
            "(" +
            "phone,full_text," +
            "consumer_first_name,consumer_middle_name,consumer_last_name,consumer_id," +
            "enterprise_id,sms_message_template_id," +
            "status, " +
            "event_date " +
            ") " +
            "values   ('%s', '%s'," +
            "'%s','%s','%s','%s', " +
            "'%s','%s'," +
            "'%s') " +
            RETURNING_SMS_QUEUE_STRING;

    public static final String SQL_INSERT_SMS_QUEUE__PRS_TEMPLATE = "insert into " +
            "bpr_sms_queue" +
            "(" +
            "phone,full_text," +
            "consumer_first_name,consumer_middle_name,consumer_last_name,consumer_id," +
            "enterprise_id,sms_message_template_id," +
            "status, " +
            "event_date " +
            ") " +
            "values   ('%s', '%s'," +
            "'%s','%s','%s','%s', " +
            "'%s','%s'," +
            "'%s' ,%s) " +
            RETURNING_SMS_QUEUE_STRING;


    PastgresqlDao dao;


    public SmsQueueService(PastgresqlDao dao) {
        this.dao = dao;
    }

    public MessageEntity add(MessageEntity entity) throws SQLException {
        return createSmsQueueItem(
                entity.phone,
                entity.fullText,
                entity.firstName, entity.middleName, entity.lastName,
                entity.consumerUid,
                entity.entepriseUid, entity.textMessageTemplateUid,
                TpDataConstant.STATUS_NEW,
                entity.eventDate);
    }

    public MessageEntity createSmsQueueItem(String phone,
                                            String fullText,
                                            String firstName, String middleName, String lastName,
                                            String consumerUid,
                                            String entepriseUid,
                                            String textMessageTemplateUid,
                                            String status,
                                            Timestamp eventDate) {

        String sql = String.format(SQL_INSERT_SMS_QUEUE__PRS_TEMPLATE,
                phone,
                fullText,
                firstName, middleName, lastName,
                consumerUid,
                entepriseUid, textMessageTemplateUid,
                status, "?");

        try (PreparedStatement pst = dao.conn.prepareStatement(sql)) {

            pst.setTimestamp(1, eventDate);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return mapToEntity(rs);
            }
        } catch (SQLException e) {
            logger.error("sql exception, ", e);
        }

        return null;
    }

    private MessageEntity mapToEntity(ResultSet rs) throws SQLException {
        MessageEntity entity = new MessageEntity();

        entity.id = rs.getString("id");

        //data block
        entity.phone = rs.getString("phone");
        entity.fullText = rs.getString("full_text");

        entity.firstName = rs.getString("consumer_first_name");
        entity.middleName = rs.getString("consumer_middle_name");
        entity.lastName = rs.getString("consumer_last_name");

        entity.consumerUid = rs.getString("consumer_id");
        entity.entepriseUid = rs.getString("enterprise_id");
        entity.textMessageTemplateUid = rs.getString("sms_message_template_id");

        entity.status = rs.getString("status");
        entity.resultCode = rs.getString("result_code");
        entity.resultMessage = rs.getString("result_message");

        entity.eventDate = rs.getTimestamp("event_date");
        //end of data block

        entity.deleted = rs.getBoolean("deleted");
        entity.created = rs.getTimestamp("created");
        entity.modified = rs.getTimestamp("modified");

        return entity;
    }

    public List<String> findNewMessages(String hoursInterval , boolean forUpdate) {
        String sql = String.format("SELECT id from bpr_sms_queue where status='NEW' and event_date <= CURRENT_TIMESTAMP + INTERVAL '%s hours'", hoursInterval);
        if(forUpdate) {
            sql = sql + " for update";
        }

        List<String> result = new ArrayList<>();

        try (Statement statement = dao.conn.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                result.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            logger.error("sql exception, ", e);
        }
        return result;
    }
}
