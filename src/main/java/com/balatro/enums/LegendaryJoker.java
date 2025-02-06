package com.balatro.enums;

import com.balatro.api.Item;

public enum LegendaryJoker implements Item {
    Canio("Canio"),
    Triboulet("Triboulet"),
    Yorick("Yorick"),
    Chicot("Chicot"),
    Perkeo("Perkeo");

    private final String name;

    LegendaryJoker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getYIndex() {
        return 12;
    }
}
