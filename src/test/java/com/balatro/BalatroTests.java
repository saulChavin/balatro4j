package com.balatro;

import com.balatro.api.Balatro;
import com.balatro.enums.LegendaryJoker;
import com.balatro.enums.RareJoker;
import org.junit.jupiter.api.Test;

public class BalatroTests {

    @Test
    void apiTest() {
        var run = Balatro.builder("2K9H9HN")
                .maxAnte(1)
                .build()
                .analyze();

        System.out.println(run.toJson());
    }

    @Test
    void seedFinderTest() {
        var seeds = Balatro.finder(1, 1_000_000)
                .configuration(config -> config.maxAnte(1))
                .filter(LegendaryJoker.Perkeo.inPack(1)
                        .or(LegendaryJoker.Triboulet.inPack(1))
                        .and(RareJoker.Blueprint.inShop(1)))
                .find();

        System.out.println("Seeds found: " + seeds.size());
    }
}
