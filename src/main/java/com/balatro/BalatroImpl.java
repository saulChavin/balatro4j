package com.balatro;

import com.balatro.api.AbstractCard;
import com.balatro.api.Balatro;
import com.balatro.api.Item;
import com.balatro.api.Run;
import com.balatro.enums.*;
import com.balatro.structs.*;
import com.balatro.structs.Card;
import com.balatro.structs.PackInfo;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public final class BalatroImpl implements Balatro {

    public static final List<? extends Item> options = Arrays.asList(
            CommonJoker.Golden_Ticket,
            CommonJoker.Hanging_Chad,
            CommonJoker.Shoot_the_Moo,
            CommonJoker.Swashbuckler,
            RareJoker.Blueprint,
            RareJoker.Brainstorm,
            RareJoker.Burnt_Joke,
            RareJoker.Drivers_License,
            RareJoker.Hit_the_Road,
            RareJoker.Invisible_Joker,
            RareJoker.Stuntman,
            RareJoker.The_Duo,
            RareJoker.The_Family,
            RareJoker.The_Order,
            RareJoker.The_Tribe,
            RareJoker.The_Trio,
            RareJoker.Wee_Joker,
            Tag.Foil_Tag,
            Tag.Holographic_Tag,
            Tag.Negative_Tag,
            Tag.Polychrome_Tag,
            Tag.Rare_Tag,
            UnCommonJoker.Acrobat,
            UnCommonJoker.Arrowhead,
            UnCommonJoker.Astronomer,
            UnCommonJoker.Bloodstone,
            UnCommonJoker.Cartomancer,
            UnCommonJoker.Certificate,
            UnCommonJoker.Flower_Pot,
            UnCommonJoker.Glass_Joker,
            UnCommonJoker.Matador,
            UnCommonJoker.Merry_Andy,
            UnCommonJoker.Mr_Bones,
            UnCommonJoker.Onyx_Agate,
            UnCommonJoker.Oops_All_6s,
            UnCommonJoker.Rough_Gem,
            UnCommonJoker.Satellite,
            UnCommonJoker.Seeing_Double,
            UnCommonJoker.Showman,
            UnCommonJoker.Smeared_Joker,
            UnCommonJoker.Sock_and_Buskin,
            UnCommonJoker.The_Idol,
            UnCommonJoker.Throwback,
            UnCommonJoker.Troubadour,
            UnCommonJoker100.Bootstraps,
            Voucher.Antimatter,
            Voucher.Glow_Up,
            Voucher.Illusion,
            Voucher.Liquidation,
            Voucher.Money_Tree,
            Voucher.Nacho_Tong,
            Voucher.Observatory,
            Voucher.Omen_Globe,
            Voucher.Overstock_Plus,
            Voucher.Palett,
            Voucher.Petroglyph,
            Voucher.Planet_Tycoon,
            Voucher.Recyclomancy,
            Voucher.Reroll_Glut,
            Voucher.Retcon,
            Voucher.Tarot_Tycoon
    );

    static final String CHARACTERS = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateRandomSeed() {
        StringBuilder result = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            int index = ThreadLocalRandom.current().nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }

    private final String seed;
    private final int ante;
    private final List<Integer> cardsPerAnte;
    private final Deck deck;
    private final Stake stake;
    private final Version version;
    private final boolean analyzeStandardPacks;
    private final boolean analyzeCelestialPacks;
    private final boolean analyzeTags;
    private final boolean analyzeBoss;
    private final boolean analyzeShopQueue;
    private final boolean analyzeJokers;
    private final boolean analyzeArcana;
    private final boolean analyzeSpectral;

    public BalatroImpl(String seed, int ante, List<Integer> cardsPerAnte, Deck deck, Stake stake, Version version,
                       @NotNull Set<PackKind> enabledPacks, boolean analyzeTags, boolean analyzeBoss, boolean analyzeShopQueue) {
        this.seed = seed;
        this.ante = ante;
        this.cardsPerAnte = cardsPerAnte;
        this.deck = deck;
        this.stake = stake;
        this.version = version;
        this.analyzeStandardPacks = enabledPacks.contains(PackKind.Standard);
        this.analyzeCelestialPacks = enabledPacks.contains(PackKind.Celestial);
        this.analyzeArcana = enabledPacks.contains(PackKind.Arcana);
        this.analyzeSpectral = enabledPacks.contains(PackKind.Spectral);
        this.analyzeJokers = enabledPacks.contains(PackKind.Buffoon);
        this.analyzeTags = analyzeTags;
        this.analyzeBoss = analyzeBoss;
        this.analyzeShopQueue = analyzeShopQueue;
    }

    @Override
    public Run analyze() {
        return performAnalysis(ante, cardsPerAnte, deck, stake, version, seed);
    }

    private @NotNull RunImpl performAnalysis(int ante, List<Integer> cardsPerAnte, Deck deck, Stake stake, @NotNull Version version, String seed) {
        boolean[] selectedOptions = new boolean[61];
        Arrays.fill(selectedOptions, true);

        Functions functions = new Functions(seed, new InstanceParams(deck, stake, false, version.getVersion()));
        functions.initLocks(1, false, false);
        functions.firstLock();

        for (int i = 0; i < options.size(); i++) {
            if (!selectedOptions[i]) functions.lock(options.get(i));
        }

        functions.setDeck(deck);
        var antes = new ArrayList<AnteImpl>(options.size());

        for (int a = 1; a <= ante; a++) {
            functions.initUnlocks(a, false);
            var play = new AnteImpl(a, functions);
            antes.add(play);

            if (analyzeBoss) {
                play.setBoss(functions.nextBoss(a));
            }

            var voucher = functions.nextVoucher(a);
            play.setVoucher(voucher);

            functions.lock(voucher);
            // Unlock next level voucher
            for (int i = 0; i < Functions.VOUCHERS.size(); i += 2) {
                if (Functions.VOUCHERS.get(i).equals(voucher)) {
                    // Only unlock it if it's unlockable
                    if (selectedOptions[options.indexOf(Functions.VOUCHERS.get(i + 1))]) {
                        functions.unlock(Functions.VOUCHERS.get(i + 1));
                    }
                }
            }

            if (analyzeTags) {
                play.addTag(functions.nextTag(a));
                play.addTag(functions.nextTag(a));
            }

            if (analyzeShopQueue) {
                for (int q = 1; q <= cardsPerAnte.get(a - 1); q++) {
                    Edition sticker = null;

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
            }

            int numPacks = (a == 1) ? 4 : 6;

            for (int p = 1; p <= numPacks; p++) {
                var pack = functions.nextPack(a);
                PackInfo packInfo = functions.packInfo(pack);
                Set<Option> options = new HashSet<>();

                switch (packInfo.getKind()) {
                    case PackKind.Celestial -> {
                        if (!analyzeCelestialPacks) continue;

                        List<Item> cards = functions.nextCelestialPack(packInfo.getSize(), a);
                        for (int c = 0; c < packInfo.getSize(); c++) {
                            options.add(new Option(cards.get(c)));
                        }
                    }
                    case PackKind.Arcana -> {
                        if (!analyzeArcana) continue;
                        var cards = functions.nextArcanaPack(packInfo.getSize(), a);
                        for (int c = 0; c < packInfo.getSize(); c++) {
                            options.add(new Option(cards.get(c)));
                        }
                    }
                    case PackKind.Spectral -> {
                        if (!analyzeSpectral) continue;
                        List<Item> cards = functions.nextSpectralPack(packInfo.getSize(), a);
                        for (int c = 0; c < packInfo.getSize(); c++) {
                            options.add(new Option(cards.get(c)));
                        }
                    }
                    case PackKind.Buffoon -> {
                        if (!analyzeJokers) continue;
                        List<JokerData> cards = functions.nextBuffoonPack(packInfo.getSize(), a);

                        for (int c = 0; c < packInfo.getSize(); c++) {
                            JokerData joker = cards.get(c);
                            var sticker = getSticker(joker);

                            options.add(new Option(sticker, joker.getJoker()));

                        }
                    }
                    case PackKind.Standard -> {
                        if (!analyzeStandardPacks) continue;

                        List<Card> cards = functions.nextStandardPack(packInfo.getSize(), a);

                        for (int c = 0; c < packInfo.getSize(); c++) {
                            Card card = cards.get(c);
                            StringBuilder output = new StringBuilder();
                            if (!card.getSeal().equals("No Seal")) {
                                output.append(card.getSeal()).append(" ");
                            }
                            if (card.getEdition() != Edition.NoEdition) {
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
                            options.add(new Option(new AbstractCard(output.toString())));
                        }
                    }
                }

                play.addPack(packInfo, options);
            }
        }

        return new RunImpl(seed, Collections.unmodifiableList(antes));
    }

    private static @Nullable Item getSticker(@NotNull JokerData joker) {
        Item sticker = null;

        if (joker.getStickers().isEternal()) {
            sticker = Edition.Eternal;
        }
        if (joker.getStickers().isPerishable()) {
            sticker = Edition.Perishable;
        }
        if (joker.getStickers().isRental()) {
            sticker = Edition.Rental;
        }

        if (joker.getEdition() != Edition.NoEdition) {
            sticker = joker.getEdition();
        }

        return sticker;
    }
}
