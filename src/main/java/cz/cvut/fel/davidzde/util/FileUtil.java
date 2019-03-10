package cz.cvut.fel.davidzde.util;

import java.io.*;

public class FileUtil {

    public static String readText(String file) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        while ((s = br.readLine()) != null) {
            fileContent.append(s).append("\n");
        }

        return fileContent.toString();
    }

}
