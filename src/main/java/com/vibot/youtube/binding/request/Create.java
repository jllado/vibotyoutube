package com.vibot.youtube.binding.request;


import java.util.Objects;

public class Create {

    private Snippet snippet;
    private Status status;

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Create create = (Create) o;
        return Objects.equals(snippet, create.snippet) &&
                Objects.equals(status, create.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(snippet, status);
    }

    @Override
    public String toString() {
        return "Create{" +
                "snippet=" + snippet +
                ", status=" + status +
                '}';
    }
}
