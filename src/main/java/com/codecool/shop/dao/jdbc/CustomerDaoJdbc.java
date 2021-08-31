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
            int id = lastId() + 1;
            st.setInt(1, id);
            st.setString(2, customer.getName());
            st.setString(3, customer.getEmail());
            st.setString(4, customer.getPassword());
            st.executeUpdate();
            customer.setId(id);
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
    public Customer find(String email, String password) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name FROM customer WHERE email = ? and password= ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Customer customer = new Customer(rs.getString(2),email, password);
            customer.setId(rs.getInt(1));
        return customer;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: ");
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Customer> getAll() {
        return null;
    }
}
