package com.vibot.youtube.binding.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Installed {

    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("project_id")
    private String projectId;
    @JsonProperty("auth_uri")
    private String authUri;
    @JsonProperty("token_uri")
    private String tokenUri;
    @JsonProperty("auth_provider_x509_cert_url")
    private String authProviderX509CertUrl;
    @JsonProperty("client_secret")
    private String clientSecret;
    @JsonProperty("auth_token")
    private String authToken;
    @JsonProperty("redirect_uris")
    private List<String> redirectUris = null;
    @JsonProperty("refresh_token")
    private String refreshToken = null;

    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("auth_uri")
    public String getAuthUri() {
        return authUri;
    }

    @JsonProperty("auth_uri")
    public void setAuthUri(String authUri) {
        this.authUri = authUri;
    }

    @JsonProperty("token_uri")
    public String getTokenUri() {
        return tokenUri;
    }

    @JsonProperty("token_uri")
    public void setTokenUri(String tokenUri) {
        this.tokenUri = tokenUri;
    }

    @JsonProperty("auth_provider_x509_cert_url")
    public String getAuthProviderX509CertUrl() {
        return authProviderX509CertUrl;
    }

    @JsonProperty("auth_provider_x509_cert_url")
    public void setAuthProviderX509CertUrl(String authProviderX509CertUrl) {
        this.authProviderX509CertUrl = authProviderX509CertUrl;
    }

    @JsonProperty("client_secret")
    public String getClientSecret() {
        return clientSecret;
    }

    @JsonProperty("client_secret")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @JsonProperty("auth_token")
    public String getAuthToken() {
        return authToken;
    }

    @JsonProperty("auth_token")
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @JsonProperty("redirect_uris")
    public List<String> getRedirectUris() {
        return redirectUris;
    }

    @JsonProperty("redirect_uris")
    public void setRedirectUris(List<String> redirectUris) {
        this.redirectUris = redirectUris;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Installed installed = (Installed) o;
        return Objects.equals(clientId, installed.clientId) &&
                Objects.equals(projectId, installed.projectId) &&
                Objects.equals(authUri, installed.authUri) &&
                Objects.equals(tokenUri, installed.tokenUri) &&
                Objects.equals(authProviderX509CertUrl, installed.authProviderX509CertUrl) &&
                Objects.equals(clientSecret, installed.clientSecret) &&
                Objects.equals(authToken, installed.authToken) &&
                Objects.equals(redirectUris, installed.redirectUris) &&
                Objects.equals(refreshToken, installed.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, projectId, authUri, tokenUri, authProviderX509CertUrl, clientSecret, authToken, redirectUris, refreshToken);
    }

    @Override
    public String toString() {
        return "Installed{" +
                "clientId='" + clientId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", authUri='" + authUri + '\'' +
                ", tokenUri='" + tokenUri + '\'' +
                ", authProviderX509CertUrl='" + authProviderX509CertUrl + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", authToken='" + authToken + '\'' +
                ", redirectUris=" + redirectUris +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
