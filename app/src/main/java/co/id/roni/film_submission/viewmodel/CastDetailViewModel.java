package co.id.roni.film_submission.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import co.id.roni.film_submission.model.Cast;
import co.id.roni.film_submission.objectdata.CreditObjectData;
import co.id.roni.film_submission.service.Api;
import co.id.roni.film_submission.service.ApiConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastDetailViewModel extends ViewModel {
    public MutableLiveData<List<Cast>> castCreditMovieList = new MutableLiveData<>();
    public MutableLiveData<List<Cast>> castCreditTvList = new MutableLiveData<>();

    private Api api = ApiConstant.getRetrofit().create(Api.class);

    public LiveData<List<Cast>> getCastCreditMovies() {
        return castCreditMovieList;
    }

    public LiveData<List<Cast>> getCastCreditTvs() {
        return castCreditTvList;
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

    public void setCastCreditTvs(int id, String apiKey) {
        Call<CreditObjectData> castCreditCall = api.getCastTvList(id, apiKey);
        castCreditCall.enqueue(new Callback<CreditObjectData>() {
            @Override
            public void onResponse(Call<CreditObjectData> call, Response<CreditObjectData> response) {
                if (response.isSuccessful()) {
                    castCreditTvList.setValue(response.body().getCasts());
                }

            }

            @Override
            public void onFailure(Call<CreditObjectData> call, Throwable t) {
                Log.w("Response Failed", "Show Error" + t.getMessage());
            }
        });
    }
}
