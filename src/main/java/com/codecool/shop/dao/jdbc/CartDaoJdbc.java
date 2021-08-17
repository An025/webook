package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.BillingInfoMem;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class CartDaoJdbc implements CartDao {
    private static CartDaoJdbc instance = null;
    private static DataSource dataSource;


    public static CartDaoJdbc getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new CartDaoJdbc();
            CartDaoJdbc.dataSource = dataSource;
        }
        return instance;
    }
    @Override
    public void add(Product product) {
        int productId = product.getId();
        int amountOfProductInCart = amountOfProductInCart(product);
        System.out.println(amountOfProductInCart);
        if (amountOfProductInCart > 0){
            System.out.println("bigger");
            increaseAmountOfProductInCartByOne(product, amountOfProductInCart);
        }else {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "INSERT INTO productamount (productid, amount) VALUES (?, ?)";
                PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                st.setInt(1, productId);
                st.setInt(2, 1);
                st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();
                rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explaination
                int insertedID = rs.getInt(1);
                System.out.println(insertedID);
            } catch (SQLException throwables) {
                throw new RuntimeException("Error while adding new product.", throwables);
            }
        }

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void setBillingInfo(BillingInfoMem billingInfoMem) {

    }

    @Override
    public ArrayList<Product> getAll() {
        return null;
    }

    public int amountOfProductInCart(Product product){
        int productId = product.getId();
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT amount FROM productamount WHERE productid = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, productId);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1); // if it returns false, it means its not in the cart yet
        } catch (SQLException e) {
            throw new RuntimeException("Error while checking cart");
        }
    }



    public void increaseAmountOfProductInCartByOne(Product product, int amountOfProductInCart) {
        int productId = product.getId();
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE productamount SET amount = ? WHERE productid = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, amountOfProductInCart + 1);
            st.setInt(2, productId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }
