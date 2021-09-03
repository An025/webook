package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.databasemanager.DatabaseConnection;
import com.codecool.shop.dao.SupplierDao;
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

public class SupplierDaoJdbc implements SupplierDao {
    private static DataSource dataSource;
    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoJdbc instance;
    private static Logger logger = LoggerFactory.getLogger(SupplierDaoJdbc.class);

    public static SupplierDaoJdbc getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
            logger.info("SupplierDao CONNECT");
            SupplierDaoJdbc.dataSource = DatabaseConnection.connect();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        data.clear();
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, description FROM supplier;";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Supplier supplier = new Supplier(rs.getString(1), rs.getString(2));
                data.add(supplier);
            }
            return data;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: " + e);
        }
    }
}
