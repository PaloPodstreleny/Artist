package com.artapp.podstreleny.palo.artist.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "genes")
public class Gene implements Parcelable{

    private static final int MINIMUM_SIZE = 2;
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    @SerializedName("display_name")
    private String displayName;
    private String description;
    private String thumbnail;
    private String nextPage;

    @Ignore
    @SerializedName("_links")
    private ImportantLink links;

    public Gene(){

    }

    @Ignore
    protected Gene(Parcel in) {
        id = in.readString();
        name = in.readString();
        displayName = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        nextPage = in.readString();
    }

    @Ignore
    public static final Creator<Gene> CREATOR = new Creator<Gene>() {
        @Override
        public Gene createFromParcel(Parcel in) {
            return new Gene(in);
        }

        @Override
        public Gene[] newArray(int size) {
            return new Gene[size];
        }
    };

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

    public ImportantLink getLinks() {
        return links;
    }

    public void setLinks(ImportantLink links) {
        this.links = links;
    }

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
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
        dest.writeString(displayName);
        dest.writeString(description);
        dest.writeString(thumbnail);
        dest.writeString(nextPage);
    }

    public boolean hasName() {
        return (getName() != null && getName().length() > MINIMUM_SIZE);
    }
}
