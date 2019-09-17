package co.id.roni.film_submission.tvshows;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.id.roni.film_submission.model.TvShowDetailModel;
import co.id.roni.film_submission.service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowsDetailViewModel extends ViewModel {
    private MutableLiveData<TvShowDetailModel> tvDetails = new MutableLiveData<>();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Api api = retrofit.create(Api.class);

    void setDetailTvShows(int id, String language) {
        Call<TvShowDetailModel> tvShowModelCall = api.getTvShowDetail(id, language);
        tvShowModelCall.enqueue(new Callback<TvShowDetailModel>() {
            @Override
            public void onResponse(Call<TvShowDetailModel> call, Response<TvShowDetailModel> response) {
                tvDetails.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TvShowDetailModel> call, Throwable t) {
                Log.w("Response Detail Failed", "Show message" + t.getMessage());
            }
        });
    }

    LiveData<TvShowDetailModel> getTvShowsDetail() {
        return tvDetails;
    }
}
