package com.canalbrewing.abcdatacollector.service;

import com.canalbrewing.abcdatacollector.model.Abc;
import com.canalbrewing.abcdatacollector.model.Observed;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

public interface ObservedService {
    List<Observed> getAllObserved(int userId) throws SQLException;
    Observed insertObserved(Observed observed) throws SQLException;
    Observed updateObserved(Observed observed) throws SQLException;
    void deleteObserved(int observedId) throws SQLException;
    Abc insertObservedAbc(int observedId, Abc abc) throws SQLException;
    Observed getObserved(int observedId) throws SQLException;
    Observed getDefaultObserved();
}
