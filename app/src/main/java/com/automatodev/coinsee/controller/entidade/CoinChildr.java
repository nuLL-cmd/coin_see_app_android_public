
package com.automatodev.coinsee.controller.entidade;


import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class CoinChildr {

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
}
