package data;

import java.io.Serializable;


public class Message implements Serializable {
    int status;
    String type;
    String username;
    String message;

    public Message() {

    }

    public Message(int status, String type, String username, String message) {
        this.status = status;
        this.type = type;
        this.username = username;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}