package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.databasemanager.DatabaseConnection;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class CustomerDaoJdbc implements CustomerDao {
    private static CustomerDaoJdbc instance = null;
    private static DataSource dataSource;

    public static CustomerDaoJdbc getInstance() {
        if (instance == null) {
            instance = new CustomerDaoJdbc();
            CustomerDaoJdbc.dataSource = DatabaseConnection.connect();
        }
        return instance;
    }

    @Override
    public void add(Customer customer) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO customer (id, name, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, lastId() +1 );
            st.setString(2, customer.getName());
            st.setString(3, customer.getEmail());
            st.setString(4, customer.getPassword());
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new product.", throwables);
        }
    }

    private Integer lastId(){
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id FROM customer ORDER BY id DESC LIMIT 1";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            Integer id = 0;
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
               id=  rs.getInt(1);
            }
            return id;
        } catch (SQLException throwables) {
            throw new RuntimeException("Error while get last id.", throwables);
        }
    }
    @Override
    public Customer find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Customer> getAll() {
        return null;
    }
}
