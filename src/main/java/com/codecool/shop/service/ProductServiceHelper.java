package com.codecool.shop.service;

import com.codecool.shop.config.Init;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.dao.jdbc.CartDaoJdbc;
import com.codecool.shop.dao.jdbc.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.jdbc.ProductDaoJdbc;
import com.codecool.shop.dao.jdbc.SupplierDaoJdbc;
import com.codecool.shop.databasemanager.BookDatabaseManager;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ProductServiceHelper {

    public static ProductService getDataForProduct() {
//<<<<<<< HEAD
//        //if(jdbc)
//        BookDatabaseManager bookDatabaseManager = new BookDatabaseManager();
//        DataSource datasource = bookDatabaseManager.connect();
//
//        ProductDao productDataStore = ProductDaoJdbc.getInstance(datasource);
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance(datasource);
//        CartDao cartDao = CartDaoJdbc.getInstance(datasource);
////        SupplierDao supplierDao = SupplierDaoJdbc.getInstance(datasource);
//
//        //else
////        ProductDao productDataStore = ProductDaoMem.getInstance();
////        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
////        CartDao cartDao = CartDaoMem.getInstance();
//        SupplierDao supplierDao = SupplierDaoMem.getInstance();
//=======
        ProductDao productDataStore = null;
        ProductCategoryDao productCategoryDataStore = null;
        CartDao cartDao = null;
        SupplierDao supplierDao = null;
        if(Init.getInitType().equals("database")){
            System.out.println("database");
            BookDatabaseManager bookDatabaseManager = new BookDatabaseManager();
            DataSource datasource = bookDatabaseManager.connect();
            productDataStore = ProductDaoJdbc.getInstance(datasource);
            productCategoryDataStore = ProductCategoryDaoJdbc.getInstance(datasource);
            cartDao = CartDaoJdbc.getInstance(datasource);
            //            supplierDao = SupplierDaoJdbc.getInstance(datasource);
            supplierDao = SupplierDaoMem.getInstance();
        }
        else if(Init.getInitType().equals("memory")){
            System.out.println("memory");
            productDataStore = ProductDaoMem.getInstance();
            productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            supplierDao = SupplierDaoMem.getInstance();
            cartDao = CartDaoMem.getInstance();
        }
//>>>>>>> development

    return new ProductService(productDataStore, productCategoryDataStore, supplierDao, cartDao);
}
}
