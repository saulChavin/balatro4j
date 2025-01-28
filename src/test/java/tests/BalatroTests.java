package tests;

import com.balatro.Util;
import com.balatro.api.*;
import com.balatro.enums.RareJoker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.balatro.enums.LegendaryJoker.*;
import static com.balatro.enums.LegendaryJoker.Triboulet;
import static com.balatro.enums.RareJoker.Blueprint;
import static com.balatro.enums.RareJoker.Brainstorm;

public class BalatroTests {

    @Test
    void apiTest() {
        var run = Balatro.builder("PUSSY")
                .maxAnte(8)
                .build();

        System.out.println(run.toJson());
    }

    @Test
    void test1234() {
        var json = Balatro.builder("1234")
                .maxAnte(8)
                .build()
                .toJson();

        System.out.println(json);
    }

    @Test
    void testUtilRound13() {
        System.out.println(Util.round13(0.04098230016037929));
    }

    @Test
    void testFilters() {
        var run = Balatro.builder("66HETU9")
                .maxAnte(8)
                .build();

        var found = Perkeo.inPack(1)
                .and(Triboulet.inPack(1))
                .filter(run);

        Assertions.assertTrue(found);

        run = Balatro.builder("IQ6789I")
                .maxAnte(8)
                .build();

        found = Perkeo.inPack()
                .or(Triboulet.inPack(1))
                .and(Blueprint.inShop(1))
                .filter(run);

        Assertions.assertTrue(found);
    }

    @Test
    void seedSearchTest() {
        var seeds = Balatro.search(10, 10_000_000)
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
