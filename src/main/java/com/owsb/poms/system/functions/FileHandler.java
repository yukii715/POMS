package com.owsb.poms.system.functions;

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class FileHandler {
    
        // Generic write method
    public static <T> void saveToFile(String filePath, List<T> list, Function<T, String> mapper) {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); 
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (T item : list) {
                bw.write(mapper.apply(item));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generic read method
    public static <T> List<T> readFromFile(String filePath, Function<String, T> parser) {
        List<T> list = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(parser.apply(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
