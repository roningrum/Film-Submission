package co.id.roni.film_submission.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import co.id.roni.film_submission.model.TVShowModel;
import co.id.roni.film_submission.objectdata.TvShowsObject;
import co.id.roni.film_submission.service.Api;
import co.id.roni.film_submission.service.ApiConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowsViewModel extends ViewModel {
    private MutableLiveData<List<TVShowModel>> listTvs = new MutableLiveData<>();
    private Api api = ApiConstant.getRetrofit().create(Api.class);

    public void setListTVs(int page, String language) {
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

    public void setListSearchTvResult(String apiKey, String query, String language) {
        Call<TvShowsObject> tvSearchObjectCall = api.getTVSearchResult(apiKey, query, language);
        tvSearchObjectCall.enqueue(new Callback<TvShowsObject>() {
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
    public LiveData<List<TVShowModel>> getListTvs() {
        return listTvs;
    }
}
