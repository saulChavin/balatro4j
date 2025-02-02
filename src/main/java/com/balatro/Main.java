package com.balatro;

import com.balatro.api.Balatro;
import com.balatro.api.Run;
import com.balatro.enums.PackKind;

import java.text.DecimalFormat;

import static com.balatro.enums.LegendaryJoker.Perkeo;
import static com.balatro.enums.LegendaryJoker.Triboulet;

public class Main {
    public static void main(String[] args) {
        var seeds = Balatro.search(10, 1_000_000)
                .configuration(config -> config.maxAnte(1).disableShopQueue()
                        .disablePack(PackKind.Buffoon))
                .filter(Perkeo.inPack().and(Triboulet.inPack()))
                .find();

        System.out.println("Seeds found: " + seeds.size());

        var decimalFormat = new DecimalFormat("0.0");

        for (Run seed : seeds) {
            var play = Balatro.builder(seed.toString(), 8)
                    .analyzeAll()
                    .build();

            System.out.println(play.seed() + " " + decimalFormat.format(play.getScore()));
        }
    }
}
