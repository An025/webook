package com.codecool.shop.config.databasemanager;

import com.codecool.shop.config.Init;
import com.codecool.shop.config.Initializer;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DatabaseConnection extends Init {
    private static Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    public static DataSource connect() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(DATABASE);
        dataSource.setUser(USER_NAME);
        dataSource.setPassword(PASSWORD);
        logger.info("Trying to connect...");
        try{
            dataSource.getConnection().close();
            logger.info("Connect OK");
        }catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Exception");
            logger.error("Connect Exception");
        }
        return dataSource;
    }
}
