package com.balatro.api;

import com.balatro.SeedFinderImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Balatro {

    @Contract("_ -> new")
    static @NotNull BalatroBuilder builder(String seed) {
        return new BalatroBuilder(seed);
    }

    static Balatro of(String seed) {
        return builder(seed).build();
    }

    @Contract(" -> new")
    static @NotNull SeedFinder finder() {
        return new SeedFinderImpl();
    }

    @Contract("_, _ -> new")
    static @NotNull SeedFinder finder(int parallelism, int seedsPerThread) {
        return new SeedFinderImpl(parallelism, seedsPerThread);
    }

    Run analyze();
}
