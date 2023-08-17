package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Model implements iModel {
    public Model() {
        File dir = new File("files");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    /**
     * добавить строку с данными в файл "family.txt", если такой файл не существует - создать новый.
     *
     * @param family   название файла
     * @param userData - строка с данными пользователя
     * @return
     */
    @Override
    public void saveUser(String family, String userData) {
        String fileName = "files/" + family + ".txt";
        try {
            Files.write(Paths.get(fileName), ("\n" + userData).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            createNewFile(fileName, userData);
        }


    }


    /**
     * создать новый файл и записать в него данные
     *
     * @param fileName - название файла
     * @param data     - строка с данными
     */
    private void createNewFile(String fileName, String data) {
        try {
            File file = new File(fileName);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(data);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("не удалось создать файл");
        }
    }
}
