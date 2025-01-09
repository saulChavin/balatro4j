package com.balatro.enums;

public enum Seal implements Named {
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
}
