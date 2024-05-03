package com.example.user.Model;

import com.example.user.Entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void insertUser(User user) throws SQLException;
    User selectUser(int id);
    List<User> selectAllUsers();
    boolean deleteUser(int id) throws SQLException;
    boolean updateUser(User user) throws SQLException;
}
