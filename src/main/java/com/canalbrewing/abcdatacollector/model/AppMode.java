package com.canalbrewing.abcdatacollector.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppMode {
    private int id;
    private String setupComplete;
    private String requireLogin;
    private String startPage;
    private String sessionToken;
    private String ipAddress;
    private String hostName;
    private String port;

    @Override
    public String toString() {
        return "AppMode [id=" + id + ", requireLogin=" + requireLogin + ", startPage=" + startPage + ", sessionToken=" + sessionToken + "]";
    }
}
