package com.balatro.enums;

import com.balatro.api.Item;

public enum Spectral implements Item {

    Familiar("Familiar"),
    Grim("Grim"),
    Incantation("Incantation"),
    Talisman("Talisman"),
    Aura("Aura"),
    Wraith("Wraith"),
    Sigil("Sigil"),
    Ouija("Ouija"),
    Ectoplasm("Ectoplasm"),
    Immolate("Immolate"),
    Ankh("Ankh"),
    Deja_Vu("Deja Vu"),
    Hex("Hex"),
    Trance("Trance"),
    Medium("Medium"),
    Cryptid("Cryptid"),
    RETRY("RETRY"),
    RETRY2("RETRY");

    private final String name;

    Spectral(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getYIndex() {
        return 5;
    }

}
