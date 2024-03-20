package com.canalbrewing.abcdatacollector.dal;

public interface IncidentSql {
    String SELECT_INCIDENTS = "SELECT i.id, i.incident_date, i.observed_id, i.user_id, i.duration, i.intensity, i.description, " +
            " ia.observed_abc_id, od.type_cd, od.value" +
            " FROM incidents i" +
            " JOIN incident_abcs ia ON i.id = ia.incident_id" +
            " JOIN observed_defaults od ON ia.observed_abc_id = od.id" +
            " WHERE i.observed_id = ?";
    String INCIDENT_DATE_CLAUSE = " AND i.incident_date >= ?";
    String INCIDENT_ORDER_BY = " ORDER BY i.id, i.incident_date desc";
    String SELECT_INCIDENT = "SELECT i.id, i.incident_date, i.observed_id, i.user_id, i.duration, i.intensity, i.description, " +
            " ia.observed_abc_id, od.type_cd, od.value" +
            " FROM incidents i" +
            " JOIN incident_abcs ia ON i.id = ia.incident_id" +
            " JOIN observed_defaults od ON ia.observed_abc_id = od.id" +
            " WHERE i.id = ?";

    String INSERT_INCIDENT = "INSERT INTO incidents (incident_date, observed_id, user_id, location_id, duration, intensity, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String INSERT_INCIDENT_ABCS = "INSERT INTO incident_abcs (incident_id, observed_abc_id) VALUES (?, ?)";
    String DELETE_INCIDENT = "DELETE FROM incidents WHERE id = ?";
    String DELETE_INCIDENT_ABCS = "DELETE FROM incident_abcs WHERE incident_id = ?";
    String UPDATE_INCIDENT = "UPDATE incidents SET incident_date = ?, user_id = ?, location_id = ?, duration = ?, intensity = ?, description = ? WHERE id = ?";
}
