package com.automatodev.coinsee.controller.entidade;

public class UserEntity {
    String userName;
    String userLastName;
    String userCountry;
    String userEmail;
    long userPhone;
    String userPhoto;

    public UserEntity() {
    }

    public UserEntity(String userName, String userLastName, String userCountry, String userEmail, long userPhone) {
        this.userName = userName;
        this.userLastName = userLastName;
        this.userCountry = userCountry;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
