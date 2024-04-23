package services;

import models.Posts;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostsService {
    private Connection connection = DataSource.getInstance().getConnection();


    public void ajouter(Posts post) {
        String req = "INSERT INTO `posts`(`title`, `content`, `user_id`, `category_id`, `date_time`, `image_url`) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, post.getTitle());
            pst.setString(2, post.getContent());
            pst.setInt(3, post.getUser_id());
            pst.setInt(4, post.getCategory_id());
            pst.setTimestamp(5,Timestamp.valueOf(post.getDate()));
            pst.setString(6, post.getImage_url());



            pst.executeUpdate();
            System.out.println("Post ajouter avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<Posts> afficher(int category_id) {
        List<Posts> posts = new ArrayList();

        String req = "SELECT * FROM `posts` WHERE `category_id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, category_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                posts.add(new Posts(rs.getInt("id"), rs.getString("title"), rs.getString("content"), rs.getInt("user_id"), category_id, rs.getTimestamp("date_time").toLocalDateTime(), rs.getString("image_url")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return posts;
    }
    public void supprimer(Posts post) {
        String req = "DELETE FROM `posts` WHERE `id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, post.getId());
            pst.executeUpdate();
            System.out.println("Post supprimer avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void modifier(Posts post) {
        String req = "UPDATE `posts` SET `title` = ?, `content` = ?, `category_id` = ?, `date_time` = ?, `image_url` = ? WHERE `id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, post.getTitle());
            pst.setString(2, post.getContent());
            pst.setInt(3, post.getCategory_id());
            pst.setTimestamp(4, Timestamp.valueOf(post.getDate()));
            pst.setString(5, post.getImage_url());
            pst.setInt(6, post.getId());



            pst.executeUpdate();
            System.out.println("Post modifiée avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}



