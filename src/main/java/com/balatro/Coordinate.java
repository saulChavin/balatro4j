package com.balatro;

import org.jetbrains.annotations.NotNull;

public record Coordinate(String value, int ante, int y) {



    public @NotNull String resample(int r) {
        return "%s_resample%s".formatted(value, r);
    }
}
