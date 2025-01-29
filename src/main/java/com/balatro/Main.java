package com.balatro;

import com.balatro.api.Balatro;
import com.balatro.api.Run;

import static com.balatro.enums.LegendaryJoker.Perkeo;
import static com.balatro.enums.LegendaryJoker.Triboulet;
import static com.balatro.enums.RareJoker.Blueprint;

public class Main {
    public static void main(String[] args) {
        var seeds = Balatro.search(10, 1_000_000)
                .configuration(config -> config.maxAnte(1))
                .filter(Perkeo.inPack().and(Triboulet.inPack()).and(Blueprint.inShop()))
                .find();

        System.out.println("Seeds found: " + seeds.size());

        for (Run seed : seeds) {
            var play = Balatro.builder(seed.toString())
                    .build();

            System.out.println(play.seed() + " " + play.getScore());
        }
    }
}
