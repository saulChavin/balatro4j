package com.balatro.enums;

import com.balatro.api.Named;

public enum LegendaryJoker implements Named {
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
}
