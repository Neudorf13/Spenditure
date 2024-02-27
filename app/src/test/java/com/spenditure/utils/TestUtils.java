package com.spenditure.utils;

//import com.google.common.io.Files;

import com.spenditure.application.MainActivity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestUtils {
    private static final File DB = new File("src/main/java/com/spenditure/database/assets/SC.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(DB.toPath(), target.toPath());
        MainActivity.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}
