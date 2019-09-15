package co.id.roni.film_submission;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieModel implements Parcelable {
    private int id;
    private String title;
    private String poster_path;
    private String overview;
    private String release_date;
    private double vote_average;

    MovieModel(JSONObject jsonObject) {
        try {
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            String poster_path = jsonObject.getString("poster_path");
            String overview = jsonObject.getString("overview");
            String release_date = jsonObject.getString("release_date");
            double vote_average = jsonObject.getDouble("vote_average");

            this.id = id;
            this.title = title;
            this.poster_path = ("https://image.tmdb.org/t/p/w185" + jsonObject.getString("poster_path"));
            this.overview = overview;
            this.release_date = release_date;
            this.vote_average = vote_average;

        } catch (JSONException e) {
            e.printStackTrace();
        }


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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeDouble(this.vote_average);
    }

    protected MovieModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readDouble();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
