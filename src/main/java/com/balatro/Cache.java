package com.balatro;

import org.jetbrains.annotations.NotNull;

public interface Cache {

    Double get(String id);

    void setGeneratedFirstPack(boolean generatedFirstPack);

    boolean isGeneratedFirstPack();

    void put(String id, double value);

    double get(@NotNull Coordinate c);

    void put(@NotNull Coordinate c, double value);
}
