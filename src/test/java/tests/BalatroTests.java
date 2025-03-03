package tests;

import com.balatro.Util;
import com.balatro.api.*;
import com.balatro.enums.Edition;
import com.balatro.enums.RareJoker;
import com.balatro.enums.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.balatro.enums.LegendaryJoker.*;
import static com.balatro.enums.LegendaryJoker.Triboulet;
import static com.balatro.enums.RareJoker.Blueprint;
import static com.balatro.enums.RareJoker.Brainstorm;

public class BalatroTests {

    @Test
    void apiTest() {
        var run = Balatro.builder("ALEX", 8)
                .analyzeAll();

        System.out.println(run.toJson());
    }

    @Test
    void test33JCJSA() {
        var ante = Balatro.builder("33JCJSA", 1)
                .analyzeAll()
                .getFirstAnte();

        Assertions.assertTrue(ante.getTags().contains(Tag.D6_Tag));
        Assertions.assertTrue(ante.getTags().contains(Tag.Charm_Tag));
    }


    @Test
    void testIGSPUNF() {
        var run = Balatro.builder("IGSPUNF", 1)
                .enableAll()
                .disableShopQueue()
                .analyze();

        System.out.println(run.toJson());
    }

    @Test
    void test1234() {
        var json = Balatro.builder("1234", 8)
                .analyze()
                .toJson();

        System.out.println(json);
    }

    @Test
    void testUtilRound13() {
        System.out.println(Util.round13(0.04098230016037929));
    }

    @Test
    void testFilters() {
        var run = Balatro.builder("66HETU9", 8)
                .analyze();

        var found = Perkeo.inPack(1)
                .and(Triboulet.inPack(1))
                .filter(run);

        Assertions.assertTrue(found);

        run = Balatro.builder("IQ6789I", 8)
                .analyze();

        found = Perkeo.inPack()
                .or(Triboulet.inPack(1))
                .and(Blueprint.inShop(1))
                .filter(run);

        Assertions.assertTrue(found);

        run = Balatro.builder("IGSPUNF", 8)
                .analyzeAll();

        found = Perkeo.inPack(1, Edition.Negative)
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

        for (String seed : seeds) {
            var play = Balatro.builder(seed, 8)
                    .analyze();

            System.out.println(play.seed() + " " + play.getScore());
        }
    }

}
