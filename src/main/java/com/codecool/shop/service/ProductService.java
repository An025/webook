package com.codecool.shop.service;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.BillingInfoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductService{
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private CartDao cartDao;

    public ProductService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao, CartDao cartDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
        this.cartDao = cartDao;
    }

    public ProductCategory getProductCategory(int categoryId){
        return productCategoryDao.find(categoryId);
    }

    public List<Supplier> getAllSuppliers(){
        return supplierDao.getAll();
    }

    public List<ProductCategory> getAllProductCategories(){
        return productCategoryDao.getAll();
    }
    public List<Product> getAllProduct(){
        return productDao.getAll();
    }

    public Product getProduct(int id){
        return productDao.find(id);
    }

    public List<Product> getProductsForCategory(int categoryId){
        var category = productCategoryDao.find(categoryId);
        return productDao.getBy(category);


    }
    public ArrayList<Product> getAllProductFromCart(){
        return cartDao.getAll();
    }

    public void removeProductFromCart(Product product){
        cartDao.removeProductFromCart(product);
    }

    public void addProductToCart(Product product){
        cartDao.add(product);
    }

    public void decreaseAmountOfProductInCart(Product product){
        cartDao.decreaseAmountOfProductInCartByOne(product);
    }

    public void setAddress(BillingInfoMem billingInfoMem) {
        cartDao.setBillingInfo(billingInfoMem);
    }


}
