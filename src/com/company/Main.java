package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(String filePath, GameProgress save) {
        File saveFile = new File(filePath);
        try (FileOutputStream outPutStream = new FileOutputStream(saveFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outPutStream)) {
            objectOutputStream.writeObject(save);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static void zipFiles(String fileZipPath, List<String> savesPath) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(fileZipPath))) {
            for (String s : savesPath) {
                FileInputStream fileInputStream = new FileInputStream(s);
                ZipEntry zipEntry = new ZipEntry(s);
                zipOutputStream.putNextEntry(zipEntry);
                byte[] buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File savegames = new File("C://Games//savegames");

        GameProgress save1 = new GameProgress(10, 1, 1, 1.0);
        GameProgress save2 = new GameProgress(50, 20, 10, 1.5);
        GameProgress save3 = new GameProgress(75, 5, 15, 1.7);

        File saveFile1 = new File(savegames, "save1.dat");
        File saveFile2 = new File(savegames, "save2.dat");
        File saveFile3 = new File(savegames, "save3.dat");

        saveGame(saveFile1.getPath(), save1);
        saveGame(saveFile2.getPath(), save2);
        saveGame(saveFile3.getPath(), save3);

        List<String> savesPath = new ArrayList<>();
        savesPath.add(saveFile1.getPath());
        savesPath.add(saveFile2.getPath());
        savesPath.add(saveFile3.getPath());

        zipFiles("C://Games//savegames//save.zip", savesPath);

        saveFile1.delete();
        saveFile2.delete();
        saveFile3.delete();
    }
}