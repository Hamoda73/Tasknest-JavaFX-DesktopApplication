package services;

import models.Category;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class  CategoryService {



    private Connection connection = DataSource.getInstance().getConnection();
    public CategoryService() {
    }
    public List<Category> afficher() {
        List<Category> categories = new ArrayList();

        String req = "SELECT * FROM `categories`";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("image_url")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }

    public Category getCategory(int id) {

        Category category = new Category();
        String req = "SELECT * FROM `categories` WHERE `id` = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                category = new Category(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("image_url"));
            }
            System.out.println(category);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return category;
    }


}
