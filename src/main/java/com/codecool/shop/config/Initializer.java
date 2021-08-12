package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {
    private static Logger logger = LoggerFactory.getLogger(Initializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Start Initializer");
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        ProductCategory gastronomy = new ProductCategory("Gastronomy", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(gastronomy);
        ProductCategory romantic  = new ProductCategory("Romantic", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(romantic);
        ProductCategory family = new ProductCategory("Family", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(family);

        //setting up products and printing it
        productDataStore.add(new Product("Gastronomy1", 49, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", gastronomy, amazon));
        productDataStore.add(new Product("Gastronomy2", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", gastronomy, lenovo));
        productDataStore.add(new Product("Gastronomy3", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", gastronomy, amazon));
        productDataStore.add(new Product("Family1", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", family, amazon));
        productDataStore.add(new Product("Family2", 49, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", family, lenovo));
        productDataStore.add(new Product("Family3", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", family, lenovo));
        productDataStore.add(new Product("Family4", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", family, amazon));
        productDataStore.add(new Product("Romantic1", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", romantic, lenovo));
        productDataStore.add(new Product("Romantic2", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", romantic, amazon));
        productDataStore.add(new Product("Romantic3", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", romantic, lenovo));
    }
}
