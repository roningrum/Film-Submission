package co.id.roni.film_submission;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<MovieModel>> listMovies = new MutableLiveData<>();
    private MutableLiveData<MovieModel> movieDetails = new MutableLiveData<>();


    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Api api = retrofit.create(Api.class);

    void setListMovies( final int page, String language ){

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

//    void setDetailMovies( final int id, String language ){
//        Call<MovieModel> movieModelCall = api.getMovieDetail(id, language);
//        movieModelCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                movieDetails.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//                Log.w("Response Detail Failed", "Show message"+t.getMessage());
//            }
//        });

    LiveData<List<MovieModel>> getListMovies() {
        return listMovies;
    }
//    LiveData<MovieModel> getMovieDetail(){
//        return movieDetails;
//    }
}
