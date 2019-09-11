package com.vibot.youtube.binding.response;


public class Status {

    private String uploadStatus;
    private String privacyStatus;
    private String license;
    private boolean embeddable;
    private boolean publicStatsViewable;

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getPrivacyStatus() {
        return privacyStatus;
    }

    public void setPrivacyStatus(String privacyStatus) {
        this.privacyStatus = privacyStatus;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean isEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(boolean embeddable) {
        this.embeddable = embeddable;
    }

    public boolean isPublicStatsViewable() {
        return publicStatsViewable;
    }

    public void setPublicStatsViewable(boolean publicStatsViewable) {
        this.publicStatsViewable = publicStatsViewable;
    }

}
