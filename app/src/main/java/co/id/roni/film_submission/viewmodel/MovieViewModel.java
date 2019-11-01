package co.id.roni.film_submission.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import co.id.roni.film_submission.model.MovieModel;
import co.id.roni.film_submission.objectdata.MovieObjectData;
import co.id.roni.film_submission.service.Api;
import co.id.roni.film_submission.service.ApiConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<MovieModel>> listMovies = new MutableLiveData<>();
    private Api api = ApiConstant.getRetrofit().create(Api.class);

    public void setListMovies(int page, String language) {

        Call<MovieObjectData> movieObjectCall = api.getMovieList(page, language);
        movieObjectCall.enqueue(new Callback<MovieObjectData>() {
            @Override
            public void onResponse(Call<MovieObjectData> call, Response<MovieObjectData> response) {
                if (response.isSuccessful()) {
                    listMovies.setValue(response.body().getResults());
                }

            }

            @Override
            public void onFailure(Call<MovieObjectData> call, Throwable t) {
                Log.w("Response Failed", "" + t.getMessage());
            }
        });
    }

    public void setListSearchMovieResult(String apiKey, String query, String language) {
        Call<MovieObjectData> movieSearchObjectCall = api.getMovieSearchResult(apiKey, query, language);
        movieSearchObjectCall.enqueue(new Callback<MovieObjectData>() {
            @Override
            public void onResponse(Call<MovieObjectData> call, Response<MovieObjectData> response) {
                if (response.isSuccessful()) {
                    listMovies.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieObjectData> call, Throwable t) {
                Log.w("Response Failed", "" + t.getMessage());
            }
        });
    }
    public LiveData<List<MovieModel>> getListMovies() {
        return listMovies;
    }
}
