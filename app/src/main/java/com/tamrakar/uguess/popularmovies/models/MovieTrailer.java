package com.tamrakar.uguess.popularmovies.models;

public class MovieTrailer {

    //region Variables..
    private String key;
    private String name;
    private String site;
    //endregion

    //region Constructors...
    public MovieTrailer(String key, String name, String site) {
        this.key = key;
        this.name = name;
        this.site = site;

    }
    //endregion

    //region Getters...
    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
    //endregion

    public boolean isYouTubeTrailer() {
        return site.contains("YouTube");
    }
}
