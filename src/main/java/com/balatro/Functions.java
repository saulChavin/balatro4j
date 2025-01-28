package com.balatro;

import com.balatro.api.Item;
import com.balatro.enums.*;
import com.balatro.enums.Card;
import com.balatro.enums.PackType;
import com.balatro.structs.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.balatro.Util.pseudohash;
import static com.balatro.Util.round13;

public final class Functions extends Lock {

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

    public Functions(String s, InstanceParams params) {
        seed = s;
        hashedSeed = pseudohash(s);
        this.params = params;
        cache = new Cache();
    }

//    private LuaRandom getRandom(String id) {
//        return new LuaRandom(getNode(id));
//    }

    private double getNode(String id) {
        var c = cache.nodes.get(id);

        if (c == null) {
            c = pseudohash(id + seed);
            cache.nodes.put(id, c);
        }

        var value = round13((c * 1.72431234 + 2.134453429141) % 1);

        cache.nodes.put(id, value);

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

    public <T extends Item> T randchoice(String ID, @NotNull List<T> items) {
        T item = items.get(randint(ID, items.size() - 1));

        if (!params.isShowman() && isLocked(item) || "RETRY".equals(item.getName())) {
            int resample = 2;
            while (true) {
                item = items.get(randint(ID + "_resample" + resample, items.size() - 1));
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
        if (soulable && (params.isShowman() || !isLocked(Specials.THE_SOUL)) && random("soul_Tarot" + ante) > 0.997) {
            return Specials.THE_SOUL;
        }
        return randchoice("Tarot" + source + ante, TAROTS);
    }

    public Item nextPlanet(String source, int ante, boolean soulable) {
        if (soulable && (params.isShowman() || !isLocked(Specials.BLACKHOLE)) && random("soul_Planet" + ante) > 0.997) {
            return Specials.BLACKHOLE;
        }
        return randchoice("Planet" + source + ante, PLANETS);
    }

    public Item nextSpectral(String source, int ante, boolean soulable) {
        if (soulable) {
            Item forcedKey = null;

            if ((params.isShowman() || !isLocked(Specials.THE_SOUL)) && random("soul_Spectral" + ante) > 0.997) {
                forcedKey = Specials.THE_SOUL;
            }

            if ((params.isShowman() || !isLocked(Specials.BLACKHOLE)) && random("soul_Spectral" + ante) > 0.997) {
                forcedKey = Specials.BLACKHOLE;
            }

            if (forcedKey != null) {
                return forcedKey;
            }
        }
        return randchoice("Spectral" + source + ante, SPECTRALS);
    }


    static Set<String> setA = Set.of("Gros Michel", "Ice Cream", "Cavendish", "Luchador", "Turtle Bean", "Diet Cola", "Popcorn", "Ramen", "Seltzer", "Mr. Bones", "Invisible Joker");
    static Set<String> setB = Set.of("Ceremonial Dagger", "Ride the Bus", "Runner", "Constellation", "Green Joker", "Red Card", "Madness", "Square Joker", "Vampire", "Rocket", "Obelisk", "Lucky Cat", "Flash Card", "Spare Trousers", "Castle", "Wee Joker");

    public JokerData nextJoker(String source, int ante, boolean hasStickers) {
        // Get rarity
        int rarity;

        switch (source) {
            case "sou" -> rarity = 4;
            case "wra", "rta" -> rarity = 3;
            case "uta" -> rarity = 2;
            default -> {
                double rarityPoll = random("rarity" + ante + source);
                if (rarityPoll > 0.95) {
                    rarity = 3;
                } else if (rarityPoll > 0.7) {
                    rarity = 2;
                } else {
                    rarity = 1;
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

        Edition edition;
        double editionPoll = random("edi" + source + ante);

        if (editionPoll > 0.997) {
            edition = Edition.Negative;
        } else if (editionPoll > 1 - 0.006 * editionRate) {
            edition = Edition.Polychrome;
        } else if (editionPoll > 1 - 0.02 * editionRate) {
            edition = Edition.Holographic;
        } else if (editionPoll > 1 - 0.04 * editionRate) {
            edition = Edition.Foil;
        } else {
            edition = Edition.NoEdition;
        }

        // Get next joker
        Item joker;

        switch (rarity) {
            case 4 -> {
                if (params.version > 10099) {
                    joker = randchoice("Joker4", LEGENDARY_JOKERS);
                } else {
                    joker = randchoice("Joker4" + source + ante, LEGENDARY_JOKERS);
                }
            }
            case 3 -> {
                if (params.version > 10103) joker = randchoice("Joker3" + source + ante, RARE_JOKERS);
                else {
                    if (params.version > 10099) {
                        joker = randchoice("Joker3" + source + ante, RARE_JOKERS_101C);
                    } else {
                        joker = randchoice("Joker3" + source + ante, RARE_JOKERS_100);
                    }
                }
            }
            case 2 -> {
                if (params.version > 10103) {
                    joker = randchoice("Joker2" + source + ante, UNCOMMON_JOKERS);
                } else {
                    if (params.version > 10099) {
                        joker = randchoice("Joker2" + source + ante, UNCOMMON_JOKERS_101C);
                    } else {
                        joker = randchoice("Joker2" + source + ante, UNCOMMON_JOKERS_100);
                    }
                }
            }
            default -> {
                if (params.version > 10099) {
                    joker = randchoice("Joker1" + source + ante, COMMON_JOKERS);
                } else {
                    joker = randchoice("Joker1" + source + ante, COMMON_JOKERS_100);
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
                    stickerPoll = random(((source.equals("buf")) ? "packetper" : "etperpoll") + ante);
                }

                if (stickerPoll > 0.7) {
                    if (!setA.contains(joker.getName())) {
                        stickers.eternal = true;
                    }
                }

                if ((stickerPoll > 0.4 && stickerPoll <= 0.7) && (params.getStake() == Stake.Orange_Stake || params.getStake() == Stake.Gold_Stake)) {
                    if (!setB.contains(joker.getName())) {
                        stickers.perishable = true;
                    }
                }

                if (params.getStake() == Stake.Gold_Stake) {
                    stickers.rental = random(((source.equals("buf")) ? "packssjr" : "ssjr") + ante) > 0.7;
                }

            } else {
                if (params.getStake() == Stake.Black_Stake || params.getStake() == Stake.Blue_Stake || params.getStake() == Stake.Purple_Stake || params.getStake() == Stake.Orange_Stake || params.getStake() == Stake.Gold_Stake) {
                    if (!setA.contains(joker.getName())) {
                        stickers.eternal = random("stake_shop_joker_eternal" + ante) > 0.7;
                    }
                }
                if (params.version > 10099) {
                    if ((params.getStake() == Stake.Orange_Stake || params.getStake() == Stake.Gold_Stake) && !stickers.eternal) {
                        stickers.perishable = random("ssjp" + ante) > 0.49;
                    }
                    if (params.getStake() == Stake.Gold_Stake) {
                        stickers.rental = random("ssjr" + ante) > 0.7;
                    }
                }
            }
        }

        return new JokerData(joker, rarity, edition, stickers);
    }

    // Shop Logic
    public ShopInstance getShopInstance() {
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

        double cdtPoll = random("cdt" + ante) * shop.getTotalRate();

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
                var jkr = nextJoker("sho", ante, true);
                return new ShopItem(type, jkr.joker, jkr);
            }
            case Type.Tarot -> {
                return new ShopItem(type, nextTarot("sho", ante, false));
            }
            case Type.Planet -> {
                return new ShopItem(type, nextPlanet("sho", ante, false));
            }
            case Type.Spectral -> {
                return new ShopItem(type, nextSpectral("sho", ante, false));
            }
        }

        return new ShopItem();
    }

    public PackType nextPack(int ante) {
        if (ante <= 2 && !cache.generatedFirstPack && params.version > 10099) {
            cache.generatedFirstPack = true;
            return PackType.Buffoon_Pack;
        }

        return randweightedchoice("shop_pack" + ante, PACKS);
    }

    public com.balatro.structs.Pack packInfo(@NotNull PackType pack) {
        if (pack.isMega()) {
            return new com.balatro.structs.Pack(pack, (pack.isBuffon() || pack.isSpectral()) ? 4 : 5, 2);
        } else if (pack.isJumbo()) {
            return new com.balatro.structs.Pack(pack, (pack.isBuffon() || pack.isSpectral()) ? 4 : 5, 1);
        } else {
            return new com.balatro.structs.Pack(pack, (pack.isBuffon() || pack.isSpectral()) ? 2 : 3, 1);
        }
    }

    public com.balatro.structs.Card nextStandardCard(int ante) {
        // Enhancement
        String enhancement;

        if (random("stdset" + ante) <= 0.6) {
            enhancement = "No Enhancement";
        } else {
            enhancement = randchoice("Enhancedsta" + ante, ENHANCEMENTS).getName();
        }

        // Edition
        Edition edition;

        double editionPoll = random("standard_edition" + ante);

        if (editionPoll > 0.988) {
            edition = Edition.Polychrome;
        } else if (editionPoll > 0.96) {
            edition = Edition.Polychrome;
        } else if (editionPoll > 0.92) {
            edition = Edition.Foil;
        } else {
            edition = Edition.NoEdition;
        }

        // Seal
        Seal seal;

        if (random("stdseal" + ante) <= 0.8) {
            seal = Seal.NoSeal;
        } else {
            double sealPoll = random("stdsealtype" + ante);
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

        var base = randchoice("frontsta" + ante, CARDS);

        return new com.balatro.structs.Card(base.getName(), enhancement, edition, seal);
    }

    public @NotNull List<Item> nextArcanaPack(int size, int ante) {
        List<Item> pack = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            if (isVoucherActive(Voucher.Omen_Globe) && random("omen_globe") > 0.8) {
                pack.add(nextSpectral("ar2", ante, true));
            } else {
                pack.add(nextTarot("ar1", ante, true));
            }
            if (!params.isShowman()) {
                lock(pack.get(i));
            }
        }

        for (int i = 0; i < size; i++) {
            unlock(pack.get(i));
        }

        return pack;
    }

    public @NotNull List<Item> nextCelestialPack(int size, int ante) {
        List<Item> pack = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            pack.add(nextPlanet("pl1", ante, true));
            if (!params.isShowman()) {
                lock(pack.get(i));
            }
        }

        for (int i = 0; i < size; i++) {
            unlock(pack.get(i));
        }

        return pack;
    }

    public @NotNull List<Item> nextSpectralPack(int size, int ante) {
        List<Item> pack = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            pack.add(nextSpectral("spe", ante, true));

            if (!params.isShowman()) {
                lock(pack.get(i));
            }
        }

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

        for (int i = 0; i < size; i++) {
            pack.add(nextJoker("buf", ante, true));
            if (!params.isShowman()) {
                lock(pack.get(i).getJoker());
            }
        }
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
        for (int i = 0; i < VOUCHERS.size(); i += 2) {
            if (VOUCHERS.get(i).equals(voucher)) {
                unlock(VOUCHERS.get(i + 1).getName());
            }
        }
    }

    public Voucher nextVoucher(int ante) {
        return randchoice("Voucher" + ante, VOUCHERS);
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
        return randchoice("Tag" + ante, TAGS);
    }

    public Boss nextBoss(int ante) {
        List<Boss> bossPool = new ArrayList<>();
        int numBosses = 0;

        // First pass: Try to find unlocked bosses
        for (Boss boss : BOSSES) {
            if (!isLocked(boss)) {
                if ((ante % 8 == 0 && boss.charAt(0) != 'T') ||
                        (ante % 8 != 0 && boss.charAt(0) == 'T')) {
                    bossPool.add(boss);
                    numBosses++;
                }
            }
        }

        // If no bosses found, unlock appropriate bosses and try again
        if (numBosses == 0) {
            for (Boss boss : BOSSES) {
                if ((ante % 8 == 0 && boss.charAt(0) != 'T') ||
                        (ante % 8 != 0 && boss.charAt(0) == 'T')) {
                    unlock(boss.getName());
                }
            }
            return nextBoss(ante);
        }

        var chosenBoss = randchoice("boss", bossPool);
        lock(chosenBoss);
        return chosenBoss;
    }

}

