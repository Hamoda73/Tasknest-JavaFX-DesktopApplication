package Tasknest.tests;
import Tasknest.services.CvService;
import Tasknest.models.CV;
import Tasknest.models.Skill;
import Tasknest.services.SkillService;

public class TasknestMain {
    public static void main(String[] args) {

/* ***************************************************** CRUD CV ************************************************************************ */
       CvService cvs = new CvService();
        //cvs.ajouter(new CV("erij", "helloooo",new String[]{"Arabic"}, "erij", "bac", "https://www.facebook.com/erij.mazouz?locale=fr_FR", 33));
        //cvs.modifier(new CV(63,"it's been a while", "helloooo",new String[]{"English", "Arabic"}, "erij", "bac", "https://www.facebook.com/erij.mazouz?locale=fr_FR", 33));
        //cvs.supprimer(new CV(63));

       // System.out.println(cvs.afficher());

/* ***************************************************** CRUD Skill ************************************************************************ */
        SkillService Skills = new SkillService();
        //Skills.ajouter(new Skill("Java12",38, 35));
        //Skills.ajouter(new Skill("CSS",50, 35));
        //Skills.modifier(new Skill(86,"Js",75, 35));
        //Skills.supprimer(new Skill(86));

         //System.out.println(Skills.afficher());
    }
}
