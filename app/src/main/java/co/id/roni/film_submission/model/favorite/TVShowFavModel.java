package co.id.roni.film_submission.model.favorite;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import co.id.roni.film_submission.data.MovieDatabase;

@Entity(tableName = TVShowFavModel.TABLE_NAME)
public class TVShowFavModel {

    public static final String TABLE_NAME = "tbTVShowFav";

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "vote_average")
    private double vote_average;

    @Ignore
    public TVShowFavModel() {
    }

    public TVShowFavModel(Context context) {
        MovieDatabase.getDatabase(context);
    }

    public TVShowFavModel(int id, String name, String poster_path, String overview, double vote_average) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
    }

    public static TVShowFavModel fromContentValues(ContentValues values) {
        TVShowFavModel tvFavModel = new TVShowFavModel();
        if (values.containsKey("id")) {
            tvFavModel.setId(values.getAsInteger("id"));
        }
        if (values.containsKey("name")) {
            tvFavModel.setName(values.getAsString("name"));
        }
        if (values.containsKey("poster_path")) {
            tvFavModel.setPoster_path(values.getAsString("poster_path"));
        }
        if (values.containsKey("overview")) {
            tvFavModel.setOverview(values.getAsString("overview"));
        }
        if (values.containsKey("vote_average")) {
            tvFavModel.setVote_average(values.getAsDouble("vote_average"));
        }
        return tvFavModel;
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
