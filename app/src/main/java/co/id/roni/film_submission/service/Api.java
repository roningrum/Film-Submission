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
    String BASE_URL = "https://api.themoviedb.org/3/";


    @GET("discover/movie?api_key=" + BuildConfig.API_KEY)
    Call<MovieObjectData> getMovieList(@Query("id") int Id, @Query("language") String language);

    @GET("discover/tv?api_key=" + BuildConfig.API_KEY)
    Call<TvShowsObject> getTVList(@Query("id") int Id, @Query("language") String language);

    @GET("movie/{movie_id}?api_key=" + BuildConfig.API_KEY)
    Call<MovieDetailModel> getMovieDetail(@Path("movie_id") int Id, @Query("language") String language);

    @GET("tv/{tv_id}?api_key=" + BuildConfig.API_KEY)
    Call<TVShowDetailModel> getTvShowDetail(@Path("tv_id") int Id, @Query("language") String language);

    @GET("movie/{movie_id}/credits?api_key=" + BuildConfig.API_KEY)
    Call<CreditObjectData> getCastMovieList(@Path("movie_id") int Id, @Query("api") String api);

    @GET("tv/{tv_id}/credits?api_key=" + BuildConfig.API_KEY)
    Call<CreditObjectData> getCastTvList(@Path("tv_id") int Id, @Query("api") String api);

    @GET("search/movie?api_key=" + BuildConfig.API_KEY)
    Call<MovieObjectData> getMovieSearchResult(@Query("api_key") String api, @Query("query") String query, @Query("language") String language);

    @GET("search/tv?api_key=" + BuildConfig.API_KEY)
    Call<TvShowsObject> getTVSearchResult(@Query("api_key") String api, @Query("query") String query, @Query("language") String language);

    @GET("discover/movie?api_key=" + BuildConfig.API_KEY)
    Call<MovieObjectData> getMovieRelaase(@Query("api_key") String api, @Query("primary_release_date.gte") String currentdate, @Query("primary_release_date.lte") String date);



}
