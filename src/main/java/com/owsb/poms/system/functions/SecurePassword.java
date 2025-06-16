package com.owsb.poms.system.functions;

import java.security.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecurePassword {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    public static final String SYMBOLS  = "!@#&*_+=:;?,.";
    
    private static final String ALL = UPPER + LOWER + DIGITS + SYMBOLS;
    
    // Create strong random password when create a new user or reset password
    public static String newPassword(){
        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = new ArrayList<>();

        // Ensure at least one character from each category
        passwordChars.add(UPPER.charAt(random.nextInt(UPPER.length())));
        passwordChars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordChars.add(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));

        // Fill the rest with random characters from all sets
        for (int i = 4; i < 12; i++) {
            passwordChars.add(ALL.charAt(random.nextInt(ALL.length())));
        }

        // Shuffle to avoid predictable positions
        Collections.shuffle(passwordChars, random);

        // Build the final string
        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) password.append(c);

        return password.toString();
    }
    
    // Process of Vigenère cipher
    public static char encryptChar(char plainChar, char keyChar) {
        if (!Character.isLetter(plainChar)) return plainChar;

        char base = Character.isUpperCase(plainChar) ? 'A' : 'a';
        int plainIndex = plainChar - base;
        int keyIndex = Character.toUpperCase(keyChar) - 'A';

        return (char)((plainIndex + keyIndex) % 26 + base);
    }
    
    // Encrypt hash by Vigenère cipher
    public static String encrypt(String plaintext, String key) {
        StringBuilder encrypted = new StringBuilder();
        key = key.toUpperCase();
        int keyLen = key.length();

        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            if (Character.isLetter(plainChar)) {
                encrypted.append(encryptChar(plainChar, key.charAt(j % keyLen)));
                j++;
            } else {
                encrypted.append(plainChar);  // keep non-letters unchanged
            }
        }
        return encrypted.toString();
    }
    
    // SHA-256 Hashing
    public static String sha256(String password) {
        try {
            // Get SHA-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convert input string to bytes and hash it
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found");
        }
    }
}
