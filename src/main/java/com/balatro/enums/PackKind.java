package com.balatro.enums;

public enum PackKind {
    Celestial("Celestial"),
    Arcana("Arcana"),
    Standard("Standard"),
    Buffoon("Buffoon"),
    Spectral("Spectral");

    private final String name;

    PackKind(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
