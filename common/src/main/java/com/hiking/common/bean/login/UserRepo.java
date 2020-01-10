package com.hiking.common.bean.login;


import com.google.gson.annotations.SerializedName;
import com.hiking.common.util.GsonUtil;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "user_repos")
@TypeConverters(UserRepo.ReposPersistentConverter.class)
public class UserRepo {
    public int indexInSortResponse = -1;  // persistent layer index


    @PrimaryKey
    public Long id;
    @SerializedName("node_id")
    @ColumnInfo(name = "node_id")
    public String nodeId;
    public String name;
    @SerializedName("full_name")
    @ColumnInfo(name = "full_name")
    public String fullName;
    @ColumnInfo(name = "is_private")
    @SerializedName("private")
    public Boolean isPrivate;
    public RepoOwner owner;
    @SerializedName("html_url")
    @ColumnInfo(name = "html_url")
    public String htmlUrl;
    public String description;
    public Boolean fork;
    public String url;
    @SerializedName("forks_url")
    @ColumnInfo(name = "forks_url")
    public String forksUrl;
    @SerializedName("keys_url")
    @ColumnInfo(name = "keys_url")
    public String keysUrl;
    @SerializedName("collaborators_url")
    @ColumnInfo(name = "collaborators_url")
    public String collaboratorsUrl;
    @SerializedName("teams_url")
    @ColumnInfo(name = "teams_url")
    public String teamsUrl;
    @SerializedName("hooks_url")
    @ColumnInfo(name = "hooks_url")
    public String hooksUrl;
    @SerializedName("issue_events_url")
    @ColumnInfo(name = "issue_events_url")
    public String issueEventsUrl;
    @SerializedName("events_url")
    @ColumnInfo(name = "events_url")
    public String eventsUrl;
    @SerializedName("assignees_url")
    @ColumnInfo(name = "assignees_url")
    public String assigneesUrl;
    @SerializedName("branches_url")
    @ColumnInfo(name = "branches_url")
    public String branchesUrl;
    @SerializedName("tags_url")
    @ColumnInfo(name = "tags_url")
    public String tagsUrl;
    @SerializedName("blobs_url")
    @ColumnInfo(name = "blobs_url")
    public String blobsUrl;
    @SerializedName("git_tags_url")
    @ColumnInfo(name = "git_tags_url")
    public String gitTagsUrl;
    @SerializedName("git_refs_url")
    @ColumnInfo(name = "git_refs_url")
    public String gitRefsUrl;
    @SerializedName("trees_url")
    @ColumnInfo(name = "trees_url")
    public String treesUrl;
    @SerializedName("statuses_url")
    @ColumnInfo(name = "statuses_url")
    public String statusesUrl;
    @SerializedName("languages_url")
    @ColumnInfo(name = "languages_url")
    public String languagesUrl;
    @SerializedName("stargazers_url")
    @ColumnInfo(name = "stargazers_url")
    public String stargazersUrl;
    @SerializedName("contributors_url")
    @ColumnInfo(name = "contributors_url")
    public String contributorsUrl;
    @SerializedName("subscribers_url")
    @ColumnInfo(name = "subscribers_url")
    public String subscribersUrl;
    @SerializedName("subscription_url")
    @ColumnInfo(name = "subscription_url")
    public String subscriptionUrl;
    @SerializedName("commits_url")
    @ColumnInfo(name = "commits_url")
    public String commitsUrl;
    @SerializedName("git_commits_url")
    @ColumnInfo(name = "git_commits_url")
    public String gitCommitsUrl;
    @SerializedName("comments_url")
    @ColumnInfo(name = "comments_url")
    public String commentsUrl;
    @SerializedName("issue_comment_url")
    @ColumnInfo(name = "issue_comment_url")
    public String issueCommentUrl;
    @SerializedName("contents_url")
    @ColumnInfo(name = "contents_url")
    public String contentsUrl;
    @SerializedName("compare_url")
    @ColumnInfo(name = "compare_url")
    public String compareUrl;
    @SerializedName("merges_url")
    @ColumnInfo(name = "merges_url")
    public String mergesUrl;
    @SerializedName("archive_url")
    @ColumnInfo(name = "archive_url")
    public String archiveUrl;
    @SerializedName("downloads_url")
    @ColumnInfo(name = "downloads_url")
    public String downloadsUrl;
    @SerializedName("issues_url")
    @ColumnInfo(name = "issues_url")
    public String issuesUrl;
    @SerializedName("pulls_url")
    @ColumnInfo(name = "pulls_url")
    public String pullsUrl;
    @SerializedName("milestones_url")
    @ColumnInfo(name = "milestones_url")
    public String milestonesUrl;
    @SerializedName("notifications_url")
    @ColumnInfo(name = "notifications_url")
    public String notificationsUrl;
    @SerializedName("labels_url")
    @ColumnInfo(name = "labels_url")
    public String labelsUrl;
    @SerializedName("releases_url")
    @ColumnInfo(name = "releases_url")
    public String releasesUrl;
    @SerializedName("deployments_url")
    @ColumnInfo(name = "deployments_url")
    public String deploymentsUrl;
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String createdAt;
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updatedAt;
    @SerializedName("pushed_at")
    @ColumnInfo(name = "pushed_at")
    public String pushedAt;
    @SerializedName("git_url")
    @ColumnInfo(name = "git_url")
    public String gitUrl;
    @SerializedName("ssh_url")
    @ColumnInfo(name = "ssh_url")
    public String sshUrl;
    @SerializedName("clone_url")
    @ColumnInfo(name = "clone_url")
    public String cloneUrl;
    @SerializedName("svn_url")
    @ColumnInfo(name = "svn_url")
    public String svnUrl;
    @SerializedName("homepage")
    @ColumnInfo(name = "homepage")
    public String homepage;
    public int size;
    @SerializedName("stargazers_count")
    @ColumnInfo(name = "stargazers_count")
    public int stargazersCount;
    @SerializedName("watchers_count")
    @ColumnInfo(name = "watchers_count")
    public int watchersCount;
    public String language;
    @SerializedName("has_issues")
    @ColumnInfo(name = "has_issues")
    public boolean hasIssues;
    @SerializedName("has_projects")
    @ColumnInfo(name = "has_projects")
    public boolean hasProjects;
    @SerializedName("has_downloads")
    @ColumnInfo(name = "has_downloads")
    public boolean hasDownloads;
    @SerializedName("has_wiki")
    @ColumnInfo(name = "has_wiki")
    public boolean hasWiki;
    @SerializedName("has_pages")
    @ColumnInfo(name = "has_pages")
    public boolean hasPages;
    @SerializedName("forks_count")
    @ColumnInfo(name = "forks_count")
    public int forksCount;
    @SerializedName("mirror_url")
    @ColumnInfo(name = "mirror_url")
    public String mirrorUrl;
    @SerializedName("open_issues_count")
    @ColumnInfo(name = "open_issues_count")
    public int openIssuesCount;
    public License license;
    public int forks;
    @SerializedName("open_issues")
    @ColumnInfo(name = "open_issues")
    public int openIssues;
    public int watchers;
    @ColumnInfo(name = "default_branch")
    public String defaultBranch;

    static public class RepoOwner {
        public String login;
        public int id;
        @SerializedName("node_id")
        public String nodeId;
        @SerializedName("avatar_url")
        public String avatarUrl;
        @SerializedName("gravatar_id")
        public String gravatarId;
        public String url;
        @SerializedName("html_url")
        public String htmlUrl;
        @SerializedName("followers_url")
        public String followersUrl;
        @SerializedName("following_url")
        public String followingUrl;
        @SerializedName("gists_url")
        public String gistsUrl;
        @SerializedName("starred_url")
        public String starredUrl;
        @SerializedName("subscriptions_url")
        public String subscriptionsUrl;
        @SerializedName("organizations_url")
        public String organizationsUrl;
        @SerializedName("repos_url")
        public String reposUrl;
        @SerializedName("events_url")
        public String eventsUrl;
        @SerializedName("received_events_url")
        public String receivedEventsUrl;
        public String type;
        @SerializedName("site_admin")
        public String siteAdmin;
    }


    static public class License {
        public String key;
        public String name;
        @SerializedName("spdx_id")
        public String spdxId;
        public String url;
        @SerializedName("node_id")
        public String nodeId;
    }

    static public class ReposPersistentConverter {

        // RepoOwner
        @TypeConverter
        public String storeRepoOwnerToString(RepoOwner data) {
            return GsonUtil.toJsonWithNullField(data);
        }

        @TypeConverter
        public RepoOwner storeStringToRepoOwner(String value) {
            return GsonUtil.fromJson(value, RepoOwner.class);
        }

        // License
        @TypeConverter
        public String storeLicenseToString(License data) {
            return GsonUtil.toJsonWithNullField(data);
        }

        @TypeConverter
        public License storeStringToLicense(String value) {
            return GsonUtil.fromJson(value, License.class);
        }
    }
}
