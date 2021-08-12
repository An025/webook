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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        ProductCategory programming = new ProductCategory("Programming", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(programming);
        ProductCategory romantic  = new ProductCategory("Romantic", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(romantic);
        ProductCategory family = new ProductCategory("Family", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(family);

        //setting up products and printing it
        productDataStore.add(new Product("Cracking the Coding Interview", 35, "USD", "I' am not a recruiter. I am a software engineer. And as such, I know what it's like to be asked to whip up brilliant algorithms on the spot and then write flawless code on a whiteboard. I've been through this as a candidate and as an interviewer.", programming, amazon));
        productDataStore.add(new Product("Clean Code", 32, "USD", "Even bad code can function. But if code isn't clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code. But it doesn't have to be that way.", programming, lenovo));
        productDataStore.add(new Product("Interviews in Java", 47, "USD", "The core of EPI is a collection of over 250 problems with detailed solutions. The problems are representative of interview questions asked at leading software companies. The problems are illustrated with 200 figures, 300 tested programs, and 150 additional variants.", programming, amazon));
        productDataStore.add(new Product("Family1", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", family, amazon));
        productDataStore.add(new Product("Family2", 49, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", family, lenovo));
        productDataStore.add(new Product("Family3", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", family, lenovo));
        productDataStore.add(new Product("Family4", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", family, amazon));
        productDataStore.add(new Product("Romantic1", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", romantic, lenovo));
        productDataStore.add(new Product("Romantic2", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", romantic, amazon));
        productDataStore.add(new Product("Romantic3", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", romantic, lenovo));
    }
}
