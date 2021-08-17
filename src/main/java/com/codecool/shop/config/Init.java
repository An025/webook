package com.codecool.shop.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public  class Init {
    public static String getInitType(){
        try (FileReader reader = new FileReader("src/main/resources/connection.properties");){
            Properties prop = new Properties();
            prop.load(reader);
            return prop.getProperty("data");
        } catch (IOException e) {
            e.getMessage();
        }
        return "memory";
    }
}
