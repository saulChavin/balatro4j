package com.balatro.api;

public class AndFilter implements Filter {

    private final Filter a;
    private final Filter b;

    public AndFilter(Filter a, Filter b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean filter(Run run) {
        return a.filter(run) && b.filter(run);
    }
}
