package com.justinqle.refresh.models.listing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child {

    @SerializedName("kind")
    private String kind;

    @SerializedName("data")
    private Post post;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Post getData() {
        return post;
    }

    public void setData(Post post) {
        this.post = post;
    }

}