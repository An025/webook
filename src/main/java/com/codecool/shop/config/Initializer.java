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
        Supplier libri = new Supplier("Libri", "Book and ebook");
        supplierDataStore.add(libri);

        //setting up a new product category
        ProductCategory programming = new ProductCategory("Programming", "Book", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.",1);
        productCategoryDataStore.add(programming);
        ProductCategory romantic  = new ProductCategory("Romance", "Book", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.",1);
        productCategoryDataStore.add(romantic);
        ProductCategory thriller = new ProductCategory("Thriller", "Book", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.",1);
        productCategoryDataStore.add(thriller);

        //setting up products and printing it
        productDataStore.add(new Product("Cracking the Coding Interview", 35, "USD", "I' am not a recruiter. I am a software engineer. And as such, I know what it's like to be asked to whip up brilliant algorithms on the spot and then write flawless code on a whiteboard. I've been through this as a candidate and as an interviewer.", programming, amazon));
        productDataStore.add(new Product("Clean Code", 32, "USD", "Even bad code can function. But if code isn't clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code. But it doesn't have to be that way.", programming, libri));
        productDataStore.add(new Product("Interviews in Java", 47, "USD", "The core of EPI is a collection of over 250 problems with detailed solutions. The problems are representative of interview questions asked at leading software companies. The problems are illustrated with 200 figures, 300 tested programs, and 150 additional variants.", programming, amazon));
        productDataStore.add(new Product("Billy Summers", 19, "USD", "From legendary storyteller and number-one best seller Stephen King, whose “restless imagination is a power that cannot be contained” (The New York Times Book Review), comes a thrilling new novel about a good guy in a bad job.", thriller, amazon));
        productDataStore.add(new Product("The Therapist", 15, "USD", "The multimillion-copy New York Times bestselling author B.A. Paris returns to her heartland of gripping psychological suspense in The Therapist―a powerful tale of a house that holds a shocking secret.", thriller, libri));
        productDataStore.add(new Product("The Dark", 10, "USD", "From New York Times and number one Audible best-selling duo Jeremy Robinson and R.C. Bray comes a horrifying revelation about the centuries-old Three Days of Darkness prophecy, during which the legions of Hell will be unleashed ", thriller, libri));
        productDataStore.add(new Product("Foolish Hearts", 6, "USD", "It’s been three years since Ashiya Waters walked away from Russell—and made the biggest mistake of her life. She knows she shouldn’t dwell on the past. Love isn't meant to last…and nobody taught her that better than her own family. ", romantic, libri));
        productDataStore.add(new Product("The Wildest Ride", 12, "USD", "Filled with deep emotion and intense spark, Marcella Bell brings grit, spark and brilliance to western romance! Marcella Bell is one to watch!”—Maisey Yates, New York Times bestselling author", romantic, amazon));
        productDataStore.add(new Product("New Moon", 89, "USD", "From evil vampires to a mysterious pack of wolves, new threats of danger and vengeance test Bella and Edward's romance in the second book of the irresistible Twilight saga.", romantic, libri));
    }
}
