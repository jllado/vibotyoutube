package com.vibot.youtube.binding.request;

import java.util.List;
import java.util.Objects;

public class Snippet {

    private String title;
    private String description;
    private List<String> tags = null;
    private int categoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snippet snippet = (Snippet) o;
        return categoryId == snippet.categoryId &&
                Objects.equals(title, snippet.title) &&
                Objects.equals(description, snippet.description) &&
                Objects.equals(tags, snippet.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, tags, categoryId);
    }

    @Override
    public String toString() {
        return "Snippet{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", categoryId=" + categoryId +
                '}';
    }
}
