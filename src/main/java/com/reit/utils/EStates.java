package com.reit.utils;


public enum EStates {
    TODO("TODO"),
    ONGOING("ONGOING"),
    DONE("DONE");

    private String value;

    EStates(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}