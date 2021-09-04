package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.databasemanager.DatabaseConnection;
import com.codecool.shop.controller.Registration;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    private static Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);

    private List<Product> data = new ArrayList<>();
    private static ProductDaoJdbc instance = null;
    private static DataSource dataSource;

    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoJdbc();
            logger.info("productDaoJdbc CONNECCT");
            ProductDaoJdbc.dataSource = DatabaseConnection.connect();
        }
        return instance;
    }
    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT product.name, product.defaultprice,product.defaultcurrency, product.description,c.id, c.name, s.name, s.description, s.id FROM product\n" +
                    "INNER JOIN category c on product.categoryid = c.id\n" +
                    "INNER JOIN supplier s on product.supplierid = s.id\n" +
                    "where product.id = ?;";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ProductCategory productCategory = new ProductCategory(rs.getString(6));
            productCategory.setId(rs.getInt(5));
            Supplier supplier = new Supplier(rs.getString(7), rs.getString(8));
            supplier.setId(rs.getInt(9));
            Product product = new Product(rs.getString(1),
                    rs.getFloat(2),
                    rs.getString(3),
                    rs.getString(4),
                    productCategory,
                    supplier);
            product.setId(id);
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        data.clear();
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT product.name, product.defaultprice,product.defaultcurrency, product.picturepath, product.description,c.id, c.name, s.name, s.description, s.id, product.id FROM product\n" +
                    "INNER JOIN category c on product.categoryid = c.id\n" +
                    "INNER JOIN supplier s on product.supplierid = s.id\n";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                ProductCategory productCategory = new ProductCategory(rs.getString(7));
                Supplier supplier = new Supplier(rs.getString(8), rs.getString(9));
                supplier.setId(rs.getInt(10));

                Product product = new Product(rs.getString(1),
                        rs.getFloat(2),
                        rs.getString(3),
                        rs.getString(5),
                        productCategory,
                        supplier);
                product.setId(rs.getInt(11));
                data.add(product);
            }
            System.out.println("products" + data);
            return data;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading products", e);
        }
//        return null;
    }




    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        int id= productCategory.getId();
        data.clear();
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT product.name, product.defaultprice,product.defaultcurrency, product.description,c.id, c.name, s.name, s.description, s.id FROM product\n" +
                    "INNER JOIN category c on product.categoryid = c.id\n" +
                    "INNER JOIN supplier s on product.supplierid = s.id\n" +
                    "where product.id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) { // while result set pointer is positioned before or on last row read authors

                Supplier supplier = new Supplier(rs.getString(7), rs.getString(8));
                supplier.setId(rs.getInt(9));
                Product product = new Product(rs.getString(1),
                        rs.getFloat(2),
                        rs.getString(3),
                        rs.getString(4),
                        productCategory,
                        supplier);
                product.setId(id);
                data.add(product);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: " + id, e);
        }
    }
}
