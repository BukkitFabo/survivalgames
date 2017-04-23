package de.debitorlp.server.survivalgames.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import de.debitorlp.server.survivalgames.Main;

public class Zip {

    private List<String> fileList;
    private String outputZipFile;
    private String sourceFolder;

    public Zip(String sourceFolder, String outputZipFile) {
        this.sourceFolder = sourceFolder;
        this.outputZipFile = outputZipFile;
        fileList = new ArrayList<String>();
    }

    public void zipIt() {
        byte[] buffer = new byte[1024];

        try {
            FileOutputStream out = new FileOutputStream(outputZipFile);
            ZipOutputStream zout = new ZipOutputStream(out);

            if (Main.debugMode) {
                System.out.println("Output to Zip:" + outputZipFile);
            }

            for (String file : this.fileList) {
                ZipEntry ze = new ZipEntry(file);
                zout.putNextEntry(ze);

                FileInputStream in = new FileInputStream(sourceFolder + File.separator + file);

                int length;
                while ((length = in.read(buffer)) > 0) {
                    zout.write(buffer, 0, length);
                }

                in.close();
                if (Main.debugMode) {
                    System.out.println("File added: " + file);
                }
            }

            zout.closeEntry();
            zout.close();

            if (Main.debugMode) {
                System.out.println("Done!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateZipEntry(String file) {
        return file.substring(sourceFolder.length() + 1, file.length());
    }

    public void generateFileList(File path) {

        if (path.isFile()) {
            fileList.add(generateZipEntry(path.getPath().toString()));
            if (Main.debugMode) {
                System.out.println("added to fileList: " + path.getPath().toString());
            }
        }

        if (path.isDirectory()) {
            String[] subFile = path.list();
            for (String filename : subFile) {
                generateFileList(new File(path, filename));
            }
        }

    }

}
