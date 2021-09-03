package com.codecool.shop.service;

import com.codecool.shop.config.Init;
import com.codecool.shop.config.databasemanager.DatabaseConnection;
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

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ProductServiceHelper {
    private static String dataType = null;
    public static void getDataType() {
        if (dataType == null) {
            dataType = Init.getInitType();
        }
    }

    public static ProductService getDataForProduct() {
        getDataType();
        ProductDao productDataStore = null;
        ProductCategoryDao productCategoryDataStore = null;
        CartDao cartDao = null;
        SupplierDao supplierDao = null;
        if(dataType.equals("database")){
            productDataStore = ProductDaoJdbc.getInstance();
            productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
            cartDao = CartDaoJdbc.getInstance();
            supplierDao = SupplierDaoJdbc.getInstance();
        }
        else if(dataType.equals("memory")){
            productDataStore = ProductDaoMem.getInstance();
            productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            supplierDao = SupplierDaoMem.getInstance();
            cartDao = CartDaoMem.getInstance();
        }

    return new ProductService(productDataStore, productCategoryDataStore, supplierDao, cartDao);
}
}
