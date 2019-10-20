package com.inflatemymind.utility;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashSalt {

    private static String salt;

    static {
        Scanner scanner = null;
        String pathToSalt = "src/main/resources/salt";
        try {
            scanner = new Scanner(new File(pathToSalt));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        salt = scanner.nextLine();
    }

    public static String getSalt() {
        return salt;
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, HashSalt.getSalt());
    }
}
