package com.canalbrewing.abcdatacollector.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {
    private int userId;
    private String sessionToken;

    @Override
    public String toString() {
        return "UserSession [userId=" + userId + ", sessionToken=" + sessionToken + "]";
    }
}
