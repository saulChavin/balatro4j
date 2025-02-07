package com.balatro;

import com.balatro.api.*;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SeedFinderImpl implements SeedFinder {

    private final int parallelism;
    private final int seedsPerThread;
    private final AtomicBoolean lock = new AtomicBoolean(false);
    private Filter filter;
    private final List<Run> foundSeeds = new ArrayList<>();
    private Consumer<BalatroBuilder> configuration;

    public SeedFinderImpl() {
        this(Runtime.getRuntime().availableProcessors(), 1_000_000);
    }

    public SeedFinderImpl(int seedsPerThread) {
        this(Runtime.getRuntime().availableProcessors(), seedsPerThread);
    }

    public SeedFinderImpl(int parallelism, int seedsPerThread) {
        this.parallelism = parallelism;
        if (parallelism > Runtime.getRuntime().availableProcessors()) {
            throw new IllegalArgumentException("Parallelism cannot be greater than available processors");
        }

        this.seedsPerThread = seedsPerThread;
    }

    @Override
    public SeedFinder configuration(Consumer<BalatroBuilder> configuration) {
        this.configuration = configuration;
        return this;
    }

    @Override
    public SeedFinder filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public List<Run> find() {
        search();
        return foundSeeds;
    }

    private void search() {
        if (filter == null) {
            throw new IllegalStateException("No filters were added");
        }

        if (lock.get()) {
            return;
        }

        lock.set(true);
        count.reset();

        List<ForkJoinTask<?>> tasks = new ArrayList<>(parallelism);

        for (int i = 0; i < parallelism; i++) {
            var task = ForkJoinPool.commonPool().submit(this::generate);
            tasks.add(task);
        }

        int time = 0;
        long c;
        var format = new DecimalFormat("#,###");

        var init = LocalDateTime.now();
        System.out.println("Searching " + format.format((long) parallelism * seedsPerThread) + " seeds with " + parallelism + " tasks");

        while (!tasks.stream().allMatch(ForkJoinTask::isDone)) {
            try {
                Thread.sleep(1000);

                time++;
                c = SeedFinderImpl.count.longValue();
                if (time % 2 == 0) {
                    System.out.println(format.format(c / time) + " ops/s seeds analyzed: " + format.format(c) + " " + getMemory());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("FINISHED: " + (init.until(LocalDateTime.now(), ChronoUnit.SECONDS)) + " seconds | "
                           + format.format(count.longValue() / time) + " Seeds/sec, Seeds analyzed: " + format.format(count.longValue()));
        System.out.println(getMemory());
        System.out.println("--------------------------------------------------------------------------------------------");

    }

    public static @NotNull String getMemory() {
        Runtime runtime = Runtime.getRuntime();

        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        return "Used memory: " + usedMemory / 1024 / 1024 + " MB";
    }

    static LongAdder count = new LongAdder();

    private boolean checkFilters(Run run) {
        return filter.filter(run);
    }

    private void generate() {
        for (int i = 0; i < seedsPerThread; i++) {
            try {
                count.increment();
                var seed = BalatroImpl.generateRandomSeed();

                var builder = Balatro.builder(seed, 0);

                if (configuration != null) {
                    configuration.accept(builder);
                }

                var run = builder
                        .build();

                if (checkFilters(run)) {
                    foundSeeds.add(run);
                }
            } catch (Exception ex) {
                Logger.getLogger(SeedFinderImpl.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
    }

}
