package models;

import java.time.LocalDateTime;

public class Comments {
    int id;
    String content;
    int user_id;
    int post_id;
    LocalDateTime date;

    public Comments(int id, String content, int user_id, int post_id, LocalDateTime date) {
        this.id = id;
        this.content = content;
        this.user_id = user_id;
        this.post_id = post_id;
        this.date = date;
    }

    public Comments(int user_id, int post_id, String content) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.content = content;
        this.date = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user_id=" + user_id +
                ", post_id=" + post_id +
                ", date=" + date +
                '}';
    }
}
