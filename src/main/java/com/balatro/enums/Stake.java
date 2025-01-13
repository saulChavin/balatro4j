package com.balatro.enums;

import com.balatro.api.Named;

public enum Stake implements Named {
    White_Stake("White Stake"),
    Red_Stake("Red Stake"),
    Green_Stake("Green Stake"),
    Black_Stake("Black Stake"),
    Blue_Stake("Blue Stake"),
    Purple_Stake("Purple Stake"),
    Orange_Stake("Orange Stake"),
    Gold_Stake("Gold Stake");

    private final String name;

    Stake(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
