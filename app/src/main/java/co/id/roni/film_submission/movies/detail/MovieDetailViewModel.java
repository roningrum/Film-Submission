package co.id.roni.film_submission.movies.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.id.roni.film_submission.model.detailmodel.MovieDetailModel;
import co.id.roni.film_submission.service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailViewModel extends ViewModel {
    private MutableLiveData<MovieDetailModel> movieDetails = new MutableLiveData<>();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Api api = retrofit.create(Api.class);

    void setDetailMovies(int id, String language) {
        Call<MovieDetailModel> movieModelCall = api.getMovieDetail(id, language);
        movieModelCall.enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(Call<MovieDetailModel> call, Response<MovieDetailModel> response) {
                movieDetails.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetailModel> call, Throwable t) {
                Log.w("Response Detail Failed", "Show message" + t.getMessage());
            }
        });
    }

    public LiveData<MovieDetailModel> getMovieDetail1(int id, String language) {
        if (movieDetails == null) {
            setDetailMovies(id, language);
        }
        return movieDetails;
    }
    LiveData<MovieDetailModel> getMovieDetail() {
        return movieDetails;
    }
}
