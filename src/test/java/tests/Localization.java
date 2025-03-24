package tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Localization {

    public static void main(String[] args) throws IOException {
        var localization = new File("/Users/alex/Desktop/en-us.lua");

        var lines = Files.readAllLines(localization.toPath());

        for (String line : lines) {
            System.out.println(line.replace("=", ": "));
        }
    }
}
