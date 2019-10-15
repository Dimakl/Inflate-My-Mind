package com.inflatemymind.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashSalt {

    private static String SALT;

    static {
        Scanner scanner = null;
        String pathToSalt = "src/main/resources/salt";
        try {
            scanner = new Scanner(new File(pathToSalt));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SALT = scanner.nextLine();
    }

    public static String getSALT() {
        return SALT;
    }
}
