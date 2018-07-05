package com.justinqle.refresh.models.listing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinkFlairRichtext {

    @SerializedName("e")
    private String e;

    @SerializedName("t")
    private String t;

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

}
