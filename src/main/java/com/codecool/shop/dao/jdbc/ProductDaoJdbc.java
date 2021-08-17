package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private List<Product> data = new ArrayList<>();
    private static ProductDaoJdbc instance = null;
    private static DataSource dataSource;

    public static ProductDaoJdbc getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new ProductDaoJdbc();
            ProductDaoJdbc.dataSource = dataSource;
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
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        int id= productCategory.getId();
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
            System.out.println(data);
            return data;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: " + id, e);
        }
    }
}
