package com.automatodev.coinSee.controller.entity;

public class UserEntity {
    private String userName;
    private String userLastName;
    private String userCountry;
    private String userEmail;
    private long userPhone;
    private String userPhoto;
    private String userUid;

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

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
