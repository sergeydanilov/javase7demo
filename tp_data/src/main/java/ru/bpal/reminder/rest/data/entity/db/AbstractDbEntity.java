package ru.bpal.reminder.rest.data.entity.db;

//import java.util.Date;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by serg on 29.10.15.
 */
public abstract class AbstractDbEntity {
    public String id;

    // custom filelds....

    public boolean deleted;
    public Timestamp created;
    public Timestamp modified;

}
