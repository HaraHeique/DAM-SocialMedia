package com.example.socialmedia.Enums;

public enum TimelineType {
    ALL_WORLD(0),
    MY_WORLD(1),
    ONLY_ME(2);

    private final int value;

    private TimelineType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
