package com.canalbrewing.abcdatacollector.dal;

import com.canalbrewing.abcdatacollector.common.DatabaseConstants;
import com.canalbrewing.abcdatacollector.model.Abc;
import com.canalbrewing.abcdatacollector.model.Observed;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ObservedDao implements ObservedSql {
    private Connection connection = null;

    public ObservedDao() {
        try {
            this.connect();
        } catch ( SQLException ex ) {
            ex.printStackTrace();
        }
    }

    public List<Observed> getAllObserved(int userId) throws SQLException {
        List<Observed> list = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_OBSERVED)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapObserved(rs));
            }
        }

        return list;
    }

    public Observed getObserved(int observedId) throws SQLException {
        Observed observed = null;

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_OBSERVED)) {
            stmt.setInt(1, observedId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                observed = mapObserved(rs);
            }
        }

        return observed;
    }

    public List<Abc> getObservedDefaults(int observedId) throws SQLException {
        List<Abc> list = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_OBSERVED_DEFAULTS)) {
            stmt.setInt(1, observedId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Abc abc = new Abc();
                abc.setId(rs.getInt(DatabaseConstants.ID));
                abc.setTypeCode(rs.getString(DatabaseConstants.TYPE_CODE));
                abc.setValue(rs.getString(DatabaseConstants.VALUE));
                abc.setActiveFl(rs.getString(DatabaseConstants.ACTIVE_FL));
                list.add(abc);
            }
        }

        return list;
    }

    private Observed mapObserved(ResultSet rs) throws SQLException {
        Observed observed = new Observed();
        observed.setId(rs.getInt(DatabaseConstants.ID));
        observed.setObservedName(rs.getString(DatabaseConstants.OBSERVED_NAME));
        observed.setUserId(rs.getInt(DatabaseConstants.USER_ID));
        return observed;
    }


    public Observed insertObserved(Observed observed) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(INSERT_OBSERVED, Statement.RETURN_GENERATED_KEYS) ) {
            stmt.setString(1, observed.getObservedName());
            stmt.setInt(2, observed.getUserId());
            stmt.executeUpdate();

            observed.setId(DatabaseUtils.getGeneratedId(stmt));
        }

        return observed;
    }

    public Abc insertObservedDefault(int observedId, Abc abc) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(INSERT_OBSERVED_DEFAULTS, Statement.RETURN_GENERATED_KEYS) ) {
            stmt.setInt(1, observedId);
            stmt.setString(2, abc.getTypeCode());
            stmt.setString(3, abc.getValue());
            stmt.setString(4, abc.getActiveFl());
            stmt.executeUpdate();

            abc.setId(DatabaseUtils.getGeneratedId(stmt));
        }

        return abc;
    }

    public Observed updateObserved(Observed observed) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(UPDATE_OBSERVED) ) {
            stmt.setString(1, observed.getObservedName());
            stmt.setInt(2, observed.getId());
        }

        return observed;
    }

    public void updateObservedDefault(Abc abc) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(UPDATE_OBSERVED_DEFAULT) ) {
            stmt.setString(1, abc.getValue());
            stmt.setInt(2, abc.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteObservedDefault(Abc abc) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(DELETE_OBSERVED_DEFAULT) ) {
            stmt.setInt(1, abc.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteObserved(int observedId) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(DELETE_OBSERVED) ) {
            stmt.setInt(1, observedId);
            int count = stmt.executeUpdate();
            System.out.println("DELETE_OBSERVED COUNT" + count);
        }
    }

    public void deleteObservedDefaults(int observedId) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(DELETE_OBSERVED_DEFAULTS) ) {
            stmt.setInt(1, observedId);
            int count = stmt.executeUpdate();
            System.out.println("DELETE_OBSERVED_DEFAULTS COUNT" + count);
        }
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DatabaseConstants.CONNECT_URL);
    }
}
