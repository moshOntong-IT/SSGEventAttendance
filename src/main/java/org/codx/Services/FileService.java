package org.codx.Services;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class FileService {


    private static  String generatePath() throws URISyntaxException {
        File file = new File(FileService.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        String result = getParentFile(3, file).replace('\\', '/');
        return result;
    }

    private static  String getParentFile(int timesToBack, File file){
        String result = "";
        for (int i = 1 ; i <= timesToBack; i++){
            file = file.getParentFile();
            result = file.getParent();
        }

        return  result;
    }
   public static  String getProjectPath(){
        String location = "";
        try{
            location = generatePath() != null ? generatePath() : "Project Folder identifying is failed";
        }catch(URISyntaxException syntaxException){
            syntaxException.printStackTrace();
        }

        return location;
   }
}
