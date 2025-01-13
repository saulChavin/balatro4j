package com.balatro.enums;

import com.balatro.api.Named;

public enum PackType implements Named {

    RETRY("RETRY", 22.42),

    Arcana_Pack("Arcana Pack", 4),
    Jumbo_Arcana_Pack("Jumbo Arcana Pack", 2),
    Mega_Arcana_Pack("Mega Arcana Pack", 0.5),

    Celestial_Pack("Celestial Pack", 4),
    Jumbo_Celestial_Pack("Jumbo Celestial Pack", 2),
    Mega_Celestial_Pack("Mega Celestial Pack", 0.5),

    Standard_Pack("Standard Pack", 4),
    Jumbo_Standard_Pack("Jumbo Standard Pack", 2),
    Mega_Standard_Pack("Mega Standard Pack", 0.5),

    Buffoon_Pack("Buffoon Pack", 1.2),
    Jumbo_Buffoon_Pack("Jumbo Buffoon Pack", 0.6),
    Mega_Buffoon_Pack("Mega Buffoon Pack", 0.15),

    Spectral_Pack("Spectral Pack", 0.6),
    Jumbo_Spectral_Pack("Jumbo Spectral Pack", 0.3),
    Mega_Spectral_Pack("Mega Spectral Pack", 0.07);

    private final String name;
    private final double value;

    PackType(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public PackKind getKind() {
        return switch (this) {
            case Arcana_Pack, Jumbo_Arcana_Pack, Mega_Arcana_Pack -> PackKind.Arcana;
            case Celestial_Pack, Jumbo_Celestial_Pack, Mega_Celestial_Pack -> PackKind.Celestial;
            case Standard_Pack, Jumbo_Standard_Pack, Mega_Standard_Pack -> PackKind.Standard;
            case Buffoon_Pack, Jumbo_Buffoon_Pack, Mega_Buffoon_Pack -> PackKind.Buffoon;
            case Spectral_Pack, Jumbo_Spectral_Pack, Mega_Spectral_Pack -> PackKind.Spectral;
            default -> throw new IllegalArgumentException("Invalid pack type: " + this);
        };
    }

    public boolean isMega() {
        return this == Mega_Arcana_Pack || this == Mega_Celestial_Pack || this == Mega_Standard_Pack || this == Mega_Buffoon_Pack || this == Mega_Spectral_Pack;
    }

    public boolean isJumbo() {
        return this == Jumbo_Arcana_Pack || this == Jumbo_Celestial_Pack || this == Jumbo_Standard_Pack || this == Jumbo_Buffoon_Pack || this == Jumbo_Spectral_Pack;
    }

    public boolean isBuffon() {
        return this == Buffoon_Pack || this == Jumbo_Buffoon_Pack || this == Mega_Buffoon_Pack;
    }

    public boolean isSpectral() {
        return this == Spectral_Pack || this == Jumbo_Spectral_Pack || this == Mega_Spectral_Pack;
    }
}
