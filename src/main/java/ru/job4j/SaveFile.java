package ru.job4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }
}
