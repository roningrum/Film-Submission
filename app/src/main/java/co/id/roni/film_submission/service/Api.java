package co.id.roni.film_submission.service;

import co.id.roni.film_submission.BuildConfig;
import co.id.roni.film_submission.model.detailmodel.MovieDetailModel;
import co.id.roni.film_submission.model.detailmodel.TVShowDetailModel;
import co.id.roni.film_submission.objectdata.CreditObjectData;
import co.id.roni.film_submission.objectdata.MovieObjectData;
import co.id.roni.film_submission.objectdata.TvShowsObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://api.themoviedb.org/";


    @GET("3/discover/movie?api_key=" + BuildConfig.API_KEY + "&sort_by=popularity.desc&include_adult=false&include_video=false")
    Call<MovieObjectData> getMovieList(@Query("id") int Id, @Query("language") String language);

    @GET("3/discover/tv?api_key=" + BuildConfig.API_KEY + "&sort_by=popularity.desc&include_adult=false&include_video=false")
    Call<TvShowsObject> getTVList(@Query("id") int Id, @Query("language") String language);

    @GET("/3/movie/{movie_id}?api_key=" + BuildConfig.API_KEY)
    Call<MovieDetailModel> getMovieDetail(@Path("movie_id") int Id, @Query("language") String language);

    @GET("/3/tv/{tv_id}?api_key=" + BuildConfig.API_KEY)
    Call<TVShowDetailModel> getTvShowDetail(@Path("tv_id") int Id, @Query("language") String language);

    @GET("/3/movie/{movie_id}/credits?api_key=" + BuildConfig.API_KEY)
    Call<CreditObjectData> getCastMovieList(@Path("movie_id") int Id, @Query("api") String api);

    @GET("/3/tv/{tv_id}/credits?api_key=" + BuildConfig.API_KEY)
    Call<CreditObjectData> getCastTvList(@Path("tv_id") int Id, @Query("api") String api);

}
