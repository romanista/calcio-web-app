package com.rdiachuk.edu.calcio.util;

/**
 * Created by roman.diachuk on 3/13/2017.
 */
public class CalcioErrorResponseType {

    private final String errorMessage;

    public CalcioErrorResponseType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
