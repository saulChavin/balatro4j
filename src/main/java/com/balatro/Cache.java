package com.balatro;

import java.util.HashMap;
import java.util.Map;

final class Cache {

    final Map<String, Double> nodes;
    boolean generatedFirstPack;

    public Cache() {
        nodes = new HashMap<>();
        generatedFirstPack = false;
    }
}
