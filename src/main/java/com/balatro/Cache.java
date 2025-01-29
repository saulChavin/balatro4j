package com.balatro;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

final class Cache {

    private final Map<String, Double> nodes;
    private boolean generatedFirstPack;

    public Cache() {
        nodes = new HashMap<>();
        generatedFirstPack = false;
    }

    public boolean isGeneratedFirstPack() {
        return generatedFirstPack;
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
