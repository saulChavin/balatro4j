package com.balatro.enums;

import com.balatro.api.Named;

public enum Boss implements Named {

    The_Arm("The Arm"),
    The_Club("The Club"),
    The_Eye("The Eye"),
    Amber_Acorn("Amber Acorn"),
    Cerulean_Bell("Cerulean Bell"),
    Crimson_Heart("Crimson Heart"),
    Verdant_Leaf("Verdant Leaf"),
    Violet_Vessel("Violet Vessel"),
    The_Fish("The Fish"),
    The_Flint("The Flint"),
    The_Goad("The Goad"),
    The_Head("The Head"),
    The_Hook("The Hook"),
    The_House("The House"),
    The_Manacle("The Manacle"),
    The_Mark("The Mark"),
    The_Mouth("The Mouth"),
    The_Needle("The Needle"),
    The_Ox("The Ox"),
    The_Pillar("The Pillar"),
    The_Plant("The Plant"),
    The_Psychic("The Psychic"),
    The_Serpent("The Serpent"),
    The_Tooth("The Tooth"),
    The_Wall("The Wall"),
    The_Water("The Water"),
    The_Wheel("The Wheel"),
    The_Windo("The Window");

    private final String name;

    Boss(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public char charAt(int index) {
        return name.charAt(index);
    }
}
