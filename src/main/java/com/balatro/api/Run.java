package com.balatro.api;

import com.balatro.enums.LegendaryJoker;

public interface Run {

    boolean hasInPack(String name);

    boolean hasInPack(int ante, String name);

    boolean hasInShop(Named named);

    boolean hasInShop(int ante, Named named);

    long countLegendary();

    long countLegendary(int ante);

    boolean hasInShop(int ante, Named named, int index);

    boolean hasLegendary(int ante, LegendaryJoker... jokers);

    String toJson();

    Ante getAnte(int ante);

    default Ante getFirstAnte() {
        return getAnte(1);
    }
}
