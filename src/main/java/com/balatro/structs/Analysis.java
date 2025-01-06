package com.balatro.structs;

import com.balatro.enums.LegendaryJoker;
import com.balatro.enums.Named;

import java.util.List;

public record Analysis(List<Ante> antes) {

    public boolean containsPack(int ante, String name) {
        return antes.get(ante - 1).containsInPack(name);
    }

    public boolean hasLegendary(int ante, LegendaryJoker joker) {
        return antes.get(ante - 1).hasLegendary(joker);
    }

    public boolean hasInShop(int ante, Named named) {
        return antes.get(ante - 1).hasInShop(named.getName());
    }
}
