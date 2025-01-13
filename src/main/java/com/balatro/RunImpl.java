package com.balatro;

import com.balatro.api.Run;
import com.balatro.enums.LegendaryJoker;
import com.balatro.api.Named;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

record RunImpl(String seed, List<AnteImpl> antes) implements Run {

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
    public AnteImpl getAnte(int ante) {
        return antes.get(ante - 1);
    }

    @Override
    public boolean hasLegendary(int ante, LegendaryJoker... jokers) {
        return antes.get(ante - 1).hasLegendary(jokers);
    }

    @Override
    public boolean hasInPack(int ante, String name) {
        return antes.get(ante - 1).containsInPack(name);
    }

    @Override
    public boolean hasInPack(String name) {
        return antes.stream().anyMatch(a -> a.containsInPack(name));
    }

    @Override
    public boolean hasInShop(int ante, @NotNull Named named) {
        return antes.get(ante - 1).hasInShop(named.getName());
    }

    @Override
    public boolean hasInShop(Named named) {
        return antes.stream().anyMatch(a -> a.hasInShop(named.getName()));
    }

    @Override
    public boolean hasInShop(int ante, @NotNull Named named, int index) {
        return antes.get(ante - 1).hasInShop(named.getName(), index);
    }

    @Override
    public long countLegendary() {
        return antes.stream()
                .filter(a -> a.containsInPack("The Soul")).count();
    }

    @Override
    public long countLegendary(int ante) {
        return antes.get(ante - 1).countInPack("The Soul");
    }
}
