package com.tasknest.models;

import java.util.Objects;
import java.lang.*;

import java.util.List;
public class complaint {

    private int id;
    private String type;
    private String message;

    private respond response;
    public complaint(int id, String type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;
    }

    public complaint() {

    }

    public respond getResponse() {
        return response;
    }

    private String responseMessage;


    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }


    public void setResponse(respond response) {
        this.response = response;
        if (response != null) {
            this.responseMessage = response.getMessage();
        }
    }

    @Override
    public String toString() {
        return "complaint{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }



    public complaint(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public complaint(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        complaint complaint = (complaint) o;
        return id == complaint.id && Objects.equals(type, complaint.type) && Objects.equals(message, complaint.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, message);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
