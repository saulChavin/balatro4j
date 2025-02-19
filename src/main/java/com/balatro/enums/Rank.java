package com.balatro.enums;

import com.balatro.api.Item;
import com.balatro.jackson.ItemSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ItemSerializer.class)
public enum Rank implements Item {
    R_2("2"),
    R_3("3"),
    R_4("4"),
    R_5("5"),
    R_6("6"),
    R_7("7"),
    R_8("8"),
    R_9("9"),
    R_10("10"),
    R_Jack("Jack"),
    R_Queen("Queen"),
    R_King("King"),
    R_Ace("Ace");

    private final String name;

    Rank(String name) {
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
