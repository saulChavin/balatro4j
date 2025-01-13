package com.balatro.enums;

import com.balatro.api.Named;

public enum Edition implements Named {
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
}
