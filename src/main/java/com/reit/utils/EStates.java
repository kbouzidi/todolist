package com.reit.utils;


public enum EStates {
    STARTED("STARTED"),
    ONGOING("ONGOING"),
    DONE("DONE"),
    DELETED("DELETED"),
    POSTPONED("POSTPONED");

    private String value;

    EStates(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}