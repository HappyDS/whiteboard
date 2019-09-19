package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yangzhe Xie
 * @date 19/9/19
 */
public class FileUtil {
    public static void write(String info, String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(info);

        bufferedWriter.close();
        fileWriter.close();
    }

    public static List<String> read(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<String> result = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
