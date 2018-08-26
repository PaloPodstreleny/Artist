package com.artapp.podstreleny.palo.artist.db.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "artists")
public class Artist implements Parcelable{

    private static final int MINIMAL_LENGTH = 2;

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String gender;
    private String biography;
    private String birthday;
    private String deathday;
    private String hometown;
    private String location;
    private String nationality;

    private String thumbnail;
    private String nextPage;

    @Ignore
    @SerializedName("_links")
    private ImportantLink links;

    public Artist(){

    }

    @Ignore
    protected Artist(Parcel in) {
        id = in.readString();
        name = in.readString();
        gender = in.readString();
        biography = in.readString();
        birthday = in.readString();
        deathday = in.readString();
        hometown = in.readString();
        location = in.readString();
        nationality = in.readString();
        thumbnail = in.readString();
    }

    @Ignore
    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    @Ignore
    public boolean hasName(){
        return (getName().length() > MINIMAL_LENGTH);
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(biography);
        dest.writeString(birthday);
        dest.writeString(deathday);
        dest.writeString(hometown);
        dest.writeString(location);
        dest.writeString(nationality);
        dest.writeString(thumbnail);
    }
}
