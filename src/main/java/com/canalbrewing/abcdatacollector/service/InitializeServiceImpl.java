package com.canalbrewing.abcdatacollector.service;

import com.canalbrewing.abcdatacollector.common.AppConstants;
import com.canalbrewing.abcdatacollector.dal.InitializeDao;
import com.canalbrewing.abcdatacollector.model.AppMode;
import com.canalbrewing.abcdatacollector.model.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

@Component
public class InitializeServiceImpl implements InitializeService {
    Logger logger = LoggerFactory.getLogger(InitializeServiceImpl.class);

    private final static String NETWORK_PREFIX = "192.168";
    private final InitializeDao storage;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    public InitializeServiceImpl(InitializeDao storage) {
        this.storage = storage;
    }

    public AppMode getAppMode() throws SQLException {
        AppMode appMode = storage.getAppMode();
        if (AppConstants.NO.equals(appMode.getRequireLogin())) {
            appMode.setSessionToken(UUID.randomUUID().toString().replace(AppConstants.DASH, AppConstants.EMPTY));
        }

        String hostAddress = null;
        String hostName = null;

        try {
            Enumeration<NetworkInterface> ne = NetworkInterface.getNetworkInterfaces();
            while (ne.hasMoreElements()) {
                NetworkInterface n = ne.nextElement();
                Enumeration<InetAddress> ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = ee.nextElement();
                    if ( i.getHostAddress().startsWith(NETWORK_PREFIX)) {
                        hostAddress = i.getHostAddress();
                        hostName = i.getHostName();
                    }
                }
            }

            if (hostAddress == null) {
                InetAddress IP = InetAddress.getLocalHost();
                hostAddress = IP.getHostAddress();
                hostName = IP.getHostName();
            }

            appMode.setIpAddress(hostAddress);
            appMode.setHostName(hostName);
            appMode.setPort(serverPort);
        } catch ( Exception ex ) {
            logger.error("Network Interface Error {}", ex.getMessage(), ex);
        }

        return appMode;
    }

    public AppMode updateAppMode(AppMode appMode) throws SQLException {
        return storage.updateAppMode(appMode);
    }

    public List<Relationship> getRelationships() throws SQLException {
        return storage.getRelationships();
    }
}
