package com.vibot.youtube.binding.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Thumbnails {

    @JsonProperty("default")
    private Thumbnail primary;
    private Thumbnail medium;
    private Thumbnail high;

    public Thumbnail getMedium() {
        return medium;
    }

    public void setMedium(Thumbnail medium) {
        this.medium = medium;
    }

    public Thumbnail getHigh() {
        return high;
    }

    public void setHigh(Thumbnail high) {
        this.high = high;
    }

    public Thumbnail getPrimary() {
        return primary;
    }

    public void setPrimary(Thumbnail primary) {
        this.primary = primary;
    }
}
