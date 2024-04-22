package Tasknest.services;

import Tasknest.models.CV;
import Tasknest.models.Skill;
import Tasknest.utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class SkillService implements IService<Skill>{
    private Connection connection = DataSource.getInstance().getConnection();
    @Override
    public void ajouter(Skill skill) {
        String req = "INSERT INTO `skill`(`name`, `skill_value`, `cv_id`) VALUES (?, ?, ?);";
        try {
            PreparedStatement skillst = connection.prepareStatement(req);
            skillst.setString(1, skill.getName());
            skillst.setInt(2, skill.getSkill_value());
            skillst.setInt(3, skill.getCv_id());
            skillst.executeUpdate();
            System.out.println("Skill ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void supprimer(Skill skill) {
        String req = "DELETE FROM `skill` WHERE `skill`.`id` = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, skill.getId());
            pst.executeUpdate();
            System.out.println("Skill supprimer avec succes !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void modifier(Skill skill) {
        String req = "UPDATE `skill` SET `name` = ?, `skill_value` = ? WHERE `skill`.`id` = ?;";
        try {
            PreparedStatement skillst = connection.prepareStatement(req);
            skillst.setString(1, skill.getName());
            skillst.setInt(2, skill.getSkill_value());
            skillst.setInt(3, skill.getId());
            skillst.executeUpdate();
            System.out.println("Skill modifiée avec succes !");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public List<Skill> afficher() {
        List<Skill> skills = new ArrayList<>();

        String req = "SELECT * FROM `skill`";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                skills.add(new Skill(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("skill_value"),
                        rs.getInt("cv_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return skills;
    }
}
