package com.example.paira10.Models;

public class MessageModel {
    String uId , message,messageId;


    Long timestamp;
    public MessageModel(String uId, String message, Long timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public MessageModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }
    public MessageModel(){}

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }


    //    String str = Long.toString(timestamp);
    public Long getTimestamp() {
        return timestamp;
    }
}
