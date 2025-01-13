package com.balatro.api;


public interface Filter {

    boolean filter(Run run);

    default Filter or(Filter filter) {
        return new OrFilter(this, filter);
    }

    default Filter and(Filter filter) {
        return new AndFilter(this, filter);
    }

}
