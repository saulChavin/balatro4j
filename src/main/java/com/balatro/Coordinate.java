package com.balatro;

import org.jetbrains.annotations.NotNull;

public record Coordinate(String value, int ante, int y) {

    public @NotNull String resample(int r) {
        return "%s_resample%s".formatted(value, r);
    }

    public double pseudohash(String seed) {
        return Util.pseudohash(value + seed);
    }

    @Override
    public String toString() {
        return value;
    }
}
