package com.hiking.common.bean.login;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    //是否网络请求错误
    public boolean mIsErrorRequestResult = false;

    public UserInfo setIsError(boolean isError) {
        mIsErrorRequestResult = isError;
        return this;
    }

    public String login;
    int id;
    @SerializedName("node_id")
    String nodeId;
    @SerializedName("avatar_url")
    String avatarUrl;
    @SerializedName("gravatar_id")
    String gravatarId;
    String url;
    @SerializedName("html_url")
    String htmlUrl;
    @SerializedName("followers_url")
    String followersUrl;
    @SerializedName("following_url")
    String followingUrl;
    @SerializedName("gists_url")
    String gistsUrl;
    @SerializedName("starred_url")
    String starredUrl;
    @SerializedName("subscriptions_url")
    String subscriptionsUrl;
    @SerializedName("organizations_url")
    String organizationsUrl;
    @SerializedName("repos_url")
    String reposUrl;
    @SerializedName("events_url")
    String eventsUrl;
    @SerializedName("received_events_url")
    String receivedEventsUrl;
    String type;
    @SerializedName("site_admin")
    String siteAdmin;
    String name;
    String company;
    String blog;
    String location;
    String email;
    String hireable;
    String bio;
    @SerializedName("public_repos")
    String publicRepos;
    @SerializedName("public_gists")
    String publicGists;
    int followers;
    int following;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("private_gists")
    int privateGists;
    @SerializedName("total_private_repos")
    int totalPrivateRepos;
    @SerializedName("owned_private_repos")
    int ownedPrivateRepos;
    @SerializedName("disk_usage")
    int diskUsage;
    int collaborators;
    @SerializedName("two_factor_authentication")
    boolean twoFactorAuthentication;
}
