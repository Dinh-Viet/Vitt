package com.example.demo.Repository;

import com.example.demo.Entity.Product;
import com.example.demo.Model.MyConectionDB;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Connection conn = MyConectionDB.getMySQLConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setAddress(rs.getString("address"));
                product.setPhone(rs.getString("phone"));
                product.setPrice(rs.getDouble("price"));
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product save(Product product) {
        String query = "INSERT INTO product (name, phone, address, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = MyConectionDB.getMySQLConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getPhone());
            statement.setString(3, product.getAddress());
            statement.setDouble(4, product.getPrice());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public Product findById(Long id) {
        Product product = null;
        String query = "SELECT * FROM product WHERE id = ?";

        try (Connection connection = MyConectionDB.getMySQLConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setName(resultSet.getString("name"));
                    product.setAddress(resultSet.getString("address"));
                    product.setPhone(resultSet.getString("phone"));
                    product.setPrice(resultSet.getDouble("price"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public void deleteById(Long id) {
        String query = "DELETE FROM product WHERE id = ?";

        try (Connection connection = MyConectionDB.getMySQLConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
