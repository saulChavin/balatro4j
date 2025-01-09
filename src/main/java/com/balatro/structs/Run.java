package com.balatro.structs;

import com.balatro.enums.LegendaryJoker;
import com.balatro.enums.Named;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public record Run(String seed, List<Ante> antes) {

    static final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public String toJson() {
        try {
            return mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasLegendary(int ante, LegendaryJoker... jokers) {
        return antes.get(ante - 1).hasLegendary(jokers);
    }

    public boolean hasInPack(int ante, String name) {
        return antes.get(ante - 1).containsInPack(name);
    }

    public boolean hasInShop(int ante, Named named) {
        return antes.get(ante - 1).hasInShop(named.getName());
    }

    public boolean hasInShop(int ante, Named named, int index) {
        return antes.get(ante - 1).hasInShop(named.getName(), index);
    }

    public long countLegendary() {
        return antes.stream()
                .filter(a -> a.containsInPack("The Soul")).count();
    }

    public long countLegendary(int ante) {
        return antes.get(ante - 1).countInPack("The Soul");
    }
}
