package com.codecool.shop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public  class Init {
    private static Logger logger = LoggerFactory.getLogger(Initializer.class);
    protected static String USER_NAME;
    protected static String PASSWORD;
    protected static String DATABASE;

    public static String getInitType() {
        try (FileReader reader = new FileReader("src/main/resources/connection.properties");) {
            Properties prop = new Properties();
            prop.load(reader);
            if (prop.getProperty("data").equals("memory")) {
                return "memory";
            } else {
                DATABASE = prop.getProperty("DATABASE");
                USER_NAME = prop.getProperty("USER_NAME");
                PASSWORD = prop.getProperty("PASSWORD");
                return "database";
            }

        } catch (IOException e) {
            e.getMessage();
        }
        return "memory";
    }
}