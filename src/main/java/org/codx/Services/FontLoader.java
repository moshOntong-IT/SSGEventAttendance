/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.codx.Services;

import javafx.scene.text.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.*;
import java.nio.Buffer;

/**
 * @author ACER
 */
public class FontLoader {

    private GraphicsEnvironment g;


    public void load(String fontName, String path) {
        getFont(fontName,path);
    }


    private void getFont(String fontName, String fontPath) {
        System.out.println("[INFO]:Installing font...");

//        InputStream is = getClass().getClassLoader().getResourceAsStream(fontPath);

//        if (is != null) {

//            Font eFont = Font.loadFont(getClass().getResource(fontPath).toExternalForm(),12);

//            System.out.println("[INFO]: Font installed");
//        } else {
//            System.out.println("[INFO]:Sorry the file is not exist");
//        }
    }

}
