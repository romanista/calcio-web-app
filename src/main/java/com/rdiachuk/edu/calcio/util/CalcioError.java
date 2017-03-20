package com.rdiachuk.edu.calcio.util;

/**
 * Created by roman.diachuk on 3/15/2017.
 */
public enum CalcioError {
    TEAM_NOT_FOUND(0, "Team not found."),
    DUPLICATE_TEAM(1, "Team already exists."),

    /**
     * arg - failure reason
     */
    UPDATE_FAILURE(2, "Failed to update. Reason: %s"),

    /**
     * arg - failure reason
     */
    CREATE_FAILURE(3, "Failed to create. Reason: %s"),

    /**
     * arg - failure reason
     */
    RETRIEVE_FAILURE(4, "Failed to get. Reason: %s"),

    /**
     * arg - failure reason
     */
    DELETE_FAILURE(5, "Failed to delete. Reason: %s"),

    DUPLICATE_PLAYER(6, "Player already exists."),
    PLAYER_NOT_FOUND(7, "Player not found.");

    private final int code;
    private final String description;

    CalcioError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
