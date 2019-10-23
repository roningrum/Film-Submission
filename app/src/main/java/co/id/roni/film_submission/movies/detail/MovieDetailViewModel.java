package co.id.roni.film_submission.movies.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.TimeUnit;

import co.id.roni.film_submission.model.detailmodel.MovieDetailModel;
import co.id.roni.film_submission.service.Api;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailViewModel extends ViewModel {
    private MutableLiveData<MovieDetailModel> movieDetails = new MutableLiveData<>();

    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

    private Api api = retrofit.create(Api.class);

    void setDetailMovies(int id, String language) {
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
    LiveData<MovieDetailModel> getMovieDetail() {
        return movieDetails;
    }
}
