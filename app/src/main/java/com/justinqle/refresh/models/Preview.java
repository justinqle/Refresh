package com.justinqle.refresh.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Preview {

    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("enabled")
    @Expose
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
