package com.automatodev.coinSee.controller.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class UserEntity implements Parcelable {
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

    protected UserEntity(Parcel in) {
        userName = in.readString();
        userLastName = in.readString();
        userCountry = in.readString();
        userEmail = in.readString();
        userPhone = in.readLong();
        userPhoto = in.readString();
        userUid = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel in) {
            return new UserEntity(in);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userLastName);
        dest.writeString(userCountry);
        dest.writeString(userEmail);
        dest.writeLong(userPhone);
        dest.writeString(userPhoto);
        dest.writeString(userUid);
    }
}
