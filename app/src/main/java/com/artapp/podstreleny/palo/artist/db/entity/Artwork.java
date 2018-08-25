package com.artapp.podstreleny.palo.artist.db.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "artworks")
public class Artwork {

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


    /* Links */
    @Ignore
    @SerializedName("_links")
    private ImportantLink links;


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
}
