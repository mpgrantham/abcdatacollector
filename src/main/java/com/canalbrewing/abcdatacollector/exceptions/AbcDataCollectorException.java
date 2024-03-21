package com.canalbrewing.abcdatacollector.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class AbcDataCollectorException extends Exception {
    @Serial
    private static final long serialVersionUID = 280591020970526872L;

    public static final int INVALID_SESSION = 1000;
    public static final int EXPIRED_SESSION = 1010;
    public static final int NOT_FOUND = 400;
    public static final int INVALID_LOGIN = 500;
    public static final int DUPLICATE = 600;
    public static final int INVALID_DELETE = 700;
    public static final int UNAUTHORIZED = 403;

    public static final int OTHER = 2000;

    private int exceptionCode = 0;

    public AbcDataCollectorException() {

    }

    public AbcDataCollectorException(int exceptionCode, String message) {
        super(message);

        this.exceptionCode = exceptionCode;
    }
}
