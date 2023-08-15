package com.example.paira10.Models;

public class Users {
    String profilePic, userName, mail, password , userId, lastMessage,FullName,status;

    public Users(String profilePic, String userName, String mail, String password, String userId, String lastMessage, String fullName, String status) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
        FullName = fullName;
        this.status = status;
    }
    public Users( String userName, String mail, String password) {

        this.userName = userName;
        this.mail = mail;
        this.password = password;

    }
    public Users(  String mail, String password) {


        this.mail = mail;
        this.password = password;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users(){}
    public Users( String FullName,String userName, String mail, String password) {
        this.FullName=FullName;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getUserId(String key) {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

//    public String getUserId(String key) {
//        return userId;
//
//    }
}
