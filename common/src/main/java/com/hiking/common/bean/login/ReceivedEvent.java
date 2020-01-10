package com.hiking.common.bean.login;


import com.google.gson.annotations.SerializedName;
import com.hiking.common.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@TypeConverters(ReceivedEvent.ReceivedEventsPersistentConverter.class)
@Entity(tableName = "user_received_events")
public class ReceivedEvent {


    public static List<Type> SUPPORT_EVENT_TYPES = new ArrayList<Type>() {{
        add(Type.WatchEvent);
        add(Type.ForkEvent);
        add(Type.PushEvent);
        add(Type.CreateEvent);
    }};
    public int indexInResponse = -1;

    @PrimaryKey
    public Long id;
    @ColumnInfo(name = "type")
    public Type type;
    @ColumnInfo(name = "actor")
    public Actor actor;
    @ColumnInfo(name = "repo")
    public ReceivedEventRepo repo;
    @SerializedName("public")
    @ColumnInfo(name = "is_public")
    public Boolean isPublic;
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String createdAt;

    static class Actor {
        @SerializedName("id")
        public int actorId;
        public String login;
        @SerializedName("display_login")
        public String displayLogin;
        @SerializedName("gravatar_id")
        public String gravatarId;
        public String url;
        @SerializedName("avatar_url")
        public String avatarUrl;
    }

    static class ReceivedEventRepo {
        @SerializedName("id")
        public String repoId;
        public String name;
        public String url;
    }

    enum Type {
        WatchEvent,
        ForkEvent,
        PushEvent,
        CreateEvent,
        MemberEvent,
        PublicEvent,
        IssuesEvent,
        IssueCommentEvent,
        CheckRunEvent,
        CheckSuiteEvent,
        CommitCommentEvent,
        DeleteEvent,
        DeploymentEvent,
        DeploymentStatusEvent,
        DownloadEvent,
        FollowEvent,
        ForkApplyEvent,
        GitHubAppAuthorizationEvent,
        GistEvent,
        GollumEvent,
        InstallationEvent,
        InstallationRepositoriesEvent,
        MarketplacePurchaseEvent,
        LabelEvent,
        MembershipEvent,
        MilestoneEvent,
        OrganizationEvent,
        OrgBlockEvent,
        PageBuildEvent,
        ProjectCardEvent,
        ProjectColumnEvent,
        ProjectEvent,
        PullRequestEvent,
        PullRequestReviewEvent,
        PullRequestReviewCommentEvent,
        ReleaseEvent,
        RepositoryEvent,
        RepositoryImportEvent,
        RepositoryVulnerabilityAlertEvent,
        SecurityAdvisoryEvent,
        StatusEvent,
        TeamEvent,
        TeamAddEvent
    }


    public static class ReceivedEventsPersistentConverter {

        // Actor
        @TypeConverter
        public String storeActorToString(Actor data) {
            return GsonUtil.toJsonWithNullField(data);
        }

        @TypeConverter
        public Actor storeStringToActor(String value) {
            return GsonUtil.fromJson(value, Actor.class);
        }

        // ReceivedEventRepo
        @TypeConverter
        public String storeRepoToString(ReceivedEventRepo data) {
            return GsonUtil.toJsonWithNullField(data);
        }

        @TypeConverter
        public ReceivedEventRepo storeStringToRepo(String value) {
            return GsonUtil.fromJson(value, ReceivedEventRepo.class);
        }

        // Type
        @TypeConverter
        public Type restoreEnum(String enumName) {
            return Type.valueOf(enumName);
        }

        @TypeConverter
        public String saveEnumToString(Type enumType) {
            return enumType.name();
        }
    }
}
