package services;

import models.Category;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class UserService {

    private Connection connection = DataSource.getInstance().getConnection();

    public User getUserById(int id) {
        User user = new User();
        String req = "SELECT * FROM `user` WHERE `id` = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("fname"), rs.getString("image"));
            }
            System.out.println(user);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }
    List<User> getUsers(List<Integer> ids) {
        List<User> users = new ArrayList<>();
        for(Integer id : ids) {
            System.out.println(id);
            User user = getUserById((int) id);
            users.add(user);
        }
        return users;

    }
}
