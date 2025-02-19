package com.balatro;

import com.balatro.api.AbstractCard;
import com.balatro.api.Balatro;
import com.balatro.api.Item;
import com.balatro.api.Run;
import com.balatro.enums.*;
import com.balatro.structs.*;
import com.balatro.structs.Card;
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

    public static @NotNull String generateRandomSeed() {
        StringBuilder result = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            int index = ThreadLocalRandom.current().nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }

    private final String seed;
    private int ante;
    private final List<Integer> cardsPerAnte;
    private Deck deck;
    private Stake stake;
    private Version version;
    private boolean analyzeStandardPacks;
    private boolean analyzeCelestialPacks;
    private boolean analyzeTags;
    private boolean analyzeBoss;
    private boolean analyzeShopQueue;
    private boolean analyzeJokers;
    private boolean analyzeArcana;
    private boolean analyzeSpectral;

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
                var packInfo = functions.packInfo(pack);
                Set<EditionItem> options = new HashSet<>();

                switch (packInfo.getKind()) {
                    case PackKind.Celestial -> {
                        if (!analyzeCelestialPacks) continue;
                        var cards = functions.nextCelestialPack(packInfo.getSize(), a);
                        for (Item card : cards) {
                            options.add(card.asOption());
                        }
                    }
                    case PackKind.Arcana -> {
                        if (!analyzeArcana) continue;
                        var cards = functions.nextArcanaPack(packInfo.getSize(), a);
                        for (Item card : cards) {
                            options.add(card.asOption());
                        }
                    }
                    case PackKind.Spectral -> {
                        if (!analyzeSpectral) continue;
                        var spectral = functions.nextSpectralPack(packInfo.getSize(), a);
                        for (Item card : spectral) {
                            options.add(card.asOption());
                        }
                    }
                    case PackKind.Buffoon -> {
                        if (!analyzeJokers) continue;
                        var cards = functions.nextBuffoonPack(packInfo.getSize(), a);

                        for (JokerData joker : cards) {
                            var sticker = getSticker(joker);
                            options.add(new EditionItem(joker.getJoker(), sticker));
                        }
                    }
                    case PackKind.Standard -> {
                        if (!analyzeStandardPacks) continue;

                        var cards = functions.nextStandardPack(packInfo.getSize(), a);

                        for (Card card : cards) {
                            StringBuilder output = new StringBuilder();

                            if (card.seal() != Seal.NoSeal) {
                                output.append(card.seal()).append(" ");
                            }
                            if (card.edition() != Edition.NoEdition) {
                                output.append(card.edition()).append(" ");
                            }
                            if (card.enhancement() != null) {
                                output.append(card.enhancement().getName()).append(" ");
                            }

                            var rank = card.base().getRank();
                            output.append(rank.getName());
                            output.append(" of ")
                                    .append(card.base().getSuit().getName());
                            options.add(new EditionItem(new AbstractCard(output.toString())));
                        }
                    }
                }

                play.addPack(packInfo, options);
            }
        }

        return new RunImpl(seed, Collections.unmodifiableList(antes));
    }

    private static @Nullable Edition getSticker(@NotNull JokerData joker) {
        Edition sticker = null;

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

    @Override
    public Balatro analyzeAll() {
        analyzeStandardPacks = true;
        analyzeCelestialPacks = true;
        analyzeArcana = true;
        analyzeSpectral = true;
        analyzeJokers = true;
        analyzeTags = true;
        analyzeBoss = true;
        analyzeShopQueue = true;
        return this;
    }

    @Override
    public Balatro maxAnte(int ante) {
        this.ante = ante;
        return this;
    }

    @Override
    public Balatro disableShopQueue() {
        this.analyzeShopQueue = false;
        return this;
    }

    @Override
    public Balatro version(Version version) {
        this.version = version;
        return this;
    }

    @Override
    public Balatro deck(Deck deck) {
        this.deck = deck;
        return this;
    }

    @Override
    public Balatro stake(Stake stake) {
        this.stake = stake;
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    @Override
    public Balatro disablePack(@NotNull PackKind packKind) {
        switch (packKind) {
            case Standard -> analyzeStandardPacks = false;
            case Celestial -> analyzeCelestialPacks = false;
            case Arcana -> analyzeArcana = false;
            case Spectral -> analyzeSpectral = false;
            case Buffoon -> analyzeJokers = false;
        }
        return this;
    }
}
