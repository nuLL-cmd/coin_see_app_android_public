
package com.automatodev.coinSee.controller.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class CoinChildr implements Parcelable {

    @Expose
    private String ask;
    @Expose
    private String bid;
    @Expose
    private String code;
    @Expose
    private String codein;
    @Expose
    private String create_date;
    @Expose
    private String high;
    @Expose
    private String low;
    @Expose
    private String name;
    @Expose
    private String pctChange;
    @Expose
    private String timestamp;
    @Expose
    private String varBid;

    private int ulrPhoto;
    private boolean fav;

    public CoinChildr(String code, String codein, String name, int ulrPhoto, boolean fav) {
        this.code = code;
        this.codein = codein;
        this.name = name;
        this.ulrPhoto = ulrPhoto;
        this.fav = fav;
    }

    public CoinChildr(String code, String codein, String name, boolean fav) {
        this.code = code;
        this.codein = codein;
        this.name = name;
        this.fav = fav;
    }

    public CoinChildr() {
    }

    protected CoinChildr(Parcel in) {
        ask = in.readString();
        bid = in.readString();
        code = in.readString();
        codein = in.readString();
        create_date = in.readString();
        high = in.readString();
        low = in.readString();
        name = in.readString();
        pctChange = in.readString();
        timestamp = in.readString();
        varBid = in.readString();
        ulrPhoto = in.readInt();
        fav = in.readByte() != 0;
    }

    public static final Creator<CoinChildr> CREATOR = new Creator<CoinChildr>() {
        @Override
        public CoinChildr createFromParcel(Parcel in) {
            return new CoinChildr(in);
        }

        @Override
        public CoinChildr[] newArray(int size) {
            return new CoinChildr[size];
        }
    };

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodein() {
        return codein;
    }

    public void setCodein(String codein) {
        this.codein = codein;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPctChange() {
        return pctChange;
    }

    public void setPctChange(String pctChange) {
        this.pctChange = pctChange;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVarBid() {
        return varBid;
    }

    public void setVarBid(String varBid) {
        this.varBid = varBid;
    }

    public int getUlrPhoto() {
        return ulrPhoto;
    }

    public void setUlrPhoto(int ulrPhoto) {
        this.ulrPhoto = ulrPhoto;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ask);
        dest.writeString(bid);
        dest.writeString(code);
        dest.writeString(codein);
        dest.writeString(create_date);
        dest.writeString(high);
        dest.writeString(low);
        dest.writeString(name);
        dest.writeString(pctChange);
        dest.writeString(timestamp);
        dest.writeString(varBid);
        dest.writeInt(ulrPhoto);
        dest.writeByte((byte) (fav ? 1 : 0));
    }
}
