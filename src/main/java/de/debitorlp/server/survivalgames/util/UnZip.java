package de.debitorlp.server.survivalgames.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.debitorlp.server.survivalgames.Main;

public class UnZip {

    private String outputFolder;
    private String inputZipFile;

    public UnZip(String inputZipFile, String outputFolder) {
        this.inputZipFile = inputZipFile;
        this.outputFolder = outputFolder;
    }

    public void unZipIt() {
        byte[] buffer = new byte[1024];

        try {
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }

            ZipInputStream zin = new ZipInputStream(new FileInputStream(inputZipFile));
            ZipEntry ze = zin.getNextEntry();

            while (ze != null) {
                if (ze.isDirectory()) {
                    ze = zin.getNextEntry();
                    continue;
                }

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                if (Main.debugMode) {
                    System.out.println("File unzip: " + newFile.getAbsolutePath());
                }

                new File(newFile.getParent()).mkdirs();

                FileOutputStream fout = new FileOutputStream(newFile);

                int length;
                while ((length = zin.read(buffer)) > 0) {
                    fout.write(buffer, 0, length);
                }

                fout.close();
                ze = zin.getNextEntry();
            }

            zin.closeEntry();
            zin.close();

            if (Main.debugMode) {
                System.out.println("Done!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
