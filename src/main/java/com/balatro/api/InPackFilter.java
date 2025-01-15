package com.balatro.api;

public record InPackFilter(int ante, Item item) implements Filter {

    public InPackFilter(Item item) {
        this(-1, item);
    }

    @Override
    public boolean filter(Run run) {
        if (ante == -1) return run.hasInPack(item);
        return run.hasInPack(ante, item);
    }
}
