package com.balatro.enums;

import com.balatro.api.Item;
import com.balatro.jackson.ItemSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ItemSerializer.class)
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
        return 10;
    }
}
