package com.canalbrewing.abcdatacollector.service;

import com.canalbrewing.abcdatacollector.common.AppConstants;
import com.canalbrewing.abcdatacollector.dal.InitializeDao;
import com.canalbrewing.abcdatacollector.exceptions.AbcDataCollectorException;
import com.canalbrewing.abcdatacollector.model.AppMode;
import com.canalbrewing.abcdatacollector.model.UserSession;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    private final InitializeDao storage;

    public AuthenticationServiceImpl(InitializeDao storage) {
        this.storage = storage;
    }

    public UserSession authenticateUser(String sessionToken) throws AbcDataCollectorException, SQLException {
        UserSession userSession = new UserSession();
        userSession.setSessionToken(sessionToken);

        AppMode appMode = storage.getAppMode();
        if (AppConstants.NO.equals(appMode.getRequireLogin())) {
            userSession.setUserId(0);
        }
        // else
        // find session key
        return userSession;
    }
}
