package com.canalbrewing.abcdatacollector.dal;

import com.canalbrewing.abcdatacollector.common.DatabaseConstants;
import com.canalbrewing.abcdatacollector.model.Abc;
import com.canalbrewing.abcdatacollector.model.Incident;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

@Component
public class IncidentDao implements IncidentSql {
    private Connection connection = null;

    public IncidentDao() {
        try {
            this.connect();
        } catch ( SQLException ex ) {
            ex.printStackTrace();
        }
    }

    public List<Incident> getIncidents(int observedId, Long incidentDate) throws SQLException {
        List<Incident> list = new ArrayList<>();

        String sql = SELECT_INCIDENTS;
        if (incidentDate != null) {
            sql += INCIDENT_DATE_CLAUSE;
        }
        sql += INCIDENT_ORDER_BY;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, observedId);
            if (incidentDate != null) {
                stmt.setTimestamp(2, new Timestamp(incidentDate));
            }

            int incidentId = 0;
            int priorIncidentId = -1;
            Incident incident = new Incident();

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    incidentId = rs.getInt(DatabaseConstants.ID);
                    if (incidentId != priorIncidentId) {
                        incident = mapIncident(rs);
                        list.add(incident);
                        priorIncidentId = incidentId;
                    }

                    incident.addAbcValue(mapAbc(rs));
                }
            }
        }

        return list;
    }

    public Incident getIncident(int incidentId) throws SQLException {
        Incident incident = new Incident();

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_INCIDENT)) {
            stmt.setInt(1, incidentId);

            boolean first = true;

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (first) {
                        incident = mapIncident(rs);
                        first = false;
                    }

                    incident.addAbcValue(mapAbc(rs));
                }
            }
        }

        return incident;
    }

    private Incident mapIncident(ResultSet rs) throws SQLException {
        Incident incident = new Incident();
        incident.setId(rs.getInt(DatabaseConstants.ID));
        incident.setObservedId(rs.getInt(DatabaseConstants.OBSERVED_ID));
        incident.setIncidentDate(rs.getTimestamp(DatabaseConstants.INCIDENT_DATE).getTime());
        incident.setUserId(rs.getInt(DatabaseConstants.USER_ID));
        incident.setIntensity(rs.getString(DatabaseConstants.INTENSITY));
        incident.setDuration(rs.getInt(DatabaseConstants.DURATION));
        incident.setDescription(rs.getString(DatabaseConstants.DESCRIPTION));
        return incident;
    }

    private Abc mapAbc(ResultSet rs) throws SQLException {
        Abc abc = new Abc();
        abc.setId(rs.getInt(DatabaseConstants.OBSERVED_ABC_ID));
        abc.setTypeCode(rs.getString(DatabaseConstants.TYPE_CODE));
        abc.setValue(rs.getString(DatabaseConstants.VALUE));
        return abc;
    }

    public Incident insertIncident(Incident incident) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(INSERT_INCIDENT, Statement.RETURN_GENERATED_KEYS) ) {
            stmt.setTimestamp(1, new Timestamp(incident.getIncidentDate()));
            stmt.setInt(2, incident.getObservedId());
            stmt.setInt(3, incident.getUserId());
            stmt.setInt(4, incident.getLocation().getId());
            stmt.setInt(5, incident.getDuration());
            stmt.setString(6, incident.getIntensity());
            stmt.setString(7, incident.getDescription());
            stmt.executeUpdate();

            incident.setId(DatabaseUtils.getGeneratedId(stmt));
        }

        return incident;
    }

    public Incident updateIncident(Incident incident) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(UPDATE_INCIDENT) ) {
            stmt.setTimestamp(1, new Timestamp(incident.getIncidentDate()));
            stmt.setInt(2, incident.getUserId());
            stmt.setInt(3, incident.getLocation().getId());
            stmt.setInt(4, incident.getDuration());
            stmt.setString(5, incident.getIntensity());
            stmt.setString(6, incident.getDescription());
            stmt.setInt(7, incident.getId());
            stmt.executeUpdate();
        }

        return incident;
    }

    public void deleteIncident(int incidentId) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(DELETE_INCIDENT) ) {
            stmt.setInt(1, incidentId);
            stmt.executeUpdate();
        }
    }

    public void insertIncidentAbc(int incidentId, Abc abc) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(INSERT_INCIDENT_ABCS, Statement.RETURN_GENERATED_KEYS) ) {
            stmt.setInt(1, incidentId);
            stmt.setInt(2, abc.getId());
            stmt.executeUpdate();

            abc.setId(DatabaseUtils.getGeneratedId(stmt));
        }
    }

    public void deleteIncidentAbcs(int incidentId) throws SQLException {
        try ( PreparedStatement stmt = connection.prepareStatement(DELETE_INCIDENT_ABCS) ) {
            stmt.setInt(1, incidentId);
            stmt.executeUpdate();
        }
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DatabaseConstants.CONNECT_URL);
    }
}
