package services;

import models.*;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class LikesService {

    private Connection connection = DataSource.getInstance().getConnection();


    public LikesService() {
    }
    public void add(int user_id, int post_id){
        DislikesService dislikesService = new DislikesService();
        likes like = getLike(user_id);
        Dislikes dislike = dislikesService.getDislike(user_id);
        System.out.println(like);
        if (like.getId() != 0) {
            delete(like);
        }
        else if (dislikesService.getDislike(user_id).getId()!=0){
            dislikesService.delete(dislike);
            like.setUser_id(user_id);
            like.setPost_id(post_id);
            addData(like);
        }
        else{
            like.setUser_id(user_id);
            like.setPost_id(post_id);
            addData(like);
        }

    }

    public likes getLike(int user_id){
        likes like = new likes();
        String req = "SELECT * FROM `likes` WHERE `user_id` = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, user_id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                like = new likes(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("post_id"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return like;
    }

    void delete(likes like){
        String req = "DELETE FROM `likes` WHERE `id` = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, like.getId());
            pst.executeUpdate();
            System.out.println("Like supprimer avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void addData(likes like){
        String req = "INSERT INTO `likes`(`user_id`, `post_id`) VALUES (?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, like.getUser_id());
            pst.setInt(2, like.getPost_id());

            pst.executeUpdate();
            System.out.println("Like ajouter avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getNumber(int post_id)throws SQLException{
        int likeCount = 0;
        String query = "SELECT COUNT(*) AS like_count FROM likes WHERE post_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, post_id);

        // Execute the query and get the results
        ResultSet resultSet = statement.executeQuery();

        // Iterate through the results and get the like count
        if (resultSet.next()) {
            likeCount = resultSet.getInt("like_count");
        }
        return likeCount;
    }
    public List<User> getLikers(int post_id) throws SQLException {
        UserService userService = new UserService();
        List<User> users = new ArrayList<User>();
        List<Integer> usersIds = new ArrayList<Integer>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM likes WHERE post_id = ?;");
        stmt.setInt(1, post_id);  // Set the first parameter (?) to the actual post ID
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            usersIds.add(rs.getInt("id"));
        }
        System.out.println(userService.getUsers(usersIds));
        return userService.getUsers(usersIds);
    }
}
