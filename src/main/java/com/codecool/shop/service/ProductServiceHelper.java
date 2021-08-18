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
import com.codecool.shop.dao.jdbc.SupplierDaoJdbc;
import com.codecool.shop.databasemanager.BookDatabaseManager;

import javax.sql.DataSource;

public class ProductServiceHelper {

    public static ProductService getDataForProduct() {
        //if(jdbc)
        BookDatabaseManager bookDatabaseManager = new BookDatabaseManager();
        DataSource datasource = bookDatabaseManager.connect();

        ProductDao productDataStore = ProductDaoJdbc.getInstance(datasource);
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance(datasource);
        CartDao cartDao = CartDaoJdbc.getInstance(datasource);
//        SupplierDao supplierDao = SupplierDaoJdbc.getInstance(datasource);

        //else
//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        CartDao cartDao = CartDaoMem.getInstance();
        SupplierDao supplierDao = SupplierDaoMem.getInstance();

    return new ProductService(productDataStore, productCategoryDataStore, supplierDao, cartDao);
}
}
