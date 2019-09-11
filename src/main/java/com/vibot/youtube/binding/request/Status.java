package com.vibot.youtube.binding.request;


import java.util.Objects;

public class Status {

    private String privacyStatus;

    public String getPrivacyStatus() {
        return privacyStatus;
    }

    public void setPrivacyStatus(String privacyStatus) {
        this.privacyStatus = privacyStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(privacyStatus, status.privacyStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(privacyStatus);
    }

    @Override
    public String toString() {
        return "Status{" +
                "privacyStatus='" + privacyStatus + '\'' +
                '}';
    }
}
