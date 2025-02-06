package com.balatro.enums;

import com.balatro.api.Item;

public enum Type implements Item {
    Joker("Joker"),
    Tarot("Tarot"),
    Planet("Planet"),
    Spectral("Spectral"),
    PlayingCard("Playing Card");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getYIndex() {
        return -1;
    }
}
