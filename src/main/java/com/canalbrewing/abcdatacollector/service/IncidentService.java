package com.canalbrewing.abcdatacollector.service;

import com.canalbrewing.abcdatacollector.model.Incident;

import java.sql.SQLException;
import java.util.List;
import java.util.OptionalLong;

public interface IncidentService {
    List<Incident> getIncidents(int observedId, Long incidentDate) throws SQLException;
    Incident getIncident(int incidentId) throws SQLException;
    Incident insertIncident(Incident incident) throws SQLException;
    Incident updateIncident(Incident incident) throws SQLException;
    void deleteIncident(int incidentId) throws SQLException;
}
