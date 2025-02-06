package com.balatro.enums;

import com.balatro.api.Item;

public enum Seal implements Item {
    NoSeal("No Seal"),
    RedSeal("Red Seal"),
    BlueSeal("Blue Seal"),
    GoldSeal("Gold Seal"),
    PurpleSeal("Purple Seal");

    private final String name;

    Seal(String name) {
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
