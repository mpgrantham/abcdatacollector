package com.canalbrewing.abcdatacollector.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
    public static int getGeneratedId(Statement stmt) throws SQLException {
        int id = 0;
        ResultSet rs = stmt.getGeneratedKeys();
        while (rs.next()) {
            id = rs.getInt(1);
        }

        return id;
    }
}
