package com.owsb.poms.system.functions;

import java.io.*;

public class IdGenerator {
    
    // Use synchronized to prevent multiple threads generate ID at the same time
    public static synchronized int getTotal(String file) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                lines++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return lines;
    }
    
    public static synchronized int getTotalByFolder(String folderPath) {
        File folder = new File(folderPath);
        int fileCount = 0;

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileCount++;
                    }
                }
            }
        }

        return fileCount;
    }

    // Generate ID using prefix
    public static synchronized String generateID(String prefix, int length, int startNum) {
        String format = "%s%0" + length + "d";
        return String.format(format, prefix, startNum + 1);
    }

}
