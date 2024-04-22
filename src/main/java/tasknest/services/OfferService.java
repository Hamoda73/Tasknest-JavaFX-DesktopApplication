package tasknest.services;
import tasknest.models.offers;
import tasknest.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class OfferService implements IService<offers>{

    private Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(offers offer) {
        String req = "INSERT INTO `offers`(`entreprise_name`, `domain`,`post`, `description`,`localisation`, `period`,`salary`, `user_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, offer.getEntreprise_name());
            pst.setString(2, offer.getDomain());
            pst.setString(3, offer.getPost());
            pst.setString(4, offer.getDescription());
            pst.setString(5, offer.getLocalisation());
            pst.setString(6, offer.getPeriod());
            pst.setFloat(7, offer.getSalary());
            pst.setInt(8, offer.getUser_id());
            pst.executeUpdate();
            System.out.println("offer added successfully !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(offers offer) {
        String req = "DELETE FROM `offers` WHERE `offers`.`id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, offer.getId());
            pst.executeUpdate();
            System.out.println("Offer deleted succussfully !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(offers offer) {
        String req = "UPDATE `offers` SET `entreprise_name` = ?, `domain` = ?,`post` = ?,`description` = ?, `localisation` = ?,`period` = ?, `salary` = ? WHERE `offers`.`id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1,offer.getEntreprise_name());
            pst.setString(2, offer.getDomain());
            pst.setString(3, offer.getPost());
            pst.setString(4, offer.getDescription());
            pst.setString(5, offer.getLocalisation());
            pst.setString(  6, offer.getPeriod());
            pst.setFloat(7, offer.getSalary());
            pst.setInt(8,offer.getId());
            pst.executeUpdate();
            System.out.println("offer updated successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<offers> afficher() {
        List<offers> offersList = new ArrayList();

        String req = "SELECT * FROM `offers`";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                offersList.add(new offers(rs.getInt("id"), rs.getString("entreprise_name"), rs.getString("domain"), rs.getString("post"), rs.getString("description"),rs.getString("localisation"),rs.getString("period"),rs.getFloat("salary"),rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return offersList;
    }

}
