package co.id.roni.moviefavoriteapp;

import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieFavoriteViewModel extends ViewModel {
    private MutableLiveData<List<MovieModel>> listMovieFavs = new MutableLiveData<>();
    private List<MovieModel> favMovies = new ArrayList<>();

    public MutableLiveData<List<MovieModel>> getListMovieFavs() {
        return listMovieFavs;
    }

    void setListMovieFavs(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            do {
                MovieModel movieModel = new MovieModel(cursor);
                favMovies.add(movieModel);
            } while (cursor.moveToNext());
            cursor.close();
            listMovieFavs.postValue(favMovies);

            Log.d("Check", "Data:" + favMovies + "");
        }
    }
}
