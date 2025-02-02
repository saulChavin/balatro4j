package com.balatro.api;

import com.balatro.SeedFinderImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Balatro {

    @Contract("_,_ -> new")
    static @NotNull BalatroBuilder builder(String seed, int maxAnte) {
        return new BalatroBuilder(seed, maxAnte);
    }

    @Contract(" -> new")
    static @NotNull SeedFinder search() {
        return new SeedFinderImpl();
    }

    @Contract("_, _ -> new")
    static @NotNull SeedFinder search(int parallelism, int seedsPerThread) {
        return new SeedFinderImpl(parallelism, seedsPerThread);
    }

    Run analyze();
}
