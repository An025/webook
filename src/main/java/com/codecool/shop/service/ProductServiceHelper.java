package com.codecool.shop.service;

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
import com.codecool.shop.databasemanager.BookDatabaseManager;

import javax.sql.DataSource;

public class ProductServiceHelper {

    public static ProductService getDataForProduct() {
        //if(jdbc)
        BookDatabaseManager bookDatabaseManager = new BookDatabaseManager();
        DataSource datasource = bookDatabaseManager.connect();
//        ProductDao productDataStore = ProductDaoJdbc.getInstance(datasource);
        CartDao cartDao = CartDaoJdbc.getInstance(datasource);
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        //else
        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance(datasource);
        SupplierDao supplierDao = SupplierDaoMem.getInstance();
//    CartDao cartDao = CartDaoMem.getInstance();
    return new ProductService(productDataStore, productCategoryDataStore, supplierDao, cartDao);
}
}
