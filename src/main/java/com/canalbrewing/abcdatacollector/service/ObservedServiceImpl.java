package com.canalbrewing.abcdatacollector.service;

import com.canalbrewing.abcdatacollector.common.AppConstants;
import com.canalbrewing.abcdatacollector.dal.ObservedDao;
import com.canalbrewing.abcdatacollector.dal.ObservedSql;
import com.canalbrewing.abcdatacollector.model.Abc;
import com.canalbrewing.abcdatacollector.model.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ObservedServiceImpl implements ObservedService, ObservedSql {
    private final ObservedDao dao;

    @Autowired
    public ObservedServiceImpl(ObservedDao dao) {
        this.dao = dao;
    }

    public List<Observed> getAllObserved(int userId) throws SQLException {
        List<Observed> list = dao.getAllObserved(userId);
        for (Observed observed : list) {
            setObservedDefaults(observed);
        }

        return list;
    }

    public Observed getObserved(int observedId) throws SQLException {
        Observed observed = dao.getObserved(observedId);
        setObservedDefaults(observed);

        return observed;
    }

    public Observed getDefaultObserved() {
        Observed observed = new Observed();
        observed.setObservedName(AppConstants.EMPTY);
        setObservedDefaults(observed, DEFAULT_LOCATIONS, Abc.LOCATION);
        setObservedDefaults(observed, DEFAULT_ANTECEDENTS, Abc.ANTECEDENT);
        setObservedDefaults(observed, DEFAULT_BEHAVIORS, Abc.BEHAVIOR);
        setObservedDefaults(observed, DEFAULT_CONSEQUENCES, Abc.CONSEQUENCE);

        return observed;
    }

    private void setObservedDefaults(Observed observed) throws SQLException {
        List<Abc> abcs = dao.getObservedDefaults(observed.getId());

        for (Abc abc : abcs) {
            observed.addAbcValue(abc);
        }
    }

    public Observed insertObserved(Observed observedInput) throws SQLException {
        Observed observed = dao.insertObserved(observedInput);

        insertObservedAbcs(observed, observedInput.getLocations());
        insertObservedAbcs(observed, observedInput.getAntecedents());
        insertObservedAbcs(observed, observedInput.getBehaviors());
        insertObservedAbcs(observed, observedInput.getConsequences());

        return observed;
    }

    public Observed updateObserved(Observed observedInput) throws SQLException {
        Observed observed = dao.updateObserved(observedInput);

        // Get all exists
        List<Abc> abcs = dao.getObservedDefaults(observedInput.getId());

        updateAbcs(observed, observedInput.getAntecedents(), abcs.stream().filter((a -> a.getTypeCode().equals(Abc.ANTECEDENT))).toList());
        updateAbcs(observed, observedInput.getBehaviors(), abcs.stream().filter((a -> a.getTypeCode().equals(Abc.BEHAVIOR))).toList());
        updateAbcs(observed, observedInput.getConsequences(), abcs.stream().filter((a -> a.getTypeCode().equals(Abc.CONSEQUENCE))).toList());
        updateAbcs(observed, observedInput.getLocations(), abcs.stream().filter((a -> a.getTypeCode().equals(Abc.LOCATION))).toList());

        observed.resetDefaults();

        setObservedDefaults(observed);

        return observed;
    }

    public Abc insertObservedAbc(int observedId, Abc abc) throws SQLException {
        return dao.insertObservedDefault(observedId, abc);
    }

    private void updateAbcs(Observed observed, List<Abc> values, List<Abc> existing) throws SQLException {
        List<Abc> adds = new ArrayList<>();
        List<Abc> updates = new ArrayList<>();
        List<Abc> deletes = new ArrayList<>();

        for (Abc abc : values) {
            Optional<Abc> optional = existing.stream().filter(a -> a.getId() == abc.getId()).findFirst();
            if (optional.isPresent()) {
                // update
                if (!optional.get().getValue().equals(abc.getValue())) {
                    updates.add(abc);
                }
            } else {
                adds.add(abc);
            }
        }

        for (Abc abc : existing) {
            Optional<Abc> optional = values.stream().filter(a -> a.getId() == abc.getId()).findFirst();
            if (optional.isEmpty()) {
                deletes.add(abc);
            }
        }

        for (Abc abc : deletes) {
            dao.deleteObservedDefault(abc);
        }

        for (Abc abc : updates) {
            dao.updateObservedDefault(abc);
        }

        insertObservedAbcs(observed, adds);
    }

    public void deleteObserved(int observedId) throws SQLException {
        System.out.println("DELETE " + observedId);
        dao.deleteObserved(observedId);
        dao.deleteObservedDefaults(observedId);
    }

    private void insertObservedAbcs(Observed observed, List<Abc> values) throws SQLException {
        for (Abc abc : values) {
            dao.insertObservedDefault(observed.getId(), abc);
        }
    }

    private void setObservedDefaults(Observed observed, String[] values, String typeCode) {
        for (String value : values) {
            Abc abc = new Abc();
            abc.setTypeCode(typeCode);
            abc.setValue(value);
            abc.setActiveFl(AppConstants.YES);
            observed.addAbcValue(abc);
        }
    }

}
