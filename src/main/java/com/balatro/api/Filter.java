package com.balatro.api;


import com.balatro.api.filter.AndFilter;
import com.balatro.api.filter.OrFilter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Filter {

    boolean filter(Run run);

    default Filter or(Filter filter) {
        return new OrFilter(this, filter);
    }

    default Filter and(Filter filter) {
        return new AndFilter(this, filter);
    }


    @Contract(pure = true)
    static @NotNull Filter findAll() {
        return run -> true;
    }
}
