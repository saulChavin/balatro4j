package com.balatro.structs;

import com.balatro.enums.LegendaryJoker;
import com.balatro.enums.Named;

import java.util.List;

public record Analysis(List<Ante> antes) {

    public boolean hasLegendary(int ante, LegendaryJoker joker) {
        return antes.get(ante - 1).hasLegendary(joker);
    }

    public boolean hasInPack(int ante, String name) {
        return antes.get(ante - 1).containsInPack(name);
    }

    public boolean hasInShop(int ante, Named named) {
        return antes.get(ante - 1).hasInShop(named.getName());
    }

    public boolean hasInShop(int ante, Named named, int index) {
        return antes.get(ante - 1).hasInShop(named.getName(), index);
    }

    public long countLegendary() {
        return antes.stream()
                .filter(a -> a.containsInPack("The Soul")).count();
    }

    public long countLegendary(int ante) {
        return antes.get(ante - 1).countInPack("The Soul");
    }
}
