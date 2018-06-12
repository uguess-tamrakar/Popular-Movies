package com.tamrakar.uguess.popularmovies.models;

public class MovieReview {

    //region Variables...
    private String id;
    private String author;
    private String content;
    //endregion

    //region Constructors...
    public MovieReview(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }
    //endregion

    //region Getters...
    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
    //endregion
}
