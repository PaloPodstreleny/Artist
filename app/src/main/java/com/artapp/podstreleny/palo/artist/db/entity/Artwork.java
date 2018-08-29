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


@Entity(tableName = "artworks")
public class Artwork implements Parcelable{

    private static final int MINIMUN_LENGTH = 3;

    public Artwork(){

    }

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "artwork_title")
    private String title;

    @ColumnInfo(name = "artwork_category")
    private String category;

    @ColumnInfo(name = "artwork_date")
    private String date;

    @ColumnInfo(name = "artwork_published")
    private boolean published;

    @ColumnInfo(name = "artwork_website")
    private String website;

    @ColumnInfo(name = "artwork_rights")
    @SerializedName("image_rights")
    private String imageRights;

    @ColumnInfo(name = "can_share")
    @SerializedName("artwork_share")
    private boolean share;

    @ColumnInfo(name = "artwork_thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "next_page")
    private String nextPage;


    /* Links */
    @Ignore
    @SerializedName("_links")
    private ImportantLink links;


    @Ignore
    protected Artwork(Parcel in) {
        id = in.readString();
        title = in.readString();
        category = in.readString();
        date = in.readString();
        published = in.readByte() != 0;
        website = in.readString();
        imageRights = in.readString();
        share = in.readByte() != 0;
        thumbnail = in.readString();
        nextPage = in.readString();
    }

    @Ignore
    public Artwork(String name){
        this.title = name;
    }

    public static final Creator<Artwork> CREATOR = new Creator<Artwork>() {
        @Override
        public Artwork createFromParcel(Parcel in) {
            return new Artwork(in);
        }

        @Override
        public Artwork[] newArray(int size) {
            return new Artwork[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImageRights() {
        return imageRights;
    }

    public void setImageRights(String imageRights) {
        this.imageRights = imageRights;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Ignore
    public ImportantLink getLinks() {
        return links;
    }

    @Ignore
    public void setLinks(ImportantLink links) {
        this.links = links;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    @Ignore
    public boolean isValueAvailable(String value){
        return (value.length() > MINIMUN_LENGTH);
    }

    @Ignore
    public boolean hasTitle(){
        return (getTitle() != null && getTitle().length() > MINIMUN_LENGTH);
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
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(date);
        dest.writeByte((byte) (published ? 1 : 0));
        dest.writeString(website);
        dest.writeString(imageRights);
        dest.writeByte((byte) (share ? 1 : 0));
        dest.writeString(thumbnail);
        dest.writeString(nextPage);
    }

    @Ignore
    public boolean hasCategory() {
        return (
                getCategory() != null &&
                        getCategory().length() > MINIMUN_LENGTH);
    }

    @Ignore
    public boolean hasDate() {
        return (
                getDate() != null &&
                        getDate().length() > MINIMUN_LENGTH);
    }

    @Ignore
    public boolean hasWebsite() {
        return (getWebsite() != null && getWebsite().length() > MINIMUN_LENGTH);
    }

    public boolean hasRights() {
        return (getImageRights() != null && getImageRights().length() > MINIMUN_LENGTH);
    }

    public boolean hasThumbnail() {
        return (getThumbnail() != null && getThumbnail().length() > MINIMUN_LENGTH);
    }
}
