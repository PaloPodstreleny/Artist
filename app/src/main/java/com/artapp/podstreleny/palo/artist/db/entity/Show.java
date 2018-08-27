package com.artapp.podstreleny.palo.artist.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "shows")
public class Show implements Parcelable,IListItem {

    private static final int MINIMUM_SIZE = 2;
    @PrimaryKey
    @NonNull
    private String id;

    private String name;
    private String description;
    private String status;
    private String thumbnail;
    private String nextPage;

    @SerializedName("start_at")
    private String startAt;

    @SerializedName("end_at")
    private String endAt;

    @SerializedName("press_release")
    private String pressRelease;

    @SerializedName("sortable_name")
    private String sortableName;

    @SerializedName("is_solo_show")
    private boolean isSoloShow;

    @SerializedName("is_group_show")
    private boolean isGroupShow;

    @Ignore
    @SerializedName("_links")
    private ImportantLink links;

    public Show(){

    }

    @Ignore
    protected Show(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        status = in.readString();
        thumbnail = in.readString();
        nextPage = in.readString();
        startAt = in.readString();
        endAt = in.readString();
        pressRelease = in.readString();
        sortableName = in.readString();
        isSoloShow = in.readByte() != 0;
        isGroupShow = in.readByte() != 0;
    }

    @Ignore
    public static final Creator<Show> CREATOR = new Creator<Show>() {
        @Override
        public Show createFromParcel(Parcel in) {
            return new Show(in);
        }

        @Override
        public Show[] newArray(int size) {
            return new Show[size];
        }
    };

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getPressRelease() {
        return pressRelease;
    }

    public void setPressRelease(String pressRelease) {
        this.pressRelease = pressRelease;
    }

    public String getSortableName() {
        return sortableName;
    }

    public void setSortableName(String sortableName) {
        this.sortableName = sortableName;
    }

    public boolean isSoloShow() {
        return isSoloShow;
    }

    public void setSoloShow(boolean soloShow) {
        isSoloShow = soloShow;
    }

    public boolean isGroupShow() {
        return isGroupShow;
    }

    public void setGroupShow(boolean groupShow) {
        isGroupShow = groupShow;
    }

    @Ignore
    public ImportantLink getLinks() {
        return links;
    }

    @Ignore
    public void setLinks(ImportantLink links) {
        this.links = links;
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(status);
        dest.writeString(thumbnail);
        dest.writeString(nextPage);
        dest.writeString(startAt);
        dest.writeString(endAt);
        dest.writeString(pressRelease);
        dest.writeString(sortableName);
        dest.writeByte((byte) (isSoloShow ? 1 : 0));
        dest.writeByte((byte) (isGroupShow ? 1 : 0));
    }

    @Override
    public boolean hasItemTitle() {
        return (getName() != null && getName().length() > MINIMUM_SIZE);
    }

    @Override
    public String getItemTitle() {
        return getName();
    }

    @Override
    public String getItemThumbnail() {
        return getThumbnail();
    }

    @Override
    public String getItemId() {
        return getId();
    }

    public boolean hasDescription() {
        return (getDescription() != null && getDescription().length() > MINIMUM_SIZE);
    }

    public boolean hasStatus() {
        return (getStatus() != null && getStatus().length() > MINIMUM_SIZE);
    }

    public boolean hasStartDate() {
        return (getStartAt() != null && getStartAt().length() > MINIMUM_SIZE);
    }

    public boolean hasEndDate() {
        return (getEndAt() != null && getEndAt().length() > MINIMUM_SIZE);
    }

    public boolean hasPressRelease() {
        return (getPressRelease() != null && getPressRelease().length() > MINIMUM_SIZE);
    }
}
