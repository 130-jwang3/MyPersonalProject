package com.personal.project.util;

import java.nio.file.*;
import java.util.*;

public class ConfigProperties {

    public static String load(String key){
        Properties prop = new Properties();
        try {
            prop.load(Files.newInputStream(Path.of("C:\\Users\\johnw\\Documents\\SchoolCanvas\\backend\\src\\main\\resources\\app.config")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return prop.getProperty(key);

    }
}
