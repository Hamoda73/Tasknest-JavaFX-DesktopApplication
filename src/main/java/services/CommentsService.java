package services;

import models.Comments;
import models.Posts;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentsService {
    private Connection connection = DataSource.getInstance().getConnection();
    public void ajouter(Comments comment) {
        String req = "INSERT INTO `comments`(`user_id`, `post_id`, `content`, `date_time`) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, comment.getUser_id());
            pst.setInt(2, comment.getPost_id());
            pst.setString(3, comment.getContent());
            pst.setTimestamp(4, Timestamp.valueOf(comment.getDate()));

            pst.executeUpdate();
            System.out.println("Comment ajouter avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Comments> afficher(int post_id) {
        List<Comments> comments = new ArrayList();

        String req = "SELECT * FROM `comments` WHERE `post_id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, post_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                comments.add(new Comments(rs.getInt("id"), rs.getString("content"), post_id, rs.getInt("user_id"), rs.getTimestamp("date_time").toLocalDateTime()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return comments;
    }

}
