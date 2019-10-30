package co.id.roni.moviefavoriteapp.model;

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

    public MovieModel(Cursor cursorMovie) {
        id = cursorMovie.getInt(cursorMovie.getColumnIndex("id"));
        title = cursorMovie.getString(cursorMovie.getColumnIndex("title"));
        overview = cursorMovie.getString(cursorMovie.getColumnIndex("overview"));
        poster_path = cursorMovie.getString(cursorMovie.getColumnIndex("poster_path"));
        vote_average = cursorMovie.getDouble(cursorMovie.getColumnIndex("vote_average"));
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
