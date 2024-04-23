package models;

import java.time.LocalDateTime;

public class Posts {

    int id;
    String title;
    String content;
    int user_id;
    int category_id;
    LocalDateTime date;

    String image_url;

    public Posts() {}

    public Posts(int id, String title, String content, int user_id, int category_id, LocalDateTime date, String image_url) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.category_id = category_id;
        this.date = date;
        this.image_url = image_url;
    }

    public Posts(String title, String content, int user_id, int category_id, String image_url) {

        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.category_id = category_id;
        this.date = LocalDateTime.now();
        this.image_url = image_url;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return title+ date ;
    }

    public int getId() {
        return id;
    }
}
