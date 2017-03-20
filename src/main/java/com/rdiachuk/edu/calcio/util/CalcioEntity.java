package com.rdiachuk.edu.calcio.util;

/**
 * Created by roman.diachuk on 3/15/2017.
 */
public enum CalcioEntity {

    TEAM("Team"),
    PLAYER("Player");

    private final String value;

    CalcioEntity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
