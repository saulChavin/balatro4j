package com.balatro;

import com.balatro.api.Ante;
import com.balatro.api.Item;
import com.balatro.enums.*;
import com.balatro.structs.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class AnteImpl implements Ante {

    private final int ante;
    private final Functions functions;
    private final ShopQueue shopQueue;
    @JsonIgnore
    private final Set<String> shop;
    private final Set<Tag> tags;
    private Voucher voucher;
    private Boss boss;
    private final List<Pack> packs;

    //Cache
    private Set<String> legendaryJokers;

    AnteImpl(int ante, Functions functions) {
        this.ante = ante;
        this.functions = functions;
        this.tags = new HashSet<>(2);
        this.shopQueue = new ShopQueue();
        this.shop = new HashSet<>(20);
        this.packs = new ArrayList<>(10);
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
        int count = 0;
        for (Pack pack : packs) {
            if (pack.containsOption(Specials.THE_SOUL.getName())) {
                count++;
            }
        }
        return count;
    }

    void addTag(Tag tag) {
        tags.add(tag);
    }

    void addToQueue(@NotNull ShopItem value, Edition sticker) {
        shop.add(value.getItem());
        shopQueue.add(new SearchableItem(value.getItem(), sticker));
    }

    void setBoss(Boss boss) {
        this.boss = boss;
    }

    void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    void addPack(@NotNull Pack pack, Set<Option> options) {
        pack.setOptions(options);
        packs.add(pack);
    }

    @Override
    public boolean hasLegendary(LegendaryJoker... jokers) {
        if (legendaryJokers != null) {
            for (LegendaryJoker joker : jokers) {
                if (!legendaryJokers.contains(joker.getName())) {
                    return false;
                }
            }

            return true;
        }

        legendaryJokers = new HashSet<>();

        int souls = countInPack(Specials.THE_SOUL);

        if (souls < jokers.length) return false;

        for (int i = 0; i < souls; i++) {
            legendaryJokers.add(functions.nextJoker("sou", ante, false).joker);
        }

        for (LegendaryJoker joker : jokers) {
            if (!legendaryJokers.contains(joker.getName())) {
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

        for (Pack pack : packs) {
            if (pack.containsOption(item.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPack(PackType packType) {
        for (Pack pack : packs) {
            if (pack.getType() == packType) {
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

        for (Pack pack : packs) {
            if (pack.getKind() != PackKind.Spectral) {
                continue;
            }

            if (pack.containsOption(item.getName())) {
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
        for (Pack pack : packs) {
            if (pack.containsOption(item.getName())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean hasInBuffonPack(@NotNull Item item) {
        for (Pack pack : packs) {
            if (pack.getKind() != PackKind.Buffoon) {
                continue;
            }

            if (pack.containsOption(item.getName())) {
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
    public List<Pack> getPacks() {
        return new ArrayList<>(packs);
    }
}
