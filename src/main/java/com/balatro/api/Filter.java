package com.balatro.api;


import com.balatro.api.filter.AndFilter;
import com.balatro.api.filter.OrFilter;

public interface Filter {

    boolean filter(Run run);

    default Filter or(Filter filter) {
        return new OrFilter(this, filter);
    }

    default Filter and(Filter filter) {
        return new AndFilter(this, filter);
    }

}
