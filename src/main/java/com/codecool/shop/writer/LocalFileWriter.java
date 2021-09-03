package com.codecool.shop.writer;

import com.codecool.shop.controller.BillingInfo;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Calendar;

public  class LocalFileWriter {
    private static Logger logger = LoggerFactory.getLogger(LocalFileWriter.class);

    public static void serialize() throws IOException {
        CartDaoMem order = CartDaoMem.getInstance();
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        String filename = saveFile(order);
        Writer writer = null;
        try {
            writer = new FileWriter(filename);
            logger.info("Write this order  " + order.getIdOfActiveOrder() + " to file:" + filename);

        } catch (IOException e) {
            logger.error("Write order error: " + e);
            e.printStackTrace();
        }
        gson.toJson(order, writer);
        writer.flush(); //flush data to file   <---
        writer.close();
    }

    private static String saveFile(CartDaoMem order){
        LocalDateTime dateNow = LocalDateTime.now();
        String date = dateNow.getYear() + "." + dateNow.getMonth() + "." + dateNow.getDayOfMonth() + "." + dateNow.getHour() + "." + dateNow.getMinute();
        String filename = "src/main/resources/Order/" + date  + "_" + order.getIdOfActiveOrder() + ".json";
        createFile(filename);
        return filename;
    }
    private static void createFile(String filename) {
        //initialize File object and passing path as argument
        File file = new File(filename);
        boolean result;
        try {
            result = file.createNewFile();  //creates a new file
            // test if successfully created a new file
            if (result) {
                //returns the path string
                System.out.println("file created "+ file.getCanonicalPath());
                logger.info("file created "+ file.getCanonicalPath());
            }
            else {
                System.out.println("File already exist at location: " + file.getCanonicalPath());
                logger.info("File already exist at location: " + file.getCanonicalPath());
            }
        } catch (IOException e) {
            //prints exception if any
            logger.error("Error while create file" + e);
            e.printStackTrace();
        }
    }
}
