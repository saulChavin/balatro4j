package com.balatro;

import com.balatro.api.Balatro;
import com.balatro.api.Run;
import com.balatro.enums.RareJoker;
import com.balatro.enums.UnCommonJoker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.balatro.enums.Boss.The_Arm;
import static com.balatro.enums.Edition.Negative;
import static com.balatro.enums.LegendaryJoker.*;
import static com.balatro.enums.LegendaryJoker.Triboulet;
import static com.balatro.enums.RareJoker.Blueprint;
import static com.balatro.enums.Voucher.Antimatter;

public class BalatroTests {

    @Test
    void apiTest() {
        var run = Balatro.builder("PUSSY")
                .maxAnte(8)
                .build();

        System.out.println(run.toJson());
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
        var seeds = Balatro.search()
                .configuration(config -> config.maxAnte(2))
                .filter(Perkeo.inPack().and(Triboulet.inPack())
                        .and(UnCommonJoker.Sock_and_Buskin.inShop())
                        .and(Blueprint.inShop().or(Blueprint.inBuffonPack())))
                .find();

        System.out.println("Seeds found: " + seeds.size());

        for (Run seed : seeds) {
            System.out.println(seed.toString());
        }
    }
}
