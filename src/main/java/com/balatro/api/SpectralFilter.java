package com.balatro.api;

public record SpectralFilter(int ante, Item item) implements Filter {

    public SpectralFilter(Item item) {
        this(-1, item);
    }

    @Override
    public boolean filter(Run run) {
        if (ante == -1) {
            return run.hasInSpectral(item);
        }
        return run.hasInSpectral(ante, item);
    }
}
