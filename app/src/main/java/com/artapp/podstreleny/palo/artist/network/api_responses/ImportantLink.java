package com.artapp.podstreleny.palo.artist.network.api_responses;

public class ImportantLink {
    private Link next;
    private Link thumbnail;


    public Link getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Link thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Link getNext() {
        return next;
    }

    public void setNext(Link next) {
        this.next = next;
    }
}
