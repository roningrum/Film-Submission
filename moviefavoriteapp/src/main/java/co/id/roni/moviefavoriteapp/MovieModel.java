package co.id.roni.moviefavoriteapp;

import android.database.Cursor;

public class MovieModel {
    private int id;
    private String title;
    private String poster_path;
    private String overview;
    private double vote_average;

    public MovieModel() {
    }

    public MovieModel(int id, String title, String poster_path, String overview, double vote_average) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
    }

    public MovieModel(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("id"));
        title = cursor.getString(cursor.getColumnIndex("title"));
        overview = cursor.getString(cursor.getColumnIndex("overview"));
        poster_path = cursor.getString(cursor.getColumnIndex("poster_path"));
        vote_average = cursor.getDouble(cursor.getColumnIndex("vote_average"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

}
