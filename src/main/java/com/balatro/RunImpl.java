package com.balatro;

import com.balatro.api.Ante;
import com.balatro.api.Run;
import com.balatro.enums.*;
import com.balatro.api.Item;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

record RunImpl(String seed, List<Ante> antes) implements Run {

    static final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public String toJson() {
        try {
            return mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ante getAnte(int ante) {
        return antes.get(ante - 1);
    }

    @Override
    public boolean hasLegendary(int ante, LegendaryJoker... jokers) {
        return antes.get(ante - 1).hasLegendary(jokers);
    }

    @Override
    public boolean hasInPack(int ante, Item item) {
        if (item instanceof LegendaryJoker joker) {
            return hasLegendary(ante, joker);
        }

        return antes.get(ante - 1).hasInPack(item);
    }

    @Override
    public boolean hasInPack(Item item) {
        return antes.stream().anyMatch(a -> a.hasInPack(item));
    }

    @Override
    public boolean hasInShop(int ante, @NotNull Item item) {
        return antes.get(ante - 1).hasInShop(item);
    }

    @Override
    public boolean hasInShop(Item item) {
        return antes.stream().anyMatch(a -> a.hasInShop(item));
    }

    @Override
    public boolean hasInShop(int ante, @NotNull Item item, int index) {
        return antes.get(ante - 1).hasInShop(item, index);
    }

    @Override
    public long countLegendary() {
        return antes.stream()
                .mapToLong(Ante::countLegendary)
                .sum();
    }

    @Override
    public long countLegendary(int ante) {
        return antes.get(ante - 1).countInPack(Specials.THE_SOUL);
    }

    @Override
    public String toString() {
        return seed;
    }

    @Override
    public boolean hasInSpectral(@NotNull Item item) {
        for (Ante ante : antes) {
            if (ante.hasInSpectral(item)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasPack(PackType packType) {
        for (Ante ante : antes) {
            if (ante.hasPack(packType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasInSpectral(int ante, @NotNull Item item) {
        return antes.get(ante - 1).hasInSpectral(item);
    }

    @Override
    public boolean hasPack(int ante, PackType packType) {
        return antes.get(ante - 1).hasPack(packType);
    }

    @Override
    public boolean hasInBuffonPack(@NotNull Item item) {
        for (Ante ante : antes) {
            if (ante.hasInBuffonPack(item)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasInBuffonPack(int ante, @NotNull Item item) {
        return antes.get(ante - 1).hasInBuffonPack(item);
    }

    @Override
    public boolean hasVoucher(Voucher voucher) {
        for (Ante ante : antes) {
            if (ante.getVoucher() == voucher) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasVoucher(int ante, Voucher voucher) {
        return antes.get(ante - 1).getVoucher() == voucher;
    }

    @Override
    public boolean hasBoss(int ante, Boss boss) {
        return false;
    }

    @Override
    public boolean hasBoss(Boss boss) {
        for (Ante ante : antes) {
            if (ante.getBoss() == boss) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasInShop(@NotNull Item item, int index) {
        for (Ante ante : antes) {
            if (ante.hasInShop(item, index)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int countInPack(@NotNull Item item) {
        return antes.stream()
                .mapToInt(a -> a.countInPack(item))
                .sum();
    }

    @Override
    public boolean hasLegendary(LegendaryJoker... jokers) {
        for (Ante ante : antes) {
            if (ante.hasLegendary(jokers)) {
                return true;
            }
        }
        return false;
    }
}
