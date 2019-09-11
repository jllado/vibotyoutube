package com.vibot.youtube.binding.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "installed"
})
public class ClientSecret {

    @JsonProperty("installed")
    private Installed installed;

    @JsonProperty("installed")
    public Installed getInstalled() {
        return installed;
    }

    @JsonProperty("installed")
    public void setInstalled(Installed installed) {
        this.installed = installed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientSecret that = (ClientSecret) o;
        return Objects.equals(installed, that.installed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(installed);
    }

    @Override
    public String toString() {
        return "ClientSecret{" +
                "installed=" + installed +
                '}';
    }
}
