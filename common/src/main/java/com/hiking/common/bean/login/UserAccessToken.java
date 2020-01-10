package com.hiking.common.bean.login;

import com.google.gson.annotations.SerializedName;

public class UserAccessToken {
    @SerializedName("id")
    int id;
    @SerializedName("token")
    String token;
    @SerializedName("url")
    String url;
}
