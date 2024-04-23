package models;

public class User {
    private int id;
    private String fname;
    private String image_url;

    public User(int id, String fname, String image_url) {
        this.id = id;
        this.fname = fname;
        this.image_url = image_url;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return fname;
    }
}
