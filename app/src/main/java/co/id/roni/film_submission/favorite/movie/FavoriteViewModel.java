package co.id.roni.film_submission.favorite.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import co.id.roni.film_submission.favorite.FavoriteRepository;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository favoriteRepository;
    private LiveData<List<MovieFavModel>> movieLivesData;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
        movieLivesData = favoriteRepository.getAllMovieFavs();
    }

    public void insert(MovieFavModel movieFavModel) {
        favoriteRepository.insert(movieFavModel);
    }


    public MovieFavModel selectMovieFav(int movieId) {
        return favoriteRepository.selectMovieAsFav(movieId);
    }

    public void delete(int movieId) {
        favoriteRepository.delete(movieId);
    }

    public LiveData<List<MovieFavModel>> getMovieLivesData() {
        if (movieLivesData == null) {
            movieLivesData = favoriteRepository.getAllMovieFavs();
        }
        return movieLivesData;
    }
}
