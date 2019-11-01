package co.id.roni.moviefavoriteapp.model;

import android.database.Cursor;

import androidx.annotation.NonNull;

public class TVShowFavModel {
    private int id;
    private String name;
    private String poster_path;
    private String overview;
    private double vote_average;


    public TVShowFavModel() {
    }

    public TVShowFavModel(int id, String name, String poster_path, String overview, double vote_average) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
    }

    public TVShowFavModel(Cursor cursorTV) {
        id = cursorTV.getInt(cursorTV.getColumnIndex("id"));
        name = cursorTV.getString(cursorTV.getColumnIndex("name"));
        overview = cursorTV.getString(cursorTV.getColumnIndex("overview"));
        poster_path = cursorTV.getString(cursorTV.getColumnIndex("poster_path"));
        vote_average = cursorTV.getDouble(cursorTV.getColumnIndex("vote_average"));
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
}
