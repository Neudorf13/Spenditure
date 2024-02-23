package com.spenditure.utils;

//import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

public class TestUtils {
    private static final File DB = new File("src/main/java/com/spenditure/database/assets/SC.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        //Files.copy(DB, target);
        //Main.setDBPathName()
        return target;
    }
}
