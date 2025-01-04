package com.balatro;

import com.balatro.enums.Deck;
import com.balatro.enums.Stake;
import com.balatro.enums.Version;
import com.balatro.structs.*;

import java.util.Arrays;
import java.util.List;
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
        generate();
    }

    static AtomicInteger count = new AtomicInteger(0);

    static void generate() {
        for (int i = 0; i < 1000; i++) {
            var seed = generateRandomString();
            var result = new Balatro()
                    .performAnalysis(seed);

            if (result.contains("The Soul")) {
                count.incrementAndGet();
                System.out.println(seed);
            }
        }
    }

    public String performAnalysis(String seed) {
        return performAnalysis(8, List.of(15, 50, 50, 50, 50, 50, 50, 50), Deck.RED_DECK, Stake.White_Stake, Version.v_100n, seed);
    }

    public String performAnalysis(int ante, List<Integer> cardsPerAnte, Deck deck, Stake stake, Version version, String seed) {
        boolean[] selectedOptions = new boolean[61];
        Arrays.fill(selectedOptions, true);

        StringBuilder output = new StringBuilder();

        Functions inst = new Functions(seed);
        inst.setParams(new InstanceParams(deck, stake, false, version.getVersion()));
        inst.initLocks(1, false, false);
        inst.lock("Overstock Plus");
        inst.lock("Liquidation");
        inst.lock("Glow Up");
        inst.lock("Reroll Glut");
        inst.lock("Omen Globe");
        inst.lock("Observatory");
        inst.lock("Nacho Tong");
        inst.lock("Recyclomancy");
        inst.lock("Tarot Tycoon");
        inst.lock("Planet Tycoon");
        inst.lock("Money Tree");
        inst.lock("Antimatter");
        inst.lock("Illusion");
        inst.lock("Petroglyph");
        inst.lock("Retcon");
        inst.lock("Palette");

        for (int i = 0; i < options.size(); i++) {
            if (!selectedOptions[i]) inst.lock(options.get(i));
        }
        inst.setDeck(deck);

        for (int a = 1; a <= ante; a++) {
            inst.initUnlocks(a, false);
            output.append("==ANTE ").append(a).append("==\n");
            output.append("Boss: ").append(inst.nextBoss(a)).append("\n");
            String voucher = inst.nextVoucher(a);
            output.append("Voucher: ").append(voucher).append("\n");
            inst.lock(voucher);
            // Unlock next level voucher
            for (int i = 0; i < Functions.VOUCHERS.size(); i += 2) {
                if (Functions.VOUCHERS.get(i).equals(voucher)) {
                    // Only unlock it if it's unlockable
                    if (selectedOptions[options.indexOf(Functions.VOUCHERS.get(i + 1).getName())]) {
                        inst.unlock(Functions.VOUCHERS.get(i + 1).getName());
                    }
                }
            }
            output.append("Tags: ").append(inst.nextTag(a)).append(", ").append(inst.nextTag(a)).append("\n");

            output.append("Shop Queue: \n");
            for (int q = 1; q <= cardsPerAnte.get(a - 1); q++) {
                output.append(q).append(") ");
                ShopItem item = inst.nextShopItem(a);
                if (item.getType().equals("Joker")) {
                    if (item.getJokerData().getStickers().isEternal()) output.append("Eternal ");
                    if (item.getJokerData().getStickers().isPerishable()) output.append("Perishable ");
                    if (item.getJokerData().getStickers().isRental()) output.append("Rental ");
                    if (!item.getJokerData().getEdition().equals("No Edition"))
                        output.append(item.getJokerData().getEdition()).append(" ");
                }
                output.append(item.getItem()).append("\n");
            }

            output.append("\nPacks: \n");
            int numPacks = (a == 1) ? 4 : 6;
            for (int p = 1; p <= numPacks; p++) {
                String pack = inst.nextPack(a);
                output.append(pack).append(" - ");
                Pack packInfo = inst.packInfo(pack);
                if (packInfo.getType().equals("Celestial Pack")) {
                    List<String> cards = inst.nextCelestialPack(packInfo.getSize(), a);
                    for (int c = 0; c < packInfo.getSize(); c++) {
                        output.append(cards.get(c));
                        output.append((c + 1 != packInfo.getSize()) ? ", " : "");
                    }
                }
                if (packInfo.getType().equals("Arcana Pack")) {
                    List<String> cards = inst.nextArcanaPack(packInfo.getSize(), a);
                    for (int c = 0; c < packInfo.getSize(); c++) {
                        output.append(cards.get(c));
                        output.append((c + 1 != packInfo.getSize()) ? ", " : "");
                    }
                }
                if (packInfo.getType().equals("Spectral Pack")) {
                    List<String> cards = inst.nextSpectralPack(packInfo.getSize(), a);
                    for (int c = 0; c < packInfo.getSize(); c++) {
                        output.append(cards.get(c));
                        output.append((c + 1 != packInfo.getSize()) ? ", " : "");
                    }
                }
                if (packInfo.getType().equals("Buffoon Pack")) {
                    List<JokerData> cards = inst.nextBuffoonPack(packInfo.getSize(), a);
                    for (int c = 0; c < packInfo.getSize(); c++) {
                        JokerData joker = cards.get(c);
                        if (joker.getStickers().isEternal()) output.append("Eternal ");
                        if (joker.getStickers().isPerishable()) output.append("Perishable ");
                        if (joker.getStickers().isRental()) output.append("Rental ");
                        if (!joker.getEdition().equals("No Edition")) output.append(joker.getEdition()).append(" ");
                        output.append(joker.getJoker());
                        output.append((c + 1 != packInfo.getSize()) ? ", " : "");
                    }
                }
                if (packInfo.getType().equals("Standard Pack")) {
                    List<Card> cards = inst.nextStandardPack(packInfo.getSize(), a);
                    for (int c = 0; c < packInfo.getSize(); c++) {
                        Card card = cards.get(c);
                        if (!card.getSeal().equals("No Seal")) output.append(card.getSeal()).append(" ");
                        if (!card.getEdition().equals("No Edition")) output.append(card.getEdition()).append(" ");
                        if (!card.getEnhancement().equals("No Enhancement"))
                            output.append(card.getEnhancement()).append(" ");
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
                        output.append((c + 1 != packInfo.getSize()) ? ", " : "");
                    }
                }
                output.append("\n");
            }

            output.append("\n");
        }

        return output.toString();
    }
}
