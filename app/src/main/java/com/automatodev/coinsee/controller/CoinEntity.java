package com.automatodev.coinsee.controller;

import java.util.HashMap;
import java.util.Map;

public class CoinEntity {

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

    public CoinEntity() {
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
}