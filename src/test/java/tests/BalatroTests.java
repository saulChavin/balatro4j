package tests;

import com.balatro.Util;
import com.balatro.api.Balatro;
import com.balatro.api.Run;
import com.balatro.cache.Data;
import com.balatro.cache.JokerFile;
import com.balatro.cache.PreProcessedSeeds;
import com.balatro.cache.Query;
import com.balatro.enums.Boss;
import com.balatro.enums.Edition;
import com.balatro.enums.Tag;
import com.balatro.enums.Voucher;
import com.balatro.structs.EditionItem;
import com.balatro.structs.ItemPosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.balatro.enums.CommonJoker.Square_Joker;
import static com.balatro.enums.LegendaryJoker.Perkeo;
import static com.balatro.enums.LegendaryJoker.Triboulet;
import static com.balatro.enums.RareJoker.*;
import static com.balatro.enums.UnCommonJoker.Burglar;
import static com.balatro.enums.UnCommonJoker.Certificate;

public class BalatroTests {

    @Test
    void apiTest() {
        var run = Balatro.builder("ALEX", 8)
                .analyzeAll();

        System.out.println(run.toJson());
    }

    @Test
    void testParseSearch() {
        PreProcessedSeeds.parseSearch(List.of(new Query("Triboulet"),
                new Query("Sock and Buskin"),
                new Query("Pareidolia")
        ));
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
    void testJHZ7FPM() {
        var ante = Balatro.builder("JHZ7FPM", 1)
                .analyzeAll()
                .getFirstAnte();

        var json = ante
                .toJson();

        System.out.println(json);

        Assertions.assertEquals(Voucher.Hieroglyph, ante.getVoucher());
        Assertions.assertEquals(Boss.The_Head, ante.getBoss());
        Assertions.assertTrue(ante.hasLegendary(Perkeo));
        Assertions.assertTrue(ante.hasLegendary(Triboulet));


    }

    @Test
    void testComplexQuery() {
        var seeds = Balatro.search(1, 1_000)
                .configuration(config -> config.maxAnte(4))
                .filter(Square_Joker.inShop(1, Edition.Negative).and(Burglar.inBuffonPack(1))
                        .and(Blueprint.inBuffonPack(2).or(Blueprint.inShop(2)))
                        .and(Brainstorm.inBuffonPack(2).or(Brainstorm.inShop(2)))
                        .and(Invisible_Joker.inBuffonPack(3).or(Invisible_Joker.inShop(3)))
                        .and(Certificate.inShop(4).or(Certificate.inBuffonPack(4))))
                .find();

        seeds.forEach(System.out::println);
    }


    @Test
    void testJokerFile() throws IOException {
        List<Run> runs = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            runs.add(Balatro.builder("123456" + i, 8)
                    .analyzeAll());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        var items = new HashMap<String, List<EditionItem>>();

        for (Run run : runs) {
            JokerFile.write(baos, new Data(run));
            items.put(run.seed(), run.antes().stream()
                    .flatMap(a -> a.getJokers().stream())
                    .toList());
        }

        var bis = new ByteArrayInputStream(baos.toByteArray());

        var data = JokerFile.read(bis);

        if (data.isEmpty()) {
            Assertions.fail("No data found");
        }

        var seeds = runs.stream()
                .map(Run::seed)
                .collect(Collectors.toSet());

        for (Data d : data) {
            if (!seeds.contains(d.getSeed())) {
                Assertions.fail("Seed not found: " + d.getSeed());
            }

            if (d.getData().length == 0) {
                Assertions.fail("Positions not found");
            }
        }

        if (data.size() != runs.size()) {
            Assertions.fail("Data size mismatch");
        }

        for (Data seed : data) {
            var itemList = items.get(seed.getSeed());

            Assertions.assertNotNull(itemList);
            Assertions.assertFalse(itemList.isEmpty());

            for (EditionItem editionItem : itemList) {
                Assertions.assertTrue(seed.isOn(new ItemPosition(editionItem, 8)));
            }
        }

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

    // @Test
    @Order(Integer.MAX_VALUE)
    void seedSearchTest() {
        var seeds = Balatro.search(10, 1_000_000)
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
