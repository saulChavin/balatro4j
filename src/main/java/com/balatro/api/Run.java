package com.balatro.api;


public interface Run extends Queryable {

    String seed();

    String toJson();

    Ante getAnte(int ante);

    default Ante getFirstAnte() {
        return getAnte(1);
    }
}
