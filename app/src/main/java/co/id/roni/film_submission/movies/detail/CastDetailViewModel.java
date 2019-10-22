package co.id.roni.film_submission.movies.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import co.id.roni.film_submission.model.Cast;
import co.id.roni.film_submission.objectdata.CreditObjectData;
import co.id.roni.film_submission.service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CastDetailViewModel extends ViewModel {
    private MutableLiveData<List<Cast>> castCreditMovieList = new MutableLiveData<>();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Api api = retrofit.create(Api.class);

    LiveData<List<Cast>> getCastCreditMovies() {
        return castCreditMovieList;
    }

    void setCastCreditMovies(int id) {
        Call<CreditObjectData> castCreditCall = api.getCastMovieList(id);
        castCreditCall.enqueue(new Callback<CreditObjectData>() {
            @Override
            public void onResponse(Call<CreditObjectData> call, Response<CreditObjectData> response) {
                if (response.isSuccessful()) {
                    castCreditMovieList.setValue(response.body().getCasts());
                }

            }

            @Override
            public void onFailure(Call<CreditObjectData> call, Throwable t) {
                Log.w("Response Failed", "" + t.getMessage());
            }
        });
    }
}
