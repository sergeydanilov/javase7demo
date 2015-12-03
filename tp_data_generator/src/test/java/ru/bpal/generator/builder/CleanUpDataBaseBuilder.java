package ru.bpal.generator.builder;

import ru.bpal.reminder.rest.data.dao.PastgresqlDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by serg on 29.10.15.
 */
public class CleanUpDataBaseBuilder {

    public static void cleanUp() throws SQLException {
        PastgresqlDao dao = new PastgresqlDao();
        databaseCleanUp(dao);
    }

    protected static void databaseCleanUp(PastgresqlDao dao) throws SQLException {
        Connection connection = dao.conn;
        Statement statement = null;
        try {
            connection.setAutoCommit(Boolean.FALSE);

            statement = connection.createStatement();
            String tablesString = "";
            ResultSet tables = connection.getMetaData().getTables(null, null, "%", null);
            boolean first = true;
            while (tables.next()) {
                String string = tables.getString(3);
                if (string.startsWith("bpr_") || string.startsWith("oauth_")) {
                    if(first) {
                        tablesString = tables.getString(3);
                        first = false;
                    } else {
                        tablesString = tablesString + ", "+tables.getString(3);
                    }
                }
            }
            String sql = "TRUNCATE " + tablesString + " RESTART IDENTITY";
            statement.executeUpdate(sql);
            connection.commit();
            connection.setAutoCommit(Boolean.TRUE);
        } finally {
            if (statement != null) statement.close();
        }
    }
}
