package co.id.roni.filmsubmission.model.detailmodel;

import java.util.List;

import co.id.roni.filmsubmission.model.Genre;

public class MovieDetailModel {
    private int id;
    private String backdrop_path;
    private String overview;
    private String poster_path;
    private String title;
    private List<Genre> genres;
    private String release_date;
    private int runtime;
    private double vote_average;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return "https://image.tmdb.org/t/p/w500" + backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public double getVote_average() {
        return vote_average;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}
