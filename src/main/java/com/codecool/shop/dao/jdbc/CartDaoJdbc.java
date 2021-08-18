package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.BillingInfoMem;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class CartDaoJdbc implements CartDao {
    private static CartDaoJdbc instance = null;
    private static DataSource dataSource;
    private static int sampleUserId = 1;
    private static int ACTIVEORDER = 1;


    public static CartDaoJdbc getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new CartDaoJdbc();
            CartDaoJdbc.dataSource = dataSource;
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        // we need to check the user id and whether the user already has an active order
        if (isCurrentCustomerInDataBase()) {
            int idOfActiveOrder;
            if (!doesCustomerHaveActiveOrder()) {
                idOfActiveOrder = createNewActiveOrder();
            } else {
                idOfActiveOrder = getIdOfActiveOrder();
            }
            int productId = product.getId();
            int amountOfProductInCart = amountOfProductInCart(product);
            System.out.println(amountOfProductInCart);
            if (amountOfProductInCart > 0) {
                increaseAmountOfProductInCartByOne(product, amountOfProductInCart, idOfActiveOrder);
            } else {
                try (Connection conn = dataSource.getConnection()) {
                    String sql = "INSERT INTO productamount (orderid, productid, amount) VALUES (?, ?, ?)";
                    PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    st.setInt(1, idOfActiveOrder);
                    st.setInt(2, productId);
                    st.setInt(3, 1);
                    st.executeUpdate();
                    ResultSet rs = st.getGeneratedKeys();
                    rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explaination
                    int productAmountId = rs.getInt(1);
                    System.out.println(productAmountId);
                } catch (SQLException throwables) {
                    throw new RuntimeException("Error while adding new product.", throwables);
                }

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

    public int amountOfProductInCart(Product product) {
        int productId = product.getId();
        try (Connection conn = dataSource.getConnection()) {
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


    public void increaseAmountOfProductInCartByOne(Product product, int amountOfProductInCart, int idOfActiveOrder) {
        int productId = product.getId();
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE productamount SET amount = ? WHERE productid = ? AND orderid =? ";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, amountOfProductInCart + 1);
            st.setInt(2, productId);
            st.setInt(3, idOfActiveOrder);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createNewActiveOrder() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO orderdetails (userid, isactiveorder) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, sampleUserId);
            st.setInt(2, ACTIVEORDER);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            return rs.getInt(1); // if it returns false, it means its not in the cart yet
        } catch (SQLException e) {
            throw new RuntimeException("Error while checking cart");
        }
    }

    public boolean isCurrentCustomerInDataBase() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM customer WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, sampleUserId);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return false;
            }
            return true; // if it returns false, it means its not in the cart yet
        } catch (SQLException e) {
            throw new RuntimeException("Error while checking cart");
        }
    }

    public boolean doesCustomerHaveActiveOrder() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM orderdetails WHERE userid = ? AND isactiveorder = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, sampleUserId);
            st.setInt(2, ACTIVEORDER);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error while checking cart");
        }
    }

    public int getIdOfActiveOrder() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id FROM orderdetails WHERE userid = ? AND isactiveorder = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, sampleUserId);
            st.setInt(2, ACTIVEORDER);
            ResultSet rs = st.executeQuery();
            rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explaination
            int idOfActiveOrder = rs.getInt(1);
            return idOfActiveOrder;
        } catch( SQLException e)   {
            throw new RuntimeException("Error while checking cart");
        }
    }
    }
