package com.canalbrewing.abcdatacollector.service;

import com.canalbrewing.abcdatacollector.dal.IncidentDao;
import com.canalbrewing.abcdatacollector.model.Abc;
import com.canalbrewing.abcdatacollector.model.Incident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class IncidentServiceImpl implements IncidentService {
    private final IncidentDao dao;

    @Autowired
    public IncidentServiceImpl(IncidentDao dao) {
        this.dao = dao;
    }

    public List<Incident> getIncidents(int observedId, Long incidentDate) throws SQLException {
        return dao.getIncidents(observedId, incidentDate);
    }

    public Incident getIncident(int incidentId) throws SQLException {
        return dao.getIncident(incidentId);
    }

    public Incident insertIncident(Incident incidentInput) throws SQLException {
        Incident incident = dao.insertIncident(incidentInput);

        insertIncidentAbcs(incident);

        return incident;
    }

    public Incident updateIncident(Incident incidentInput) throws SQLException {
        Incident incident = dao.updateIncident(incidentInput);

        dao.deleteIncidentAbcs(incident.getId());

        insertIncidentAbcs(incident);

        return incident;
    }

    private void insertIncidentAbcs(Incident incident) throws SQLException {
        dao.insertIncidentAbc(incident.getId(), incident.getLocation());

        for (Abc abc : incident.getAntecedents()) {
            dao.insertIncidentAbc(incident.getId(), abc);
        }

        for (Abc abc : incident.getBehaviors()) {
            dao.insertIncidentAbc(incident.getId(), abc);
        }

        for (Abc abc : incident.getConsequences()) {
            dao.insertIncidentAbc(incident.getId(), abc);
        }
    }

    public void deleteIncident(int incidentId) throws SQLException {
        dao.deleteIncidentAbcs(incidentId);
        dao.deleteIncident(incidentId);
    }
}
