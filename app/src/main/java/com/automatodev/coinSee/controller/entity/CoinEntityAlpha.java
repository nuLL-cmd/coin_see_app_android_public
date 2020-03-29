package com.automatodev.coinSee.controller.entity;

import com.google.gson.annotations.SerializedName;

public class CoinEntityAlpha {

    @SerializedName("1. From_Currency Code")
    private String fromCode;
    @SerializedName("2. From_Currency Name")
    private String fromName;
    @SerializedName("3. To_Currency Code")
    private String toCode;
    @SerializedName("4. To_Currency Name")
    private String toName;
    @SerializedName("5. Exchange Rate")
    private String rate;
    @SerializedName("6. Last Refreshed")
    private String lastRefresh;
    @SerializedName("7. Time Zone")
    private String timeZone;
    @SerializedName("8. Bid Price")
    private String bid;
    @SerializedName("9. Ask Price")
    private String ask;

    public CoinEntityAlpha() {
    }

    public CoinEntityAlpha(String fromCode, String fromName, String toCode, String toName, String rate, String lastRefresh, String timeZone, String bid, String ask) {
        this.fromCode = fromCode;
        this.fromName = fromName;
        this.toCode = toCode;
        this.toName = toName;
        this.rate = rate;
        this.lastRefresh = lastRefresh;
        this.timeZone = timeZone;
        this.bid = bid;
        this.ask = ask;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setLastRefresh(String lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getFromCode() {
        return fromCode;
    }

    public String getFromName() {
        return fromName;
    }

    public String getToCode() {
        return toCode;
    }

    public String getToName() {
        return toName;
    }

    public String getRate() {
        return rate;
    }

    public String getLastRefresh() {
        return lastRefresh;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getBid() {
        return bid;
    }

    public String getAsk() {
        return ask;
    }

}
