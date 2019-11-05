package co.id.roni.filmsubmission.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.id.roni.filmsubmission.model.detailmodel.TVShowDetailModel;
import co.id.roni.filmsubmission.service.Api;
import co.id.roni.filmsubmission.service.ApiConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowsDetailViewModel extends ViewModel {
    private MutableLiveData<TVShowDetailModel> tvDetails = new MutableLiveData<>();

    private Api api = ApiConstant.getRetrofit().create(Api.class);

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
