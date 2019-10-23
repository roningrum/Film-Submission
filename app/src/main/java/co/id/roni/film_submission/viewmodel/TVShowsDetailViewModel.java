package co.id.roni.film_submission.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.id.roni.film_submission.model.detailmodel.TVShowDetailModel;
import co.id.roni.film_submission.service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TVShowsDetailViewModel extends ViewModel {
    private MutableLiveData<TVShowDetailModel> tvDetails = new MutableLiveData<>();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Api api = retrofit.create(Api.class);

    public void setDetailTvShows(int id, String language) {
        Call<TVShowDetailModel> tvShowModelCall = api.getTvShowDetail(id, language);
        tvShowModelCall.enqueue(new Callback<TVShowDetailModel>() {
            @Override
            public void onResponse(Call<TVShowDetailModel> call, Response<TVShowDetailModel> response) {
                tvDetails.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TVShowDetailModel> call, Throwable t) {
                Log.w("Response Detail Failed", "Show message" + t.getMessage());
            }
        });
    }

    public LiveData<TVShowDetailModel> getTvShowsDetail() {
        return tvDetails;
    }
}
