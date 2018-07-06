package com.justinqle.refresh.models.listing;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Preview {

    @SerializedName("images")
    private List<Image> images = null;

    @SerializedName("enabled")
    private boolean enabled;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
