package com.tasknest.services;

import com.tasknest.models.complaint;
import com.tasknest.models.respond;
import com.tasknest.utils.DataSource;
import com.tasknest.services.IService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ComplaintService implements IService<complaint>{

    private Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(complaint complaint) {
        String req = "INSERT INTO `complaint`(`type`, `message`) VALUES ('" + complaint.getType() + "', '" + complaint.getMessage() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Complaint ajouté avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void modifier(complaint complaint) {
        String req = "UPDATE `complaint` SET `type` = '" + complaint.getType() + "', `message` = '" + complaint.getMessage() + "' WHERE `complaint`.`id` = " + complaint.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Complaint modifié avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<complaint> afficher() {
        List<complaint> complaints = new ArrayList<>();

        String req = "SELECT c.*, r.message AS response_message " +
                "FROM complaint c LEFT JOIN respond r ON c.id = r.complaint_id";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                complaint c = new complaint(rs.getInt("id"), rs.getString("type"), rs.getString("message"));
                String responseMessage = rs.getString("response_message");
                c.setResponseMessage(responseMessage);
                complaints.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return complaints;
    }

    @Override
    public List<complaint> afficher1() {
        return null;
    }
    @Override
    public void supprimer(complaint complaint) {
        String req = "DELETE FROM `complaint` WHERE `complaint`.`id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, complaint.getId());
            pst.executeUpdate();
            System.out.println("Complaint supprimé avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
