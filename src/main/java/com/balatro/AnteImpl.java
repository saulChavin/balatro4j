package com.balatro;

import com.balatro.api.Ante;
import com.balatro.api.Item;
import com.balatro.api.Joker;
import com.balatro.enums.*;
import com.balatro.structs.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

final class AnteImpl implements Ante {

    private final int ante;
    @JsonIgnore
    private final Functions functions;
    private final ShopQueue shopQueue;
    @JsonIgnore
    private final Set<String> shop;
    private final Set<Tag> tags;
    private Voucher voucher;
    private Boss boss;
    private final List<PackInfo> packInfos;

    //Cache
    private Map<String, JokerData> legendaryJokers;

    AnteImpl(int ante, Functions functions) {
        this.ante = ante;
        this.functions = functions;
        this.tags = new HashSet<>(2);
        this.shopQueue = new ShopQueue();
        this.shop = new HashSet<>(20);
        this.packInfos = new ArrayList<>(10);
    }

    @Override
    public boolean hasBoss(Boss boss) {
        return this.boss == boss;
    }

    @Override
    public boolean hasInShop(@NotNull Item item) {
        return shop.contains(item.getName());
    }

    @Override
    public boolean hasInShop(@NotNull Item item, int index) {
        if (index > shopQueue.size()) {
            return false;
        }

        for (int i = 0; i < index; i++) {
            if (shopQueue.get(i).equals(item)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public long countLegendary() {
        hasLegendary(LegendaryJoker.Perkeo);
        return legendaryJokers.size();
    }

    void addTag(Tag tag) {
        tags.add(tag);
    }

    void addToQueue(@NotNull ShopItem value, Edition sticker) {
        shop.add(value.getItem().getName());
        shopQueue.add(new SearchableItem(value.getItem(), sticker));
    }

    void setBoss(Boss boss) {
        this.boss = boss;
    }

    void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    void addPack(@NotNull PackInfo packInfo, Set<Option> options) {
        packInfo.setOptions(options);
        packInfos.add(packInfo);
    }

    @Contract(" -> new")
    @Override
    public @NotNull HashMap<String, JokerData> getLegendaryJokers() {
        hasLegendary(LegendaryJoker.Perkeo);//pre-compute
        return new HashMap<>(legendaryJokers);
    }

    @Override
    public int getBufferedJokerCount() {
        return (int) shopQueue.stream()
                .filter(SearchableItem::hasSticker)
                .filter(a -> a.item() instanceof Joker)
                .count();
    }

    @Override
    public Double getScore() {
        var score = 0.0;
        score += countLegendary() * 5;

        if (hasLegendary(LegendaryJoker.Yorick) || hasLegendary(LegendaryJoker.Canio)) {
            score -= 5;
        }

        for (JokerData value : legendaryJokers.values()) {
            if (value.getEdition() == Edition.Negative) {
                score += 5;
            }

            if (value.getEdition() == Edition.Holographic) {
                score += 2;
            }

            if (value.getEdition() == Edition.Polychrome) {
                score += 1;
            }

            if (value.getEdition() == Edition.Foil) {
                score += 1;
            }
        }

        if (hasInShop(RareJoker.Brainstorm)) {
            if (hasInShop(RareJoker.Brainstorm, 6)) {
                score += 2.5;
            } else {
                score += 1.5;
            }
        }


        if (hasInShop(RareJoker.Blueprint)) {
            if (hasInShop(RareJoker.Blueprint, 6)) {
                score += 2.5;
            } else {
                score += 1.5;
            }
        }

        if (hasInShop(UnCommonJoker.Showman)) {
            score += 0.5;
        }

        if (hasInShop(UnCommonJoker.Constellation)) {
            score += 1.0;
        }
        if (hasInShop(RareJoker.Invisible_Joker)) {
            if (hasInShop(RareJoker.Invisible_Joker, 6)) {
                score += 2;
            } else {
                score += 1;
            }
        }

        if (hasInShop(UnCommonJoker.Sock_and_Buskin)) {
            if (hasLegendary(LegendaryJoker.Triboulet)) {
                score += 2.0;
            } else {
                score += 0.5;
            }
        }

        if (hasInShop(CommonJoker.Hanging_Chad)) {
            if (hasLegendary(LegendaryJoker.Triboulet)) {
                score += 1.0;
            } else {
                score += 0.5;
            }
        }

        if (hasInShop(CommonJoker.Fortune_Teller)) {
            score += 0.3;
        }

        if (hasInShop(RareJoker.DNA)) {
            score += 0.5;
        }

        score += getShopQueue().stream()
                         .filter(SearchableItem::hasSticker)
                         .count() * 0.5;

        score += getShopQueue().stream()
                         .filter(a -> a.hasEdition(Edition.Negative))
                         .count() * 1.0;

        score += packInfos.stream()
                         .map(PackInfo::getKind)
                         .filter(kind -> kind == PackKind.Spectral)
                         .count() * 0.3;

        for (Tag tag : tags) {
            if (tag == Tag.Negative_Tag) {
                score += 5;
            }

            if (tag == Tag.Charm_Tag) {
                score += 0.5;
            }
        }

        if (boss == Boss.The_Arm) {
            score -= 0.5;
        }

        if (hasInPack(Specials.BLACKHOLE)) {
            score += 5.0;
        }

        if (voucher == Voucher.Blank) {
            score += 1.0;
        }

        if (voucher == Voucher.Clearance_Sale) {
            score += 0.5;
        }

        if (voucher == Voucher.Overstock) {
            score += 0.2;
        }

        if (voucher == Voucher.Liquidation) {
            score += 0.5;
        }

        if (voucher == Voucher.Hieroglyph) {
            score += 0.5;
        }

        if (voucher == Voucher.Paint_Brush) {
            score += 0.5;
        }

        if (voucher == Voucher.Recyclomancy) {
            score += 0.5;
        }

        if (voucher == Voucher.Grabber) {
            score += 0.5;
        }

        if (voucher == Voucher.Wasteful) {
            score += 0.2;
        }

        for (PackInfo pack : packInfos) {
            if (pack.getKind() == PackKind.Standard) {
                score -= 1;
            }

            if (pack.getKind() == PackKind.Buffoon) {
                for (Option option : pack.getOptions()) {
                    if (option.edition() == Edition.Negative) {
                        score += 2.5;
                    }
                }
            }
        }

        return score;
    }

    @Override
    public boolean hasLegendary(LegendaryJoker... jokers) {
        if (legendaryJokers != null) {
            for (LegendaryJoker joker : jokers) {
                if (!legendaryJokers.containsKey(joker.getName())) {
                    return false;
                }
            }

            return true;
        }

        legendaryJokers = new HashMap<>();

        int souls = countInPack(Specials.THE_SOUL);

        if (souls < jokers.length) return false;

        for (int i = 0; i < souls; i++) {
            var data = functions.nextJoker("sou", Functions.joker1SouArr, Functions.joker2SouArr, Functions.joker3SouArr,
                    Functions.joker4SouArr, Functions.raritySouArr, Functions.editionSouArr, ante, false);
            legendaryJokers.put(data.joker.getName(), data);
        }

        for (LegendaryJoker joker : jokers) {
            if (!legendaryJokers.containsKey(joker.getName())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean hasInPack(@NotNull Item item) {
        if (item instanceof LegendaryJoker joker) {
            return hasLegendary(joker);
        }

        for (PackInfo packInfo : packInfos) {
            if (packInfo.containsOption(item.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPack(PackType packType) {
        for (PackInfo packInfo : packInfos) {
            if (packInfo.getType() == packType) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasInSpectral(@NotNull Item item) {
        if (item instanceof LegendaryJoker joker) {
            return hasLegendary(joker);
        }

        for (PackInfo packInfo : packInfos) {
            if (packInfo.getKind() != PackKind.Spectral) {
                continue;
            }

            if (packInfo.containsOption(item.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasVoucher(Voucher voucher) {
        return this.voucher == voucher;
    }

    @Override
    public int countInPack(@NotNull Item item) {
        if (item instanceof LegendaryJoker) {
            item = Specials.THE_SOUL;
        }

        int count = 0;
        for (PackInfo packInfo : packInfos) {
            if (packInfo.containsOption(item.getName())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean hasInBuffonPack(@NotNull Item item) {
        for (PackInfo packInfo : packInfos) {
            if (packInfo.getKind() != PackKind.Buffoon) {
                continue;
            }

            if (packInfo.containsOption(item.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getAnte() {
        return ante;
    }

    @JsonIgnore
    public Set<String> getShop() {
        return new HashSet<>(shop);
    }

    @Override
    public ShopQueue getShopQueue() {
        return new ShopQueue(shopQueue);
    }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    @Override
    public Voucher getVoucher() {
        return voucher;
    }

    @Override
    public Boss getBoss() {
        return boss;
    }

    @Override
    public List<PackInfo> getPacks() {
        return new ArrayList<>(packInfos);
    }

    @Override
    public Set<Joker> getJokers() {
        return shopQueue.stream()
                .map(SearchableItem::item)
                .filter(a -> a instanceof Joker)
                .map(a -> (Joker) a)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Joker> getRareJokers() {
        return shopQueue.stream()
                .map(SearchableItem::item)
                .filter(a -> a instanceof Joker)
                .map(a -> (Joker) a)
                .filter(Joker::isRare)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Joker> getUncommonJokers() {
        return shopQueue.stream()
                .map(SearchableItem::item)
                .filter(a -> a instanceof Joker)
                .map(a -> (Joker) a)
                .filter(Joker::isUncommon)
                .collect(Collectors.toSet());
    }

    @Override
    public int getNegativeJokerCount() {
        return (int) shopQueue.stream()
                .filter(a -> a.hasEdition(Edition.Negative))
                .map(SearchableItem::item)
                .filter(a -> a instanceof Joker)
                .map(a -> (Joker) a)
                .count();
    }

    @Override
    public Set<Tarot> getTarots() {
        return shopQueue.stream()
                .map(SearchableItem::item)
                .filter(a -> a instanceof Tarot)
                .map(a -> (Tarot) a)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Planet> getPlanets() {
        return shopQueue.stream()
                .map(SearchableItem::item)
                .filter(a -> a instanceof Planet)
                .map(a -> (Planet) a)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Spectral> getSpectrals() {
        return packInfos.stream()
                .filter(a -> a.getKind() == PackKind.Spectral)
                .flatMap(a -> a.getOptions().stream()
                        .map(Option::item))
                .filter(a -> a instanceof Spectral)
                .map(a -> (Spectral) a)
                .collect(Collectors.toSet());
    }

    @Override
    public int getStandardPackCount() {
        return (int) packInfos.stream()
                .filter(a -> a.getKind() == PackKind.Standard)
                .count();
    }

    @Override
    public int getJokerPackCount() {
        return (int) packInfos.stream()
                .filter(a -> a.getKind() == PackKind.Buffoon)
                .count();
    }

    @Override
    public int getSpectralPackCount() {
        return (int) packInfos.stream()
                .filter(a -> a.getKind() == PackKind.Spectral)
                .count();
    }

    @Override
    public int getTarotPackCount() {
        return (int) packInfos.stream()
                .filter(a -> a.getKind() == PackKind.Arcana)
                .count();
    }

    @Override
    public int getPlanetPackCount() {
        return (int) packInfos.stream()
                .filter(a -> a.getKind() == PackKind.Celestial)
                .count();
    }
}
