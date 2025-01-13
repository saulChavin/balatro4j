package com.balatro.api;

public record InShopFilter(int ante, Named named) implements Filter {

    @Override
    public boolean filter(Run run) {
        return run.hasInShop(ante, named);
    }
}
