package com.balatro;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

final class CacheMap implements Cache {

    private final Map<String, Double> nodes;
    private boolean generatedFirstPack;

    public CacheMap() {
        nodes = new LinkedHashMap<>();
        generatedFirstPack = false;
    }

    public boolean isGeneratedFirstPack() {
        return generatedFirstPack;
    }

    @Override
    public void put(String id, double value) {
        nodes.put(id, value);
    }

    @Override
    public double get(@NotNull Coordinate c) {
        return nodes.getOrDefault(c.value(), -1.0);
    }

    @Override
    public void put(@NotNull Coordinate c, double value) {
        nodes.put(c.value(), value);
    }

    public void setGeneratedFirstPack(boolean generatedFirstPack) {
        this.generatedFirstPack = generatedFirstPack;
    }

    public @Nullable Double get(String key) {
        return nodes.get(key);
    }

    public void put(String key, Double value) {
        nodes.put(key, value);
    }
}


