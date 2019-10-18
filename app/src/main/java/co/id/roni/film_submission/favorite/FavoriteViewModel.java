package co.id.roni.film_submission.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteMovieRepository favoriteMovieRepository;
    private LiveData<List<MovieFavModel>> movieLivesData;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteMovieRepository = new FavoriteMovieRepository(application);
        movieLivesData = favoriteMovieRepository.getAllMovieFavs();
    }

    public LiveData<List<MovieFavModel>> getMovieLivesData() {
        return movieLivesData;
    }

    public void insert(MovieFavModel movies) {
        favoriteMovieRepository.insert(movies);
    }

}
