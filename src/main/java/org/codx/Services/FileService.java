package org.codx.Services;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class FileService {


    private static String generatePath() throws URISyntaxException {
        File file = new File(FileService.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        System.out.println("Project Path"+file.toString());
        String result = getParentFile(1, file).replace('\\', '/');
        System.out.println("Project parent"+result);
        return result;
    }

    //save in download as a defualt path
    public static String defaultPath(String qrName) throws URISyntaxException {
        String downloadPath = "";

        File folder = new File( getProjectPath()+ "/AvantiQrCode");
        System.out.print(getProjectPath());

        if (!folder.exists()) {
            folder.mkdir();
        }
        downloadPath = folder+"/"+qrName;


        return downloadPath;
    }


    private static String getParentFile(int timesToBack, File file) {
        String result = "";
        for (int i = 1; i <= timesToBack; i++) {
            file = file.getParentFile();
            result = file.getParent();
        }

        return result;
    }

    public static String getProjectPath() {
        String location = "";
        try {
            location = generatePath() != null ? generatePath() : "Project Folder identifying is failed";
        } catch (URISyntaxException syntaxException) {
            syntaxException.printStackTrace();
        }

        return location;
    }
}
