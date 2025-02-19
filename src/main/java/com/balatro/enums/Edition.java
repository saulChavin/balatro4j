package com.balatro.enums;

import com.balatro.api.Item;
import com.balatro.jackson.ItemSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ItemSerializer.class)
public enum Edition implements Item {
    Negative("Negative"),
    Polychrome("Polychrome"),
    Holographic("Holographic"),
    Foil("Foil"),
    NoEdition("No Edition"),
    Eternal("Eternal"),
    Perishable("Perishable"),
    Rental("Rental");

    private final String name;

    Edition(String name) {
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
