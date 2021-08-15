package com.codecool.shop.databasemanager;

import com.codecool.shop.config.Initializer;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class BookDatabaseManager {
    private static Logger logger = LoggerFactory.getLogger(Initializer.class);

    public DataSource connect() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(System.getenv("DATABASE"));
        dataSource.setUser(System.getenv("USER_NAME"));
        dataSource.setPassword(System.getenv("PASSWORD"));
        logger.info("Trying to connect...");
        System.out.println("Trying to connect...");
        try{
            dataSource.getConnection().close();
        }catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Exception");
            logger.error("Connect Exception");
        }
        System.out.println("Connection OK");
        logger.info("Connect OK");
        return dataSource;
    }
}
