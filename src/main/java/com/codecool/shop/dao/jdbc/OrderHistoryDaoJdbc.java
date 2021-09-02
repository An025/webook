package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.databasemanager.DatabaseConnection;
import com.codecool.shop.dao.OrderHistoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDaoJdbc implements OrderHistoryDao {
    private static OrderHistoryDaoJdbc instance = null;
    private static DataSource dataSource;
    private ArrayList<Order> orders = new ArrayList<>();
    ArrayList<Product> productsInOrder = new ArrayList<>();
    private static int sampleUserId = 0;

    public static OrderHistoryDaoJdbc getInstance( int id) {
        if (instance == null) {
            instance = new OrderHistoryDaoJdbc();
            OrderHistoryDaoJdbc.dataSource = DatabaseConnection.connect();
            sampleUserId = id;
        }
        return instance;
    }


    @Override
    public ArrayList<Order> getAllOrders() {
        orders.clear();
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT ordertime, orderstatus, id, userid  FROM orderdetails\n" +
                    "WHERE userid = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, sampleUserId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                productsInOrder = getAllProductsInOrder(rs.getInt(3));
                Order order = new Order(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        productsInOrder
                );
                System.out.println(order);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("Error");
        }
    }

    public ArrayList<Product> getAllProductsInOrder(int orderId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT product.id, product.name,product.defaultprice, product.defaultcurrency, productamount.amount FROM product\n" +
                    "INNER JOIN productamount on product.id = productamount.productid\n" +
                    "WHERE orderid = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getInt(5));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: ");
        }
    }
}
