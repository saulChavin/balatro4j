package com.balatro.api;


import com.balatro.enums.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Run extends Queryable {

    ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    String seed();

    String toJson();

    Ante getAnte(int ante);

    List<Ante> antes();

    default Ante getFirstAnte() {
        return getAnte(1);
    }

    default Ante getSecondAnte() {
        return getAnte(2);
    }

    default Ante getThirdAnte() {
        return getAnte(3);
    }

    default int getBufferedJokerCount() {
        return antes()
                .stream()
                .mapToInt(Ante::getBufferedJokerCount)
                .sum();
    }

    default double getScore() {
        var score = 0.0;

        for (var ante : antes()) {
            score += ante.getScore();
        }

        return score;
    }

    default Set<String> getJokers() {
        return antes().stream()
                .flatMap(a -> a.getJokers().stream().map(Item::getName))
                .collect(Collectors.toSet());
    }

    default Set<String> getRareJokers() {
        return antes().stream()
                .flatMap(a -> a.getRareJokers().stream().map(Item::getName))
                .collect(Collectors.toSet());
    }

    default Set<String> getUncommonJokers() {
        return antes().stream()
                .flatMap(a -> a.getUncommonJokers().stream().map(Item::getName))
                .collect(Collectors.toSet());
    }

    default int getNegativeJokerCount() {
        return antes().stream()
                .mapToInt(Ante::getNegativeJokerCount)
                .sum();
    }

    default Set<String> getTarots() {
        return antes().stream()
                .flatMap(a -> a.getTarots().stream().map(Item::getName))
                .collect(Collectors.toSet());
    }

    default Set<String> getTags() {
        return antes().stream()
                .flatMap(a -> a.getTags().stream().map(Tag::getName))
                .collect(Collectors.toSet());
    }

    default Set<String> getPlanets() {
        return antes().stream()
                .flatMap(a -> a.getPlanets().stream().map(Item::getName))
                .collect(Collectors.toSet());
    }

    default Set<String> getVouchers() {
        return antes().stream()
                .map(a -> a.getVoucher().getName())
                .collect(Collectors.toSet());
    }

    default Set<String> getBosses() {
        return antes().stream()
                .map(a -> a.getBoss().getName())
                .collect(Collectors.toSet());
    }


    default Set<String> getLegendaryJokers() {
        return antes().stream()
                .flatMap(a -> a.getLegendaryJokers().stream())
                .collect(Collectors.toSet());
    }

    default int getStandardPackCount() {
        return antes().stream()
                .mapToInt(Ante::getStandardPackCount)
                .sum();
    }

    default int getJokerPackCount() {
        return antes().stream()
                .mapToInt(Ante::getJokerPackCount)
                .sum();
    }

    default int getSpectralPackCount() {
        return antes().stream()
                .mapToInt(Ante::getSpectralPackCount)
                .sum();
    }

    default int getTarotPackCount() {
        return antes().stream()
                .mapToInt(Ante::getTarotPackCount)
                .sum();
    }

    default int getPlanetPackCount() {
        return antes().stream()
                .mapToInt(Ante::getPlanetPackCount)
                .sum();
    }
}
