package com.balatro.cache;

import com.balatro.SeedFinderImpl;
import com.balatro.api.Balatro;
import com.balatro.api.Item;
import com.balatro.enums.*;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.balatro.enums.LegendaryJoker.*;

public class PreProcessedSeeds {

    private List<Data> dataList;

    public void start() {
        var decimalFormat = new DecimalFormat("#,##0");
        var file = new File("cache.jkr");

        if (file.exists()) {
            long init = System.currentTimeMillis();
            dataList = readFile(file);
            System.out.println("Loaded " + decimalFormat.format(dataList.size()) + " seeds from cache in " + (System.currentTimeMillis() - init) + " ms");
        } else {

            var seeds = Balatro.search(10, 25_000_000)
                    .configuration(config -> config.maxAnte(1).disableShopQueue()
                            .disablePack(PackKind.Buffoon))
                    .filter(Perkeo.inPack().or(Triboulet.inPack()).or(Yorick.inPack()).or(Chicot.inPack()).or(Canio.inPack()))
                    .find();

            System.out.println("Seeds found: " + decimalFormat.format(seeds.size()) + " Analyzing...");

            var start = LocalDateTime.now();

            dataList = seeds.parallelStream()
                    .map(seed -> Balatro.builder(seed, 8)
                            .analyzeAll()
                            .disablePack(PackKind.Standard)
                            .analyze())
                    .map(Data::new)
                    .toList();

            seeds.clear();

            System.out.println("Finished in: " + (start.until(LocalDateTime.now(), ChronoUnit.SECONDS)) + " Seconds");
            System.gc();
            System.out.println("Used Memory: " + SeedFinderImpl.getMemory());

            var baos = new ByteArrayOutputStream();

            for (Data data : dataList) {
                data.write(baos);
            }

            System.out.println("File size: " + decimalFormat.format(baos.size()));

            try {

                Files.write(file.toPath(), baos.toByteArray());
                baos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public Set<String> search(Set<String> tokens) {
        Set<String> found = new HashSet<>();

        var items = parseSearch(tokens);

        for (Data data : dataList) {
            if (data.contains(items)) {
                found.add(data.getSeed());
            }
        }

        return found;
    }

    private @NotNull List<Item> parseSearch(Set<String> tokens) {

        List<Item> items = new ArrayList<>();

        for (Spectral value : Spectral.values()) {
            if (tokens.contains(value.name().toLowerCase())) {
                items.add(value);
            }
        }

        for (CommonJoker value : CommonJoker.values()) {
            if (tokens.contains(value.name().toLowerCase())) {
                items.add(value);
            }
        }

        for (RareJoker value : RareJoker.values()) {
            if (tokens.contains(value.name().toLowerCase())) {
                items.add(value);
            }
        }

        for (LegendaryJoker value : values()) {
            if (tokens.contains(value.name().toLowerCase())) {
                items.add(value);
            }
        }

        for (UnCommonJoker value : UnCommonJoker.values()) {
            if (tokens.contains(value.name().toLowerCase())) {
                items.add(value);
            }
        }

        for (Tag value : Tag.values()) {
            if (tokens.contains(value.name().toLowerCase())) {
                items.add(value);
            }
        }

        for (Boss value : Boss.values()) {
            if (tokens.contains(value.name().toLowerCase())) {
                items.add(value);
            }
        }

        for (Planet value : Planet.values()) {
            if (tokens.contains(value.name().toLowerCase())) {
                items.add(value);
            }
        }

        for (Tarot value : Tarot.values()) {
            if (tokens.contains(value.name().toLowerCase())) {
                items.add(value);
            }
        }

        for (Voucher value : Voucher.values()) {
            if (tokens.contains(value.name().toLowerCase())) {
                items.add(value);
            }
        }

        if (items.size() < tokens.size()) {
            var itemNames = items.stream()
                    .map(Item::getName)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            var missing = tokens.stream()
                    .filter(itemNames::contains)
                    .collect(Collectors.joining(","));

            throw new IllegalStateException("Failed to parse search, missing: " + missing);
        }

        return items;
    }

    private static @NotNull List<Data> readFile(@NotNull File file) {
        try {
            var bytes = Files.readAllBytes(file.toPath());
            int cursor = 0;

            List<Data> dataList = new ArrayList<>();

            while (cursor < bytes.length) {
                byte[] seedBytes = new byte[7];
                System.arraycopy(bytes, cursor, seedBytes, 0, 7);
                cursor += 7;
                String seed = new String(seedBytes);

                long[] data = new long[13];

                ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);

                for (int i = 0; i < 13; i++) {
                    byte[] longBytes = new byte[Long.BYTES];
                    System.arraycopy(bytes, cursor, longBytes, 0, Long.BYTES);
                    cursor += Long.BYTES;
                    byteBuffer.clear();
                    byteBuffer.put(longBytes);
                    data[i] = byteBuffer.getLong(0);
                }

                dataList.add(new Data(seed, data));
            }

            return dataList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
