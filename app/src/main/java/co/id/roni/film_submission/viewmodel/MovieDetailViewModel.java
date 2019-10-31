package co.id.roni.film_submission.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.id.roni.film_submission.model.detailmodel.MovieDetailModel;
import co.id.roni.film_submission.service.Api;
import co.id.roni.film_submission.service.ApiConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailViewModel extends ViewModel {
    private MutableLiveData<MovieDetailModel> movieDetails = new MutableLiveData<>();

    private Api api = ApiConstant.getRetrofit().create(Api.class);

    public void setDetailMovies(int id, String language) {
        Call<MovieDetailModel> movieModelCall = api.getMovieDetail(id, language);
        movieModelCall.enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(Call<MovieDetailModel> call, Response<MovieDetailModel> response) {
                movieDetails.setValue(response.body());
                Log.d("Response sukses", "Show Data");
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

    public LiveData<MovieDetailModel> getMovieDetail() {
        return movieDetails;
    }
}
