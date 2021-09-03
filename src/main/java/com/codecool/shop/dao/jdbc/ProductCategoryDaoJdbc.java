package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.databasemanager.DatabaseConnection;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {
    private static Logger logger = LoggerFactory.getLogger(ProductCategoryDaoJdbc.class);
    private static ProductCategoryDaoJdbc instance = null;
    private static DataSource dataSource;
    private List<ProductCategory> data = new ArrayList<>();

    public static ProductCategoryDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJdbc();
            logger.info("ProductCategoryDao CONNECT");
            ProductCategoryDaoJdbc.dataSource = DatabaseConnection.connect();
        }
        return instance;
    }
    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT product.name, product.defaultprice,product.defaultcurrency, product.description,c.id, c.name, s.name, s.description, s.id FROM product\n" +
            "INNER JOIN category c on product.categoryid = c.id\n" +
            "INNER JOIN supplier s on product.supplierid = s.id\n" +
            "where c.id = ?";
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
            System.out.println(productCategory);
            return productCategory;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name FROM category;";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ProductCategory productCategory = new ProductCategory(rs.getString(1));
                data.add(productCategory);
            }
            return data;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: " + e);
        }
    }
}
