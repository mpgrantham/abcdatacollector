package com.canalbrewing.abcdatacollector.dal;

import com.canalbrewing.abcdatacollector.common.DatabaseConstants;
import com.canalbrewing.abcdatacollector.model.AppMode;
import com.canalbrewing.abcdatacollector.model.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeDao implements InitializeSql, DisposableBean {
    Logger logger = LoggerFactory.getLogger(InitializeDao.class);
    private Connection connection = null;

    public InitializeDao(@Value("${abcdatacollector.dropTables}") String drop) {
        try {
            this.connect();
            this.dropAllTables(drop);
            this.createAllTables();
        } catch ( SQLException ex ) {
            logger.error("Initialize Database Error {}", ex.getMessage(), ex);
        }
    }

    private void dropAllTables(String drop) throws SQLException {
        if (drop == null || drop.equalsIgnoreCase("no")) {
            return;
        }

        for ( TableDDL ddl : ALL_TABLES ) {
            if (tableExists(ddl.getName())) {
                // System.out.println("Dropping " + ddl.getName());
                logger.info("Dropping {}", ddl.getName());
                executeStatement(ddl.getDropStatement());
            }
        }
    }

    public void createAllTables() throws SQLException {
        for ( TableDDL ddl : ALL_TABLES ) {
            if (!tableExists(ddl.getName())) {
//                System.out.println("Creating " + ddl.getName());
                logger.info("Creating {}", ddl.getName());
                executeStatement(ddl.getCreateStatement());
                for ( String sql : ddl.getInitStatements() ) {
                    executeStatement(sql);
                }
            }
        }
    }

    private void executeStatement(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    private boolean tableExists(String tableName)
            throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);

        return result.next();
    }

    public AppMode getAppMode() throws SQLException {
        AppMode appMode = new AppMode();

        try ( PreparedStatement stmt = connection.prepareStatement(SELECT_APP_MODE);
              ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                appMode.setId(rs.getInt(DatabaseConstants.ID));
                appMode.setSetupComplete(rs.getString(DatabaseConstants.SETUP_COMPLETE));
                appMode.setRequireLogin(rs.getString(DatabaseConstants.REQUIRE_LOGIN));
                appMode.setStartPage(rs.getString(DatabaseConstants.START_PAGE));
            }
        }

        return appMode;
    }

    public AppMode updateAppMode(AppMode appMode) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(UPDATE_APP_MODE) ) {
            stmt.setString(1, appMode.getSetupComplete());
            stmt.setString(2, appMode.getRequireLogin());
            stmt.setString(3, appMode.getStartPage());
            stmt.setInt(4, appMode.getId());
            stmt.executeUpdate();
        }
        return appMode;
    }

    public List<Relationship> getRelationships() throws SQLException {
        List<Relationship> list = new ArrayList<>();

        try ( PreparedStatement stmt = connection.prepareStatement(SELECT_RELATIONSHIPS);
              ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Relationship relationship = new Relationship();
                relationship.setId(rs.getInt(DatabaseConstants.ID));
                relationship.setName(rs.getString(DatabaseConstants.RELATIONSHIP));
                list.add(relationship);
            }
        }

        return list;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DatabaseConstants.CONNECT_URL);
    }

    @Override
    public void destroy() {
        try {
            DriverManager.getConnection(DatabaseConstants.SHUTDOWN_URL);
        } catch ( SQLException ex ) {
            if (!ex.getSQLState().equals(DatabaseConstants.SHUTDOWN_EXCEPTION)) {
                logger.error("Shutdown Exception {}", ex.getMessage(), ex);
            }
        }
    }
}
