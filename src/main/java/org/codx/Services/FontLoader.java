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
        g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = g.getAvailableFontFamilyNames();
        boolean isExist = true;
        for (String font : fonts) {
            System.out.println(font);

            if (font.equals(fontName)) {
                System.out.println("[INFO]:" + fontName + " Found!");
                isExist = true;
                break;
            } else {
                isExist = false;
            }
        }

        if (!isExist) {
            System.out.println("[FAILED]: " + fontName + " font is not "
                    + "available in your system. We temporary to installing the " + fontName +
                    "in your system "
                    + "please wait...");
            getFont(fontName, path);
        }
    }


    private void getFont(String fontName, String fontPath) {
        System.out.println("[INFO]:Installing font...");

        InputStream is = getClass().getClassLoader().getResourceAsStream(fontPath);
//        System.out.println(FontLoader.class.getProtectionDomain().getCodeSource().getLocation());
        GraphicsEnvironment ge
                = GraphicsEnvironment.getLocalGraphicsEnvironment();
        if (is != null) {
            Font eFont = Font.loadFont(is,12);
//            javafx.scene.text.Font
//            System.out.println("[INFO]:File is exist on application itself");
//            try {
//
//
//                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, is));
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("[INFO]:Checking font again...");
//            load(fontName, fontPath);
        } else {
            System.out.println("[INFO]:Sorry the file is not exist");
        }
    }

}
