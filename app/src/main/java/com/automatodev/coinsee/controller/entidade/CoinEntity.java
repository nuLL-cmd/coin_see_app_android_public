package com.automatodev.coinsee.controller.entidade;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class CoinEntity implements Parcelable {

    private String code;
    private String codein;
    private String name;
    private double high;
    private double low;
    private double varBid;
    private double pctChange;
    private double bid;
    private double ask;
    private long timestamp;
    private String create_date;
    private String urlCoin;
    private boolean fav;


    public CoinEntity(String code, String codein, String name, String urlCoin, boolean fav) {
        this.code = code;
        this.codein = codein;
        this.name = name;
        this.urlCoin = urlCoin;
        this.fav = fav;
    }

    public CoinEntity(String code, String name, String urlCoin) {
        this.code = code;
        this.name = name;
        this.urlCoin = urlCoin;
    }

    public CoinEntity() {
    }

    protected CoinEntity(Parcel in) {
        code = in.readString();
        codein = in.readString();
        name = in.readString();
        high = in.readDouble();
        low = in.readDouble();
        varBid = in.readDouble();
        pctChange = in.readDouble();
        bid = in.readDouble();
        ask = in.readDouble();
        timestamp = in.readLong();
        create_date = in.readString();
        urlCoin = in.readString();
        fav = in.readByte() != 0;
    }

    public static final Creator<CoinEntity> CREATOR = new Creator<CoinEntity>() {
        @Override
        public CoinEntity createFromParcel(Parcel in) {
            return new CoinEntity(in);
        }

        @Override
        public CoinEntity[] newArray(int size) {
            return new CoinEntity[size];
        }
    };

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getVarBid() {
        return varBid;
    }

    public void setVarBid(double varBid) {
        this.varBid = varBid;
    }

    public double getPctChange() {
        return pctChange;
    }

    public void setPctChange(double pctChange) {
        this.pctChange = pctChange;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUrlCoin() {
        return urlCoin;
    }

    public void setUrlCoin(String urlCoin) {
        this.urlCoin = urlCoin;
    }

    public boolean getFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }


    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("codein", codein);
        result.put("name", name);
        result.put("urlCoin", urlCoin);
        result.put("fav",fav);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(codein);
        dest.writeString(name);
        dest.writeDouble(high);
        dest.writeDouble(low);
        dest.writeDouble(varBid);
        dest.writeDouble(pctChange);
        dest.writeDouble(bid);
        dest.writeDouble(ask);
        dest.writeLong(timestamp);
        dest.writeString(create_date);
        dest.writeString(urlCoin);
        dest.writeByte((byte) (fav ? 1 : 0));
    }
}