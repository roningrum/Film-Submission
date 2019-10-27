package co.id.roni.film_submission.model.favorite;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = MovieFavModel.TABLE_NAME)
public class MovieFavModel {

    public static final String TABLE_NAME = "tbMovieFav";

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "vote_average")
    private double vote_average;

    @Ignore
    public MovieFavModel() {

    }

    public MovieFavModel(int id, String title, String poster_path, String overview, double vote_average) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
    }

    public static MovieFavModel fromContentValues(ContentValues values) {
        MovieFavModel movieFavModel = new MovieFavModel();
        if (values.containsKey("id")) {
            movieFavModel.setId(values.getAsInteger("id"));
        }
        if (values.containsKey("title")) {
            movieFavModel.setTitle(values.getAsString("title"));
        }
        if (values.containsKey("poster_path")) {
            movieFavModel.setPoster_path(values.getAsString("poster_path"));
        }
        if (values.containsKey("overview")) {
            movieFavModel.setOverview(values.getAsString("overview"));
        }
        if (values.containsKey("vote_average")) {
            movieFavModel.setVote_average(values.getAsDouble("vote_average"));
        }
        return movieFavModel;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
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
