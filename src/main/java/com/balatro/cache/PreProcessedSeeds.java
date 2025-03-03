package com.balatro.cache;

import com.balatro.SeedFinderImpl;
import com.balatro.api.Balatro;
import com.balatro.api.Item;
import com.balatro.enums.*;
import com.balatro.structs.EditionItem;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.balatro.enums.LegendaryJoker.*;

public class PreProcessedSeeds {

    private List<Data> dataList;

    public static void main(String[] args) {
        var p = new PreProcessedSeeds();
        p.start(10, 100_000_000);


        var result = p.search(List.of(Perkeo, Triboulet, RareJoker.Blueprint, RareJoker.Brainstorm,
                UnCommonJoker.Sock_and_Buskin, CommonJoker.Hanging_Chad, RareJoker.Invisible_Joker));

        for (QueryResult queryResult : result) {
            System.out.println(queryResult.seed() + " " + queryResult.score());
        }
    }

    public void start() {
        start(Runtime.getRuntime().availableProcessors(), 1_000_000);
    }

    public void start(int parallelism, int seedsPerThread) {
        var decimalFormat = new DecimalFormat("#,##0");
        var file = new File("perkeo.jkr");
        System.out.println("Loading seeds from cache: " + file.getAbsolutePath());

        if (file.exists()) {
            long init = System.currentTimeMillis();
            dataList = readFile(file);
            System.out.println("Loaded " + decimalFormat.format(dataList.size()) + " seeds from cache in " + (System.currentTimeMillis() - init) + " ms");
        } else {
            var seeds = Balatro.search(parallelism, seedsPerThread)
                    .configuration(config -> config.maxAnte(1).disableShopQueue()
                            .disablePack(PackKind.Buffoon))
                    .filter(Perkeo.inPack(Edition.Negative)
                            .or(Triboulet.inPack(Edition.Negative))
                            .or(Yorick.inPack(Edition.Negative))
                            .or(Chicot.inPack(Edition.Negative))
                            .or(Canio.inPack(Edition.Negative)))
                    .find();

            System.out.println("Seeds found: " + decimalFormat.format(seeds.size()) + " Analyzing...");

            var start = LocalDateTime.now();

            dataList = seeds.parallelStream()
                    .map(seed -> Balatro.builder(seed, 8)
                            .enableAll()
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


    public List<QueryResult> search(@NotNull List<Item> items) {
        List<QueryResult> found = new ArrayList<>();

        var editionItems = items.stream()
                .map(i -> {
                    if (i instanceof EditionItem ei) {
                        return ei;
                    }

                    return new EditionItem(i, Edition.NoEdition);
                }).toList();

        for (Data data : dataList) {
            if (data.contains(editionItems)) {
                found.add(new QueryResult(data.getSeed(), data.getScore()));
            }
        }

        found.sort((a, b) -> Integer.compare(b.score(), a.score()));

        return found;
    }

    public Set<String> search(@NotNull Set<String> tokens) {
        return find(tokens.stream()
                .map(Query::new)
                .toList())
                .stream()
                .map(QueryResult::seed)
                .collect(Collectors.toSet());
    }

    public List<QueryResult> find(List<Query> tokens) {
        List<QueryResult> found = new ArrayList<>();

        var items = parseSearch(tokens);

        for (Data data : dataList) {
            if (data.contains(items)) {
                found.add(new QueryResult(data.getSeed(), data.getScore()));
            }
        }

        return found;
    }

    private @NotNull List<EditionItem> parseSearch(@NotNull List<Query> queries) {
        List<EditionItem> items = new ArrayList<>(queries.size());

        for (Query query : queries) {
            for (Spectral value : Spectral.values()) {
                if (query.getItem().equalsIgnoreCase(value.name())) {
                    items.add(new EditionItem(value, query.getEdition()));
                    break;
                }
            }

            for (CommonJoker value : CommonJoker.values()) {
                if (query.getItem().equalsIgnoreCase(value.name())) {
                    items.add(new EditionItem(value, query.getEdition()));
                    break;
                }
            }

            for (RareJoker value : RareJoker.values()) {
                if (query.getItem().equalsIgnoreCase(value.name())) {
                    items.add(new EditionItem(value, query.getEdition()));
                    break;
                }
            }

            for (LegendaryJoker value : values()) {
                if (query.getItem().equalsIgnoreCase(value.name())) {
                    items.add(new EditionItem(value, query.getEdition()));
                    break;
                }
            }

            for (UnCommonJoker value : UnCommonJoker.values()) {
                if (query.getItem().equalsIgnoreCase(value.name())) {
                    items.add(new EditionItem(value, query.getEdition()));
                    break;
                }
            }
            for (Tag value : Tag.values()) {
                if (query.getItem().equalsIgnoreCase(value.name())) {
                    items.add(new EditionItem(value, query.getEdition()));
                    break;
                }
            }

            for (Boss value : Boss.values()) {
                if (query.getItem().equalsIgnoreCase(value.name())) {
                    items.add(new EditionItem(value, query.getEdition()));
                    break;
                }
            }

            for (Planet value : Planet.values()) {
                if (query.getItem().equalsIgnoreCase(value.name())) {
                    items.add(new EditionItem(value, query.getEdition()));
                    break;
                }
            }

            for (Tarot value : Tarot.values()) {
                if (query.getItem().equalsIgnoreCase(value.name())) {
                    items.add(new EditionItem(value, query.getEdition()));
                    break;
                }
            }

            for (Voucher value : Voucher.values()) {
                if (query.getItem().equalsIgnoreCase(value.name())) {
                    items.add(new EditionItem(value, query.getEdition()));
                    break;
                }
            }
        }

        if (items.size() < queries.size()) {
            var itemNames = items.stream()
                    .map(Item::getName)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            var missing = queries.stream()
                    .map(Query::getItem)
                    .filter(item -> !itemNames.contains(item.toLowerCase()))
                    .collect(Collectors.joining(","));

            throw new IllegalStateException("Failed to parse search, missing: " + missing + ", tokens %s items %s".formatted(queries.size(), items.size()));
        }

        return items;
    }

    private static @NotNull List<Data> readFile(@NotNull File file) {
        List<Data> dataList = new ArrayList<>();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] seedBytes = new byte[7];
            byte[] longBytes = new byte[Long.BYTES];
            byte[] intBytes = new byte[Integer.BYTES];
            ByteBuffer longBuffer = ByteBuffer.allocate(Long.BYTES);
            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);

            while (bis.read(seedBytes) != -1) {
                String seed = new String(seedBytes);
                long[] data = new long[Data.DATA_SIZE];

                for (int i = 0; i < Data.DATA_SIZE; i++) {
                    if (bis.read(longBytes) == -1) {
                        throw new IOException("Unexpected end of file");
                    }
                    longBuffer.clear();
                    longBuffer.put(longBytes);
                    data[i] = longBuffer.getLong(0);
                }

                if (bis.read(intBytes) == -1) {
                    throw new IOException("Unexpected end of file");
                }
                intBuffer.clear();
                intBuffer.put(intBytes);
                int score = intBuffer.getInt(0);

                if (bis.read(intBytes) == -1) {
                    throw new IOException("Unexpected end of file");
                }
                intBuffer.clear();
                intBuffer.put(intBytes);
                int editionSize = intBuffer.getInt(0);

                if (editionSize == 0) {
                    dataList.add(new Data(seed, score, data, null));
                    continue;
                }

                long[] editionData = new long[editionSize * 3];

                for (int i = 0; i < editionSize; i += 3) {
                    if (bis.read(longBytes) == -1) {
                        throw new IOException("Unexpected end of file");
                    }
                    longBuffer.clear();
                    longBuffer.put(longBytes);
                    editionData[i * 3] = longBuffer.getLong(0);

                    if (bis.read(longBytes) == -1) {
                        throw new IOException("Unexpected end of file");
                    }
                    longBuffer.clear();
                    longBuffer.put(longBytes);
                    editionData[i * 3 + 1] = longBuffer.getLong(0);

                    if (bis.read(longBytes) == -1) {
                        throw new IOException("Unexpected end of file");
                    }
                    longBuffer.clear();
                    longBuffer.put(longBytes);
                    editionData[i * 3 + 2] = longBuffer.getLong(0);
                }

                dataList.add(new Data(seed, score, data, editionData));

            }
        } catch (IOException e) {
            Logger.getLogger(PreProcessedSeeds.class.getName())
                    .log(Level.SEVERE, null, e);
        }

        return dataList;
    }
}
