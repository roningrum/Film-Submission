package co.id.roni.film_submission.tvshows;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import co.id.roni.film_submission.model.TvShowModel;
import co.id.roni.film_submission.objectdata.TvShowsObject;
import co.id.roni.film_submission.service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowsViewModel extends ViewModel {
    private MutableLiveData<List<TvShowModel>> listTvs = new MutableLiveData<>();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Api api = retrofit.create(Api.class);

    void setListTVs(int page, String language) {

        Call<TvShowsObject> tvObjectCall = api.getTVList(page, language);
        tvObjectCall.enqueue(new Callback<TvShowsObject>() {
            @Override
            public void onResponse(Call<TvShowsObject> call, Response<TvShowsObject> response) {
                if (response.isSuccessful()) {
                    listTvs.setValue(response.body().getResults());
                }

            }

            @Override
            public void onFailure(Call<TvShowsObject> call, Throwable t) {
                Log.w("Response Failed", "" + t.getMessage());
            }
        });
    }


    LiveData<List<TvShowModel>> getListTvs() {
        return listTvs;
    }
}
