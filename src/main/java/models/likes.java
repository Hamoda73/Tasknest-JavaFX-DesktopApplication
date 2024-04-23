package models;

public class likes {
    int id, user_id, post_id;
    public likes() {
    }

    public likes(int id, int userId, int postId) {
        this.id = id;
        this.user_id = userId;
        this.post_id = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    @Override
    public String toString() {
        return "likes{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", post_id=" + post_id +
                '}';
    }
}
