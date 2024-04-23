package com.tasknest.services;
import com.tasknest.models.complaint;
import com.tasknest.services.ComplaintService;
import java.util.List;
public interface IService<T> {

    public void ajouter(T t);
    public void supprimer(T t);
    public void modifier(T t);
    public List<T> afficher();

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
    List<complaint> afficher1();
}
