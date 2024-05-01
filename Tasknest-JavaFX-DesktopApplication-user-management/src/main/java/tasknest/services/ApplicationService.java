package tasknest.services;

import tasknest.models.Application;
import tasknest.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationService implements IService <Application>{

    private Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Application application) {
        String req = "INSERT INTO `application`(`offers_id`, `user_id`, `cv`) VALUES (?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, application.getOffers_id());
            pst.setInt(2, application.getUser_id());
            pst.setString(3, application.getCv());

            pst.executeUpdate();
            System.out.println("application added successfully !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Application application) {
        String req = "DELETE FROM `application` WHERE `application`.`id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, application.getId());
            pst.executeUpdate();
            System.out.println("application deleted succussfully !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Application application) {
        String req = "UPDATE `Application` SET `offers_id` = ?, `user_id` = ?,`cv` = ?  WHERE `application`.`id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, application.getOffers_id());
            pst.setInt(2, application.getUser_id());
            pst.setString(3, application.getCv());
            pst.setInt(4, application.getId());
            pst.executeUpdate();
            System.out.println("app updated successfully !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Application> afficher() {
        List<Application> offersList = new ArrayList();

        String req = "SELECT * FROM `application`";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                offersList.add(new Application(rs.getInt("id"),rs.getInt("offers_id"),rs.getInt("user_id"),rs.getString("cv")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return offersList;
    }


}
