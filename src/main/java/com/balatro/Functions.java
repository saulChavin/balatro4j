package com.balatro;

import com.balatro.api.Item;
import com.balatro.api.Lock;
import com.balatro.enums.*;
import com.balatro.enums.Card;
import com.balatro.enums.PackType;
import com.balatro.structs.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.balatro.Util.pseudohash;
import static com.balatro.Util.round13;

public final class Functions implements Lock {

    public static final List<Tarot> TAROTS = Arrays.asList(Tarot.values());
    public static final List<Planet> PLANETS = Arrays.asList(Planet.values());
    public static final List<Spectral> SPECTRALS = Arrays.asList(Spectral.values());
    public static final List<LegendaryJoker> LEGENDARY_JOKERS = Arrays.asList(LegendaryJoker.values());
    public static final List<UnCommonJoker> UNCOMMON_JOKERS = Arrays.asList(UnCommonJoker.values());
    public static final List<UnCommonJoker101C> UNCOMMON_JOKERS_101C = Arrays.asList(UnCommonJoker101C.values());
    public static final List<UnCommonJoker100> UNCOMMON_JOKERS_100 = Arrays.asList(UnCommonJoker100.values());
    public static final List<Card> CARDS = Arrays.asList(Card.values());
    public static final List<Enhancement> ENHANCEMENTS = Arrays.asList(Enhancement.values());
    public static final List<Voucher> VOUCHERS = Arrays.asList(Voucher.values());
    public static final List<Tag> TAGS = Arrays.asList(Tag.values());
    public static final List<PackType> PACKS = Arrays.asList(PackType.values());
    public static final List<RareJoker> RARE_JOKERS = Arrays.asList(RareJoker.values());
    public static final List<RareJoker101C> RARE_JOKERS_101C = Arrays.asList(RareJoker101C.values());
    public static final List<RareJoker100> RARE_JOKERS_100 = Arrays.asList(RareJoker100.values());
    public static final List<CommonJoker> COMMON_JOKERS = Arrays.asList(CommonJoker.values());
    public static final List<CommonJoker100> COMMON_JOKERS_100 = Arrays.asList(CommonJoker100.values());
    public static final List<Boss> BOSSES = Arrays.asList(Boss.values());

    private final InstanceParams params;
    private final Cache cache;
    public final String seed;
    public final double hashedSeed;
    private final Lock lock;

    public Functions(String s, InstanceParams params) {
        seed = s;
        hashedSeed = pseudohash(s);
        this.params = params;
        cache = new Cache();
        this.lock = new LongArrayLock();
    }

    private double getNode(String id) {
        var c = cache.get(id);

        if (c == null) {
            c = pseudohash(id + seed);
            cache.put(id, c);
        }

        var value = round13((c * 1.72431234 + 2.134453429141) % 1);

        cache.put(id, value);

        return (value + hashedSeed) / 2;
    }

    private double random(String id) {
        return LuaRandom.random(getNode(id));
    }

    private int randint(String ID, int max) {
        return LuaRandom.randint(getNode(ID), 0, max);
    }

    public PackType randweightedchoice(String ID, List<PackType> items) {
        double poll = random(ID) * 22.42;
        int idx = 1;
        double weight = 0;
        while (weight < poll) {
            weight += items.get(idx).getValue();
            idx++;
        }
        return items.get(idx - 1);
    }

    public <T extends Item> T randchoice(String id, @NotNull List<T> items) {
        T item = items.get(randint(id, items.size() - 1));

        if (!params.isShowman() && isLocked(item) || "RETRY".equals(item.getName())) {
            int resample = 2;
            while (true) {
                item = items.get(randint(id + "_resample" + resample, items.size() - 1));
                resample++;
                if ((!isLocked(item) && !"RETRY".equals(item.getName())) || resample > 1000) {
                    return item;
                }
            }
        }
        return item;
    }

    // Card Generators
    public Item nextTarot(String source, int ante, boolean soulable) {
        if (soulable && (params.isShowman() || !isLocked(Specials.THE_SOUL)) && random(soul_TarotArr[ante]) > 0.997) {
            return Specials.THE_SOUL;
        }
        return randchoice(source, TAROTS);
    }

    public Item nextPlanet(String source, int ante, boolean soulable) {
        if (soulable && (params.isShowman() || !isLocked(Specials.BLACKHOLE)) && random(soul_PlanetArr[ante]) > 0.997) {
            return Specials.BLACKHOLE;
        }
        return randchoice(source, PLANETS);
    }

    public Item nextSpectral(String source, int ante, boolean soulable) {
        if (soulable) {
            if (random(soul_SpectralArr[ante]) < 0.997) {
                return randchoice(source, SPECTRALS);
            }

            if (params.isShowman() || !isLocked(Specials.BLACKHOLE)) {
                return Specials.BLACKHOLE;
            }

            if (params.isShowman() || !isLocked(Specials.THE_SOUL)) {
                return Specials.THE_SOUL;
            }
        }

        return randchoice(source, SPECTRALS);
    }


    static Set<String> setA = Set.of("Gros Michel", "Ice Cream", "Cavendish", "Luchador", "Turtle Bean", "Diet Cola", "Popcorn", "Ramen", "Seltzer", "Mr. Bones", "Invisible Joker");
    static Set<String> setB = Set.of("Ceremonial Dagger", "Ride the Bus", "Runner", "Constellation", "Green Joker", "Red Card", "Madness", "Square Joker", "Vampire", "Rocket", "Obelisk", "Lucky Cat", "Flash Card", "Spare Trousers", "Castle", "Wee Joker");

    public JokerData nextJoker(@NotNull String source,
                               String[] joker1Arr, String[] joker2Arr, String[] joker3Arr, String[] joker4Arr,
                               String[] rarityArr, String[] editionArr,
                               int ante, boolean hasStickers) {
        // Get rarity
        int rarity = 1;

        switch (source) {
            case "sou" -> rarity = 4;
            case "wra", "rta" -> rarity = 3;
            case "uta" -> rarity = 2;
            default -> {
                double rarityPoll = random(rarityArr[ante]);
                if (rarityPoll > 0.95) {
                    rarity = 3;
                } else if (rarityPoll > 0.7) {
                    rarity = 2;
                }
            }
        }

        // Get edition
        int editionRate = 1;

        if (isVoucherActive(Voucher.Glow_Up)) {
            editionRate = 4;
        } else if (isVoucherActive(Voucher.Hone)) {
            editionRate = 2;
        }

        var edition = Edition.NoEdition;
        var editionPoll = random(editionArr[ante]);

        if (editionPoll > 0.997) {
            edition = Edition.Negative;
        } else if (editionPoll > 1 - 0.006 * editionRate) {
            edition = Edition.Polychrome;
        } else if (editionPoll > 1 - 0.02 * editionRate) {
            edition = Edition.Holographic;
        } else if (editionPoll > 1 - 0.04 * editionRate) {
            edition = Edition.Foil;
        }

        // Get next joker
        Item joker;

        switch (rarity) {
            case 4 -> {
                if (params.version > 10099) {
                    joker = randchoice("Joker4", LEGENDARY_JOKERS);
                } else {
                    joker = randchoice(joker4Arr[ante], LEGENDARY_JOKERS);
                }
            }
            case 3 -> {
                if (params.version > 10103) {
                    joker = randchoice(joker3Arr[ante], RARE_JOKERS);
                } else {
                    if (params.version > 10099) {
                        joker = randchoice(joker3Arr[ante], RARE_JOKERS_101C);
                    } else {
                        joker = randchoice(joker3Arr[ante], RARE_JOKERS_100);
                    }
                }
            }
            case 2 -> {
                if (params.version > 10103) {
                    joker = randchoice(joker2Arr[ante], UNCOMMON_JOKERS);
                } else {
                    if (params.version > 10099) {
                        joker = randchoice(joker2Arr[ante], UNCOMMON_JOKERS_101C);
                    } else {
                        joker = randchoice(joker2Arr[ante], UNCOMMON_JOKERS_100);
                    }
                }
            }
            default -> {
                if (params.version > 10099) {
                    joker = randchoice(joker1Arr[ante], COMMON_JOKERS);
                } else {
                    joker = randchoice(joker1Arr[ante], COMMON_JOKERS_100);
                }
            }
        }

        // Get next joker stickers
        var stickers = new JokerStickers();

        if (hasStickers) {
            if (params.version > 10103) {
                boolean searchForSticker = (params.getStake() == Stake.Black_Stake ||
                                            params.getStake() == Stake.Blue_Stake ||
                                            params.getStake() == Stake.Purple_Stake ||
                                            params.getStake() == Stake.Orange_Stake ||
                                            params.getStake() == Stake.Gold_Stake);

                double stickerPoll = 0.0;

                if (searchForSticker) {
                    if (source.equals("buf")) {
                        stickerPoll = random(packetperArr[ante]);
                    } else {
                        stickerPoll = random(etperpollArr[ante]);
                    }
                }

                if (stickerPoll > 0.7) {
                    if (!setA.contains(joker.getName())) {
                        stickers.setRental(true);
                    }
                }

                if ((stickerPoll > 0.4 && stickerPoll <= 0.7) && (params.getStake() == Stake.Orange_Stake || params.getStake() == Stake.Gold_Stake)) {
                    if (!setB.contains(joker.getName())) {
                        stickers.setPerishable(true);
                    }
                }

                if (params.getStake() == Stake.Gold_Stake) {
                    if (source.equals("buf")) {
                        stickers.setRental(random(packssjrArr[ante]) > 0.7);
                    } else {
                        stickers.setRental(random(ssjrArr[ante]) > 0.7);
                    }
                }

            } else {
                if (params.getStake() == Stake.Black_Stake || params.getStake() == Stake.Blue_Stake ||
                    params.getStake() == Stake.Purple_Stake || params.getStake() == Stake.Orange_Stake || params.getStake() == Stake.Gold_Stake) {
                    if (!setA.contains(joker.getName())) {
                        stickers.setEternal(random(stake_shop_joker_eternalArr[ante]) > 0.7);
                    }
                }
                if (params.version > 10099) {
                    if ((params.getStake() == Stake.Orange_Stake || params.getStake() == Stake.Gold_Stake) && !stickers.isEternal()) {
                        stickers.setPerishable(random(ssjpArr[ante]) > 0.49);
                    }
                    if (params.getStake() == Stake.Gold_Stake) {
                        stickers.setRental(random(ssjrArr[ante]) > 0.7);
                    }
                }
            }
        }

        return new JokerData(joker, rarity, edition, stickers);
    }

    // Shop Logic
    @Contract(" -> new")
    public @NotNull ShopInstance getShopInstance() {
        double tarotRate = 4;
        double planetRate = 4;
        double playingCardRate = 0;
        double spectralRate = 0;

        if (params.getDeck() == Deck.GHOST_DECK) {
            spectralRate = 2;
        }
        if (isVoucherActive(Voucher.Tarot_Tycoon)) {
            tarotRate = 32;
        } else if (isVoucherActive(Voucher.Tarot_Merchant)) {
            tarotRate = 9.6;
        }
        if (isVoucherActive(Voucher.Planet_Tycoon)) {
            planetRate = 32;
        } else if (isVoucherActive(Voucher.Planet_Merchant)) {
            planetRate = 9.6;
        }
        if (isVoucherActive(Voucher.Magic_Trick)) {
            playingCardRate = 4;
        }

        return new ShopInstance(20, tarotRate, planetRate, playingCardRate, spectralRate);
    }

    @Contract("_ -> new")
    public @NotNull ShopItem nextShopItem(int ante) {
        var shop = getShopInstance();

        double cdtPoll = random(cdtArr[ante]) * shop.getTotalRate();

        Type type;

        if (cdtPoll < shop.jokerRate) {
            type = Type.Joker;
        } else {
            cdtPoll -= shop.jokerRate;
            if (cdtPoll < shop.tarotRate) {
                type = Type.Tarot;
            } else {
                cdtPoll -= shop.tarotRate;
                if (cdtPoll < shop.planetRate) {
                    type = Type.Planet;
                } else {
                    cdtPoll -= shop.planetRate;
                    if (cdtPoll < shop.playingCardRate) {
                        type = Type.PlayingCard;
                    } else {
                        type = Type.Spectral;
                    }
                }
            }
        }

        switch (type) {
            case Type.Joker -> {
                var jkr = nextJoker("sho", joker1ShoArr, joker2ShoArr, joker3ShoArr, joker4ShoArr,
                        rarityShoArr, editionShoArr, ante, true);
                return new ShopItem(type, jkr.joker, jkr);
            }
            case Type.Tarot -> {
                return new ShopItem(type, nextTarot(tarotShoArr[ante], ante, false));
            }
            case Type.Planet -> {
                return new ShopItem(type, nextPlanet(planetShoArr[ante], ante, false));
            }
            case Type.Spectral -> {
                return new ShopItem(type, nextSpectral(spectralShoArr[ante], ante, false));
            }
            case Type.PlayingCard -> {
                return new ShopItem(type, nextStandardCard(ante));
            }
        }

        throw new IllegalStateException("Error: ShopItem type not found");

//        return new ShopItem();
    }

    public PackType nextPack(int ante) {
        if (ante <= 2 && !cache.isGeneratedFirstPack() && params.version > 10099) {
            cache.setGeneratedFirstPack(true);
            return PackType.Buffoon_Pack;
        }

        return randweightedchoice(shop_packArr[ante], PACKS);
    }

    @Contract("_ -> new")
    public @NotNull PackInfo packInfo(@NotNull PackType pack) {
        if (pack.isMega()) {
            return new PackInfo(pack, (pack.isBuffon() || pack.isSpectral()) ? 4 : 5, 2);
        } else if (pack.isJumbo()) {
            return new PackInfo(pack, (pack.isBuffon() || pack.isSpectral()) ? 4 : 5, 1);
        } else {
            return new PackInfo(pack, (pack.isBuffon() || pack.isSpectral()) ? 2 : 3, 1);
        }
    }

    static String[] planetShoArr;
    static String[] planetpl1lArr;
    static String[] tarotShoArr;
    static String[] tarotAr1Arr;
    static String[] tarotarArr;
    static String[] spectralShoArr;
    static String[] spectralAr2Arr;
    static String[] spectralSpeArr;
    static String[] joker4ShoArr;
    static String[] joker4BufArr;
    static String[] joker4SouArr;
    static String[] joker3ShoArr;
    static String[] joker3BufArr;
    static String[] joker3SouArr;
    static String[] joker2ShoArr;
    static String[] joker2BufArr;
    static String[] joker2SouArr;
    static String[] joker1ShoArr;
    static String[] joker1BufArr;
    static String[] joker1SouArr;

    static String[] rarityShoArr;
    static String[] rarityBufArr;
    static String[] raritySouArr;
    static String[] editionShoArr;
    static String[] editionBufArr;
    static String[] editionSouArr;

    static String[] packssjrArr;
    static String[] etperpollArr;
    static String[] packetperArr;
    static String[] stake_shop_joker_eternalArr;
    static String[] ssjpArr;
    static String[] ssjrArr;
    static String[] shop_packArr;
    static String[] stdsetArr;
    static String[] standard_editionArr;
    static String[] enhancedstaArr;
    static String[] stdsealArr;
    static String[] stdsealtypeArr;
    static String[] frontstaArr;
    static String[] soul_SpectralArr;
    static String[] soul_PlanetArr;
    static String[] soul_TarotArr;
    static String[] cdtArr;
    static String[] VoucherArr;
    static String[] TagArr;

    static {
        heat(8);
    }

    @SuppressWarnings("SameParameterValue")
    private static void heat(int max) {
        System.out.println("Heating to: " + max);
        max = max + 1;

        if (rarityShoArr != null && rarityShoArr.length == max) return;

        rarityShoArr = new String[max];
        rarityBufArr = new String[max];
        raritySouArr = new String[max];
        packssjrArr = new String[max];
        etperpollArr = new String[max];
        packetperArr = new String[max];
        stake_shop_joker_eternalArr = new String[max];
        ssjpArr = new String[max];
        ssjrArr = new String[max];
        shop_packArr = new String[max];
        TagArr = new String[max];
        VoucherArr = new String[max];
        cdtArr = new String[max];
        soul_PlanetArr = new String[max];
        stdsetArr = new String[max];
        standard_editionArr = new String[max];
        enhancedstaArr = new String[max];
        stdsealArr = new String[max];
        stdsealtypeArr = new String[max];
        frontstaArr = new String[max];
        soul_SpectralArr = new String[max];
        soul_TarotArr = new String[max];

        planetShoArr = new String[max];
        planetpl1lArr = new String[max];

        tarotShoArr = new String[max];
        tarotarArr = new String[max];
        tarotAr1Arr = new String[max];

        spectralShoArr = new String[max];
        spectralAr2Arr = new String[max];
        spectralSpeArr = new String[max];

        joker4ShoArr = new String[max];
        joker4BufArr = new String[max];
        joker4SouArr = new String[max];

        joker3ShoArr = new String[max];
        joker3BufArr = new String[max];
        joker3SouArr = new String[max];

        joker2ShoArr = new String[max];
        joker2BufArr = new String[max];
        joker2SouArr = new String[max];

        joker1ShoArr = new String[max];
        joker1BufArr = new String[max];
        joker1SouArr = new String[max];

        editionShoArr = new String[max];
        editionBufArr = new String[max];
        editionSouArr = new String[max];

        for (int ante = 1; ante < max; ante++) {
            stdsetArr[ante] = "stdset" + ante;
            standard_editionArr[ante] = "standard_edition" + ante;
            enhancedstaArr[ante] = "Enhancedsta" + ante;
            stdsealArr[ante] = "stdseal" + ante;
            stdsealtypeArr[ante] = "stdsealtype" + ante;
            frontstaArr[ante] = "frontsta" + ante;
            soul_SpectralArr[ante] = "soul_Spectral" + ante;
            soul_PlanetArr[ante] = "soul_Planet" + ante;
            soul_TarotArr[ante] = "soul_Tarot" + ante;
            cdtArr[ante] = "cdt" + ante;
            VoucherArr[ante] = "Voucher" + ante;
            TagArr[ante] = "Tag" + ante;
            shop_packArr[ante] = "shop_pack" + ante;
            ssjrArr[ante] = "ssjr" + ante;
            ssjpArr[ante] = "ssjp" + ante;
            stake_shop_joker_eternalArr[ante] = "stake_shop_joker_eternal" + ante;
            packetperArr[ante] = "packetper" + ante;
            etperpollArr[ante] = "etperpoll" + ante;
            packssjrArr[ante] = "packssjr" + ante;

            planetShoArr[ante] = "Planetsho" + ante;
            planetpl1lArr[ante] = "Planetpl1" + ante;

            tarotShoArr[ante] = "Tarotsho" + ante;
            tarotarArr[ante] = "Tarotar" + ante;
            tarotAr1Arr[ante] = "Tarotar1" + ante;

            spectralShoArr[ante] = "Spectralsho" + ante;
            spectralAr2Arr[ante] = "Spectralar2" + ante;
            spectralSpeArr[ante] = "Spectralspe" + ante;

            rarityShoArr[ante] = "rarity" + ante + "sho";
            rarityBufArr[ante] = "rarity" + ante + "buf";
            raritySouArr[ante] = "rarity" + ante + "sou";

            editionShoArr[ante] = "edisho" + ante;
            editionBufArr[ante] = "edibuf" + ante;
            editionSouArr[ante] = "edisou" + ante;

            joker4ShoArr[ante] = "Joker4sho" + ante;
            joker4BufArr[ante] = "Joker4buf" + ante;
            joker4SouArr[ante] = "Joker4sou" + ante;

            joker3ShoArr[ante] = "Joker3sho" + ante;
            joker3BufArr[ante] = "Joker3buf" + ante;
            joker3SouArr[ante] = "Joker3sou" + ante;

            joker2ShoArr[ante] = "Joker2sho" + ante;
            joker2BufArr[ante] = "Joker2buf" + ante;
            joker2SouArr[ante] = "Joker2sou" + ante;

            joker1ShoArr[ante] = "Joker1sho" + ante;
            joker1BufArr[ante] = "Joker1buf" + ante;
            joker1SouArr[ante] = "Joker1sou" + ante;
        }
    }


    @Contract("_ -> new")
    public com.balatro.structs.@NotNull Card nextStandardCard(int ante) {
        // Enhancement
        Enhancement enhancement = null;

        if (random(stdsetArr[ante]) > 0.6) {
            enhancement = randchoice(enhancedstaArr[ante], ENHANCEMENTS);
        }

        // Edition
        var edition = Edition.NoEdition;

        double editionPoll = random(standard_editionArr[ante]);

        if (editionPoll > 0.988) {
            edition = Edition.Polychrome;
        } else if (editionPoll > 0.96) {
            edition = Edition.Polychrome;
        } else if (editionPoll > 0.92) {
            edition = Edition.Foil;
        }

        // Seal
        var seal = Seal.NoSeal;

        if (random(stdsealArr[ante]) > 0.8) {
            double sealPoll = random(stdsealtypeArr[ante]);
            if (sealPoll > 0.75) {
                seal = Seal.RedSeal;
            } else if (sealPoll > 0.5) {
                seal = Seal.BlueSeal;
            } else if (sealPoll > 0.25) {
                seal = Seal.GoldSeal;
            } else {
                seal = Seal.PurpleSeal;
            }
        }

        return new com.balatro.structs.Card(randchoice(frontstaArr[ante], CARDS), enhancement, edition, seal);
    }

    public @NotNull List<Item> nextArcanaPack(int size, int ante) {
        List<Item> pack = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            if (isVoucherActive(Voucher.Omen_Globe) && random("omen_globe") > 0.8) {
                pack.add(nextSpectral(spectralAr2Arr[ante], ante, true));
            } else {
                pack.add(nextTarot(tarotAr1Arr[ante], ante, true));
            }
            if (!params.isShowman()) {
                lock(pack.get(i));
            }
        }

        if (params.isShowman()) return pack;

        for (int i = 0; i < size; i++) {
            unlock(pack.get(i));
        }

        return pack;
    }

    public @NotNull List<Item> nextCelestialPack(int size, int ante) {
        List<Item> pack = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            pack.add(nextPlanet(planetpl1lArr[ante], ante, true));
            if (!params.isShowman()) {
                lock(pack.get(i));
            }
        }

        if (params.isShowman()) return pack;

        for (int i = 0; i < size; i++) {
            unlock(pack.get(i));
        }

        return pack;
    }

    public @NotNull List<Item> nextSpectralPack(int size, int ante) {
        List<Item> pack = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            pack.add(nextSpectral(spectralSpeArr[ante], ante, true));

            if (!params.isShowman()) {
                lock(pack.get(i));
            }
        }

        if (params.isShowman()) return pack;

        for (int i = 0; i < size; i++) {
            unlock(pack.get(i));
        }

        return pack;
    }

    public @NotNull List<com.balatro.structs.Card> nextStandardPack(int size, int ante) {
        List<com.balatro.structs.Card> pack = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            pack.add(nextStandardCard(ante));
        }

        return pack;
    }

    public @NotNull List<JokerData> nextBuffoonPack(int size, int ante) {
        List<JokerData> pack = new ArrayList<>(size);

        JokerData joker;

        for (int i = 0; i < size; i++) {
            joker = nextJoker("buf", joker1BufArr, joker2BufArr, joker3BufArr, joker4BufArr, rarityBufArr, editionBufArr, ante, true);
            pack.add(joker);

            if (!params.isShowman()) {
                lock(joker.getJoker());
            }
        }

        if (params.isShowman()) return pack;

        for (int i = 0; i < size; i++) {
            unlock(pack.get(i).getJoker());
        }

        return pack;
    }

    // Misc methods
    public boolean isVoucherActive(Voucher voucher) {
        return params.getVouchers().contains(voucher);
    }

    public void activateVoucher(Voucher voucher) {
        params.getVouchers().add(voucher);
        lock(voucher);
        // Unlock next level voucher
        for (int i = voucher.ordinal(); i < VOUCHERS.size(); i += 2) {
            if (VOUCHERS.get(i).equals(voucher)) {
                unlock(VOUCHERS.get(i + 1));
            }
        }
    }

    public Voucher nextVoucher(int ante) {
        return randchoice(VoucherArr[ante], VOUCHERS);
    }

    public void setDeck(Deck deck) {
        params.setDeck(deck);
        switch (deck) {
            case MAGIC_DECK:
                activateVoucher(Voucher.Crystal_Ball);
                break;
            case NEBULA_DECK:
                activateVoucher(Voucher.Telescope);
                break;
            case ZODIAC_DECK:
                activateVoucher(Voucher.Tarot_Merchant);
                activateVoucher(Voucher.Planet_Merchant);
                activateVoucher(Voucher.Overstock);
                break;
            default:
                break;
        }
    }

    public Tag nextTag(int ante) {
        return randchoice(TagArr[ante], TAGS);
    }

    public Boss nextBoss(int ante) {
        List<Boss> bossPool = new ArrayList<>();
        int numBosses = 0;

        // First pass: Try to find unlocked bosses
        for (Boss boss : BOSSES) {
            if (isLocked(boss)) continue;

            if (ante % 8 == 0 && boss.notT()) {
                bossPool.add(boss);
                numBosses++;
            } else if (boss.isT()) {
                bossPool.add(boss);
                numBosses++;
            }
        }

        // If no bosses found, unlock appropriate bosses and try again
        if (numBosses == 0) {
            for (Boss boss : BOSSES) {
                if (ante % 8 == 0 && boss.notT()) {
                    unlock(boss);
                } else if (boss.isT()) {
                    unlock(boss);
                }
            }
            return nextBoss(ante);
        }

        var chosenBoss = randchoice("boss", bossPool);
        lock(chosenBoss);
        return chosenBoss;
    }

    @Override
    public void unlock(Item item) {
        lock.unlock(item);
    }

    @Override
    public void lock(@NotNull Item item) {
        lock.lock(item);
    }

    @Override
    public boolean isLocked(@NotNull Item item) {
        return lock.isLocked(item);
    }

    @Override
    public void initUnlocks(int ante, boolean freshProfile) {
        lock.initUnlocks(ante, freshProfile);
    }

    @Override
    public void initLocks(int ante, boolean freshProfile, boolean freshRun) {
        lock.initLocks(ante, freshProfile, freshRun);
    }

    @Override
    public void firstLock() {
        lock.firstLock();
    }
}

