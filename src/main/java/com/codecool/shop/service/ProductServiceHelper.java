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

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ProductServiceHelper {

    public static ProductService getDataForProduct() {
        ProductDao productDataStore = null;
        ProductCategoryDao productCategoryDataStore = null;
        CartDao cartDao = null;
        SupplierDao supplierDao = null;
        if(Init.getInitType().equals("database")){
            System.out.println("database");
            productDataStore = ProductDaoJdbc.getInstance();
            productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
            cartDao = CartDaoJdbc.getInstance();
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

    return new ProductService(productDataStore, productCategoryDataStore, supplierDao, cartDao);
}
}
