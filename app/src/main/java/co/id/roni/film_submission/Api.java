package co.id.roni.film_submission;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://api.themoviedb.org/";
    String API_KEY = "925c18bd71917db0242931c2fce8c338";

    @GET("3/discover/movie?api_key=" + API_KEY + "&sort_by=popularity.desc&include_adult=false&include_video=false")
    Call<MovieObjectData> getMovieList(@Query("id") int Id, @Query("language") String language);

    @GET("/3/movie/{movie_id}?api_key=" + API_KEY)
    Call<MovieDetailModel> getMovieDetail(@Path("movie_id") int Id, @Query("language") String language);
}
