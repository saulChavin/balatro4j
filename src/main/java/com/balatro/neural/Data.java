package com.balatro.neural;

import com.balatro.SeedFinderImpl;
import com.balatro.api.*;
import com.balatro.enums.*;
import com.balatro.structs.JokerData;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.balatro.enums.LegendaryJoker.*;

public final class Data {

    public static void main(String[] args) {
        var decimalFormat = new DecimalFormat("#,##0");
        var file = new File("cache.jkr");
        List<Data> dataList;

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

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Type your search: ");
            var line = scanner.nextLine().toLowerCase();

            if (line.equalsIgnoreCase("exit")) {
                break;
            }

            var tokens = new HashSet<String>();

            for (String s : line.split(",")) {
                tokens.add(s.trim());
            }

            System.out.println(tokens);

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

                System.out.println("Failed to parse search, missing: " + missing);
                break;
            }

            long init = System.currentTimeMillis();

            Set<String> found = new HashSet<>();

            for (Data data : dataList) {
                if (data.contains(items)) {
                    found.add(data.getSeed());
                }
            }

            System.out.println("Found " + decimalFormat.format(found.size()) + " seeds in " + (System.currentTimeMillis() - init) + " ms for " + items.size() + " items..");
            System.out.println("Print seeds?");

            line = scanner.nextLine();

            if (line.equalsIgnoreCase("y")) {
                for (String s : found) {
                    System.out.println(s);
                }
            }

            if (line.matches("[0-9]{1,2}")) {
                int count = Integer.parseInt(line);
                int i = 0;
                for (String s : found) {
                    System.out.println(s);
                    i++;
                    if (i == count) {
                        break;
                    }
                }
            }

        }

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

    public void write(@NotNull ByteArrayOutputStream baos) {
        baos.writeBytes(seed.getBytes());
        int byteLength = data.length * Long.BYTES; // Long.BYTES is 8
        byte[] byteArray = new byte[byteLength];
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);

        for (int i = 0; i < data.length; i++) {
            byteBuffer.putLong(0, data[i]);
            System.arraycopy(byteBuffer.array(), 0, byteArray, i * Long.BYTES, Long.BYTES);
        }

        baos.writeBytes(byteArray);
    }

    private final String seed;
    private final long[] data;

    private void turnOn(@NotNull Item item) {
        data[item.getYIndex()] = data[item.getYIndex()] | (1L << item.ordinal());
    }

    private boolean isOn(@NotNull Item item) {
        return (data[item.getYIndex()] & (1L << item.ordinal())) != 0;
    }

    public Data(String seed, long[] data) {
        this.seed = seed;
        this.data = data;
    }

    public Data(@NotNull Run run) {
        this.seed = run.seed();
        this.data = new long[13];

        for (Ante ante : run.antes()) {
            for (Joker joker : ante.getJokers()) {
                turnOn(joker);
            }

            turnOn(ante.getVoucher());
            turnOn(ante.getBoss());

            for (Tag tag : ante.getTags()) {
                turnOn(tag);
            }

            for (Tarot tarot : ante.getTarots()) {
                turnOn(tarot);
            }

            for (Planet planet : ante.getPlanets()) {
                turnOn(planet);
            }

            for (JokerData value : ante.getLegendaryJokers().values()) {
                turnOn(value);
            }

            for (Spectral spectral : ante.getSpectrals()) {
                turnOn(spectral);
            }
        }
    }

    public String getSeed() {
        return seed;
    }

    public boolean contains(@NotNull List<Item> items) {
        for (Item item : items) {
            if (!isOn(item)) {
                return false;
            }
        }

        return true;
    }


}
