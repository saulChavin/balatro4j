package com.balatro.api;

public record InPackFilter(int ante, Named named) implements Filter {
    @Override
    public boolean filter(Run run) {
        return run.hasInPack(ante, named.getName());
    }
}
