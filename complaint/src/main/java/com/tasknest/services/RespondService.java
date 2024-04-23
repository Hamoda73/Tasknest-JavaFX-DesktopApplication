package com.tasknest.services;

import com.tasknest.models.complaint;
import com.tasknest.models.respond;
import com.tasknest.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RespondService implements IService<respond>{

    private Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(respond respond) {
        String req = "INSERT INTO `respond`(`complaint_id`, `message`) VALUES ('" + respond.getComplaint_id() + "', '" + respond.getMessage() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Reponse ajouté avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void supprimer(respond respond) {
        String req = "DELETE FROM `respond` WHERE `respond`.`id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, respond.getId());
            pst.executeUpdate();
            System.out.println("Reponse supprimé avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(respond respond) {
        String req = "UPDATE `respond` SET `complaint_id` = '" + respond.getComplaint_id() + "', `message` = '" + respond.getMessage() + "' WHERE `respond`.`id` = " + respond.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Reponse modifiée avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<respond> afficher() {
        System.out.println("hi");
        List<respond> respond = null;

        return respond;
    }
  /*  @Override
    public List<respond> afficher() {
        List<respond> responds = new ArrayList();

        String req = "SELECT * FROM `respond`";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                responds.add(new respond(rs.getInt("id"), rs.getInt("complaint_id"), rs.getString("message")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return responds;
    }

   */

        @Override
        public List<complaint> afficher1() {
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


}
