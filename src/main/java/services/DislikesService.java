package services;

import models.Dislikes;
import models.likes;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DislikesService {

    private Connection connection = DataSource.getInstance().getConnection();


    public DislikesService(){}

    public Dislikes getDislike(int user_id){
        Dislikes dislike = new Dislikes();
        String req = "SELECT * FROM `dislikes` WHERE `user_id` = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, user_id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dislike = new Dislikes(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("post_id"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dislike;
    }

    void delete(Dislikes dislike){
        String req = "DELETE FROM `dislikes` WHERE `id` = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, dislike.getId());
            pst.executeUpdate();
            System.out.println("Dislike supprimer avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void add(int user_id, int post_id){
        LikesService likesService = new LikesService();
        Dislikes dislike = getDislike(user_id);
        likes like = likesService.getLike(user_id);
        System.out.println(dislike);
        if (dislike.getId() != 0) {
            delete(dislike);
        }
        else if (likesService.getLike(user_id).getId()!=0){
            likesService.delete(like);
            dislike.setUser_id(user_id);
            dislike.setPost_id(post_id);
            addData(dislike);
        }
        else{
            dislike.setUser_id(user_id);
            dislike.setPost_id(post_id);
            addData(dislike);
        }

    }

    void addData(Dislikes dislike){
        String req = "INSERT INTO `dislikes`(`user_id`, `post_id`) VALUES (?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, dislike.getUser_id());
            pst.setInt(2, dislike.getPost_id());

            pst.executeUpdate();
            System.out.println("Dislike ajouter avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int getNumber(int post_id)throws SQLException{
        int dislikeCount = 0;
        String query = "SELECT COUNT(*) AS dislike_count FROM dislikes WHERE post_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, post_id);

        // Execute the query and get the results
        ResultSet resultSet = statement.executeQuery();

        // Iterate through the results and get the like count
        if (resultSet.next()) {
            dislikeCount = resultSet.getInt("dislike_count");
        }
        return dislikeCount;
    }
}
