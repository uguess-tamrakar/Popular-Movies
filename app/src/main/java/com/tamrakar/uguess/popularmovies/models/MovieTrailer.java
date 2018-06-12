package com.tamrakar.uguess.popularmovies.models;

public class MovieTrailer {

    //region Variables..
    private String name;
    private String id;
    private String key;
    //endregion

    //region Constructors...
    public MovieTrailer(String key, String id, String name) {
        this.key = key;
        this.id = id;
        this.name = name;
    }
    //endregion

    //region Getters...
    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
    //endregion
}
