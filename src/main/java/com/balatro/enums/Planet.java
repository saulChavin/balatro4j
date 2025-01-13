package com.balatro.enums;

import com.balatro.api.Named;

public enum Planet implements Named {

    Mercury("Mercury"),
    Venus("Venus"),
    Earth("Earth"),
    Mars("Mars"),
    Jupiter("Jupiter"),
    Saturn("Saturn"),
    Uranus("Uranus"),
    Neptune("Neptune"),
    Pluto("Pluto"),
    Planet_X("Planet X"),
    Ceres("Ceres"),
    Eri("Eris");

    private final String name;

    Planet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
