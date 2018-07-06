package com.justinqle.refresh.models.listing;

import com.google.gson.annotations.SerializedName;

public class Child {

    @SerializedName("kind")
    private String kind;

    @SerializedName("data")
    private Type data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Type getData() {
        return data;
    }

    public void setData(Type data) {
        this.data = data;
    }

}