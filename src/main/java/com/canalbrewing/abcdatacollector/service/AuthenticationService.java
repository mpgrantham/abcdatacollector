package com.canalbrewing.abcdatacollector.service;

import com.canalbrewing.abcdatacollector.exceptions.AbcDataCollectorException;
import com.canalbrewing.abcdatacollector.model.UserSession;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

public interface AuthenticationService {
    UserSession authenticateUser(String sessionToken) throws AbcDataCollectorException, SQLException;
}
