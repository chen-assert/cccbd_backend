package data;

import java.io.Serializable;


public class MyMessage implements Serializable {
    private int status;
    private String type;
    private String message;

    public MyMessage() {

    }

    public MyMessage(int status, String type, String message) {
        this.status = status;
        this.type = type;
        this.message = message;
    }
    public MyMessage(String type){
        if(type.equals("sql fail")){
            setMessage("sql pool fail!!");
            setStatus(403);
            setType("fail");
        }
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

}