package com.personal.project.util;

import java.nio.file.*;
import java.util.*;

public class ConfigProperties {

    public static String load(String key){
        Properties prop = new Properties();
       // prop.load(Files.newInputStream(Path.of("resources/app.config")));
        prop.getProperty(key);
        return "";
    }
}
