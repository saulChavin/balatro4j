package com.balatro.api;

import java.util.List;
import java.util.function.Consumer;

public interface SeedFinder {

    SeedFinder filter(Filter filter);

    SeedFinder configuration(Consumer<Balatro> configuration);

    List<Run> find();
}
