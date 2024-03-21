package com.canalbrewing.abcdatacollector.service;

import com.canalbrewing.abcdatacollector.model.AppMode;
import com.canalbrewing.abcdatacollector.model.Relationship;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public interface InitializeService {
    AppMode getAppMode() throws SQLException;

    AppMode updateAppMode(AppMode appMode) throws SQLException;

    List<Relationship> getRelationships() throws SQLException;
}
