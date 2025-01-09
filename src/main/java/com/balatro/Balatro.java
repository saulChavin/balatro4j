package com.balatro;

import com.balatro.enums.*;
import com.balatro.structs.*;
import com.balatro.structs.Card;
import com.balatro.structs.Pack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Balatro {

    public static final List<String> options = Arrays.asList(
            "Negative Tag",
            "Foil Tag",
            "Holographic Tag",
            "Polychrome Tag",
            "Rare Tag",
            "Golden Ticket",
            "Mr. Bones",
            "Acrobat",
            "Sock and Buskin",
            "Swashbuckler",
            "Troubadour",
            "Certificate",
            "Smeared Joker",
            "Throwback",
            "Hanging Chad",
            "Rough Gem",
            "Bloodstone",
            "Arrowhead",
            "Onyx Agate",
            "Glass Joker",
            "Showman",
            "Flower Pot",
            "Blueprint",
            "Wee Joker",
            "Merry Andy",
            "Oops! All 6s",
            "The Idol",
            "Seeing Double",
            "Matador",
            "Hit the Road",
            "The Duo",
            "The Trio",
            "The Family",
            "The Order",
            "The Tribe",
            "Stuntman",
            "Invisible Joker",
            "Brainstorm",
            "Satellite",
            "Shoot the Moon",
            "Driver's License",
            "Cartomancer",
            "Astronomer",
            "Burnt Joker",
            "Bootstraps",
            "Overstock Plus",
            "Liquidation",
            "Glow Up",
            "Reroll Glut",
            "Omen Globe",
            "Observatory",
            "Nacho Tong",
            "Recyclomancy",
            "Tarot Tycoon",
            "Planet Tycoon",
            "Money Tree",
            "Antimatter",
            "Illusion",
            "Petroglyph",
            "Retcon",
            "Palette"
    );

    static final String CHARACTERS = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateRandomString() {
        StringBuilder result = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            int index = ThreadLocalRandom.current().nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("Searching...");

        ForkJoinTask<?> submit = ForkJoinPool.commonPool().submit(Balatro::generate);
        ForkJoinPool.commonPool().submit(Balatro::generate);
        ForkJoinPool.commonPool().submit(Balatro::generate);
        ForkJoinPool.commonPool().submit(Balatro::generate);
        ForkJoinPool.commonPool().submit(Balatro::generate);
        ForkJoinPool.commonPool().submit(Balatro::generate);
        ForkJoinPool.commonPool().submit(Balatro::generate);
        ForkJoinPool.commonPool().submit(Balatro::generate);

        int time = 0;

        while (!submit.isDone()) {
            try {
                Thread.sleep(1000);

                time++;

                if (time % 10 == 0) {
                    System.out.println("Ops per second: " + count.get() / time + " ops/s seeds analyzed: " + count.get());
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Ops per second: " + (count.get() / time) + ", seeds analyzed: " + count.get());

        var result = new Balatro()
                .performAnalysis("2K9H9HN");

        System.out.println(result.hasLegendary(2, LegendaryJoker.Perke, LegendaryJoker.Triboulet));

        System.out.println(result.toJson());
    }

    static AtomicInteger count = new AtomicInteger(0);

    static void generate() {
        for (int i = 0; i < 1_000_000; i++) {
            count.incrementAndGet();
            var seed = generateRandomString();

            long init = System.currentTimeMillis();
            var result = new Balatro()
                    .performAnalysis(seed);

            if (result.hasLegendary(1, LegendaryJoker.Perke, LegendaryJoker.Triboulet) &&
                    (result.hasInShop(1, RareJoker.Blueprint, 4) || result.hasInShop(1, RareJoker.Invisible_Joker, 4))) {
                long end = System.currentTimeMillis();

                System.err.println(seed + " " + (end - init) + " ms");

                System.out.println(result.toJson());
            }

            if (result.countLegendary() > 3) {
                System.out.println(seed);
            }
        }
    }

    public Run performAnalysis(String seed) {
        return performAnalysis(8, List.of(15, 50, 50, 50, 50, 50, 50, 50), Deck.RED_DECK, Stake.White_Stake, Version.v_101f, seed);
    }

    public Run performAnalysis(int ante, List<Integer> cardsPerAnte, Deck deck, Stake stake, Version version, String seed) {
        boolean[] selectedOptions = new boolean[61];
        Arrays.fill(selectedOptions, true);

        Functions functions = new Functions(seed, new InstanceParams(deck, stake, false, version.getVersion()));
        functions.initLocks(1, false, false);
        functions.lock("Overstock Plus");
        functions.lock("Liquidation");
        functions.lock("Glow Up");
        functions.lock("Reroll Glut");
        functions.lock("Omen Globe");
        functions.lock("Observatory");
        functions.lock("Nacho Tong");
        functions.lock("Recyclomancy");
        functions.lock("Tarot Tycoon");
        functions.lock("Planet Tycoon");
        functions.lock("Money Tree");
        functions.lock("Antimatter");
        functions.lock("Illusion");
        functions.lock("Petroglyph");
        functions.lock("Retcon");
        functions.lock("Palette");

        for (int i = 0; i < options.size(); i++) {
            if (!selectedOptions[i]) functions.lock(options.get(i));
        }

        functions.setDeck(deck);
        var antes = new ArrayList<Ante>(options.size());

        for (int a = 1; a <= ante; a++) {
            functions.initUnlocks(a, false);
            var play = new Ante(a, functions);
            antes.add(play);
            play.setBoss(functions.nextBoss(a));
            var voucher = functions.nextVoucher(a);
            play.setVoucher(voucher);

            functions.lock(voucher);
            // Unlock next level voucher
            for (int i = 0; i < Functions.VOUCHERS.size(); i += 2) {
                if (Functions.VOUCHERS.get(i).equals(voucher)) {
                    // Only unlock it if it's unlockable
                    if (selectedOptions[options.indexOf(Functions.VOUCHERS.get(i + 1).getName())]) {
                        functions.unlock(Functions.VOUCHERS.get(i + 1).getName());
                    }
                }
            }

            play.addTag(functions.nextTag(a));
            play.addTag(functions.nextTag(a));

            for (int q = 1; q <= cardsPerAnte.get(a - 1); q++) {
                Named sticker = null;

                ShopItem item = functions.nextShopItem(a);

                if (item.getType() == Type.Joker) {
                    if (item.getJokerData().getStickers().isEternal()) {
                        sticker = Edition.Eternal;
                    }
                    if (item.getJokerData().getStickers().isPerishable()) {
                        sticker = Edition.Perishable;
                    }
                    if (item.getJokerData().getStickers().isRental()) {
                        sticker = Edition.Rental;
                    }
                    if (item.getJokerData().getEdition() != Edition.NoEdition) {
                        sticker = item.getJokerData().getEdition();
                    }
                }

                play.addToQueue(item, sticker);
            }

            int numPacks = (a == 1) ? 4 : 6;

            for (int p = 1; p <= numPacks; p++) {
                var pack = functions.nextPack(a);
                Pack packInfo = functions.packInfo(pack);
                Set<Option> options = new HashSet<>();

                switch (packInfo.getKind()) {
                    case PackKind.Celestial -> {
                        List<String> cards = functions.nextCelestialPack(packInfo.getSize(), a);
                        for (int c = 0; c < packInfo.getSize(); c++) {
                            options.add(new Option(cards.get(c)));
                        }
                    }
                    case PackKind.Arcana -> {
                        List<String> cards = functions.nextArcanaPack(packInfo.getSize(), a);
                        for (int c = 0; c < packInfo.getSize(); c++) {
                            options.add(new Option(cards.get(c)));
                        }
                    }
                    case PackKind.Spectral -> {
                        List<String> cards = functions.nextSpectralPack(packInfo.getSize(), a);
                        for (int c = 0; c < packInfo.getSize(); c++) {
                            options.add(new Option(cards.get(c)));
                        }
                    }
                    case PackKind.Buffoon -> {
                        List<JokerData> cards = functions.nextBuffoonPack(packInfo.getSize(), a);

                        for (int c = 0; c < packInfo.getSize(); c++) {
                            JokerData joker = cards.get(c);
                            var sticker = getSticker(joker);

                            options.add(new Option(sticker, joker.getJoker()));

                        }
                    }
                    case PackKind.Standard -> {
                        List<Card> cards = functions.nextStandardPack(packInfo.getSize(), a);

                        for (int c = 0; c < packInfo.getSize(); c++) {
                            Card card = cards.get(c);
                            StringBuilder output = new StringBuilder();
                            if (!card.getSeal().equals("No Seal")) {
                                output.append(card.getSeal()).append(" ");
                            }
                            if (!card.getEdition().equals("No Edition")) {
                                output.append(card.getEdition()).append(" ");
                            }
                            if (!card.getEnhancement().equals("No Enhancement")) {
                                output.append(card.getEnhancement()).append(" ");
                            }

                            char rank = card.getBase().charAt(2);

                            switch (rank) {
                                case 'T':
                                    output.append("10");
                                    break;
                                case 'J':
                                    output.append("Jack");
                                    break;
                                case 'Q':
                                    output.append("Queen");
                                    break;
                                case 'K':
                                    output.append("King");
                                    break;
                                case 'A':
                                    output.append("Ace");
                                    break;
                                default:
                                    output.append(rank);
                                    break;
                            }
                            output.append(" of ");
                            char suit = card.getBase().charAt(0);
                            switch (suit) {
                                case 'C':
                                    output.append("Clubs");
                                    break;
                                case 'S':
                                    output.append("Spades");
                                    break;
                                case 'D':
                                    output.append("Diamonds");
                                    break;
                                case 'H':
                                    output.append("Hearts");
                                    break;
                            }
                            options.add(new Option(output.toString()));
                        }
                    }
                }

                play.addPack(packInfo, options);
            }
        }

        return new Run(seed, antes);
    }

    private static @Nullable Named getSticker(JokerData joker) {
        Named sticker = null;

        if (joker.getStickers().isEternal()) {
            sticker = Edition.Eternal;
        }
        if (joker.getStickers().isPerishable()) {
            sticker = Edition.Perishable;
        }
        if (joker.getStickers().isRental()) {
            sticker = Edition.Rental;
        }
        if (!joker.getEdition().equals("No Edition")) {
            sticker = joker.getEdition();
        }

        return sticker;
    }
}
