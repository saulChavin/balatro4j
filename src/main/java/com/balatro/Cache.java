package com.balatro;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    public final Map<String, Double> nodes;
    public boolean generatedFirstPack;

    public Cache() {
        nodes = new HashMap<>();
        generatedFirstPack = false;
    }
}
