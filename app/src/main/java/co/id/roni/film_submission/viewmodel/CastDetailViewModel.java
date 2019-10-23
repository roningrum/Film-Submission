package co.id.roni.film_submission.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import co.id.roni.film_submission.model.Cast;
import co.id.roni.film_submission.objectdata.CreditObjectData;
import co.id.roni.film_submission.service.Api;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CastDetailViewModel extends ViewModel {
    public MutableLiveData<List<Cast>> castCreditMovieList = new MutableLiveData<>();

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

    public LiveData<List<Cast>> getCastCreditMovies() {
        return castCreditMovieList;
    }

    public void setCastCreditMovies(int id, String apiKey) {
        Call<CreditObjectData> castCreditCall = api.getCastMovieList(id, apiKey);
        castCreditCall.enqueue(new Callback<CreditObjectData>() {
            @Override
            public void onResponse(Call<CreditObjectData> call, Response<CreditObjectData> response) {
                if (response.isSuccessful()) {
                    castCreditMovieList.setValue(response.body().getCasts());
                }

            }

            @Override
            public void onFailure(Call<CreditObjectData> call, Throwable t) {
                Log.w("Response Failed", "Show Error" + t.getMessage());
            }
        });
    }
}
