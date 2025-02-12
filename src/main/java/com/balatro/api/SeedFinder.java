package com.balatro.api;

import java.util.Set;
import java.util.function.Consumer;

public interface SeedFinder {

    SeedFinder filter(Filter filter);

    SeedFinder configuration(Consumer<Balatro> configuration);

    Set<String> find();
}
