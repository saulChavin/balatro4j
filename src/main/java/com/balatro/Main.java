package com.balatro;

import com.balatro.api.Balatro;
import com.balatro.api.Run;
import com.balatro.enums.*;

import java.text.DecimalFormat;

import static com.balatro.enums.LegendaryJoker.Perkeo;
import static com.balatro.enums.LegendaryJoker.Triboulet;

public class Main {
    public static void main(String[] args) {
        if (System.getenv("STARTUP_TIMEOUT") != null) {
            long startTime = Long.parseLong(System.getenv("STARTUP_TIMEOUT"));
            long currentTime = System.currentTimeMillis();

            System.out.println("-------------------------------------------------");
            System.out.println("STARTUP TIME: " + (currentTime - startTime) + " ms " + SeedFinderImpl.getMemory());
            System.out.println("-------------------------------------------------");
        }

        var seeds = Balatro.search(10, 10_000_000)
                .configuration(config -> config.maxAnte(1).disableShopQueue()
                        .disablePack(PackKind.Buffoon))
                .filter(Perkeo.inPack().and(Triboulet.inPack()))
                .find();

        System.out.println("Seeds found: " + seeds.size());

        var decimalFormat = new DecimalFormat("0.0");

        for (Run seed : seeds) {
            var play = Balatro.builder(seed.toString(), 8)
                    .analyzeAll()
                    .analyze();

            System.out.println(play.seed() + " " + decimalFormat.format(play.getScore()));
        }
    }
}
