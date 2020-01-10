package com.hiking.common.bean.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginRequest {
    final List<String> scopes;
    final String note;
    @SerializedName("client_id")
    final String clientId;
    @SerializedName("client_secret")
    final String clientSecret;

    public LoginRequest(Builder builder) {
        scopes = builder.scopes;
        note = builder.note;
        clientId = builder.clientId;
        clientSecret = builder.clientSecret;
    }

    public static class Builder{
        List<String> scopes;
        String note;
        String clientId;
        String clientSecret;

        public Builder setScopes(List<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder setNote(String note) {
            this.note = note;
            return this;
        }

        public Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public LoginRequest build(){
            return new LoginRequest(this);
        }

    }
}
