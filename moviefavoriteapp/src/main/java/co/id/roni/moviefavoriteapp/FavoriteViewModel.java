package co.id.roni.moviefavoriteapp;

import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import co.id.roni.moviefavoriteapp.model.MovieModel;
import co.id.roni.moviefavoriteapp.model.TVShowFavModel;

public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<List<MovieModel>> listMovieFavs = new MutableLiveData<>();
    private MutableLiveData<List<TVShowFavModel>> listTVFavs = new MutableLiveData<>();
    private List<MovieModel> favMovies = new ArrayList<>();
    private List<TVShowFavModel> favTvs = new ArrayList<>();

    public MutableLiveData<List<MovieModel>> getListMovieFavs() {
        return listMovieFavs;
    }

    public void setListMovieFavs(Cursor cursorMovie) {
        if (cursorMovie != null && cursorMovie.moveToFirst()) {
            do {
                MovieModel movieModel = new MovieModel(cursorMovie);
                favMovies.add(movieModel);
            } while (cursorMovie.moveToNext());
            cursorMovie.close();
            listMovieFavs.postValue(favMovies);

            Log.d("Check", "Data:" + favMovies + "");
        }
    }

    public MutableLiveData<List<TVShowFavModel>> getListTVFavs() {
        return listTVFavs;
    }

    public void setListTVFavs(Cursor cursorTV) {
        if (cursorTV != null && cursorTV.moveToFirst()) {
            do {
                TVShowFavModel tvShowFavModel = new TVShowFavModel(cursorTV);
                favTvs.add(tvShowFavModel);
            } while (cursorTV.moveToNext());
            cursorTV.close();
            listTVFavs.postValue(favTvs);

            Log.d("Check", "Data:" + favTvs + "");
        }
    }
}
