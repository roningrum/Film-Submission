package co.id.roni.film_submission.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import co.id.roni.film_submission.favorite.FavoriteRepository;
import co.id.roni.film_submission.model.favorite.MovieFavModel;
import co.id.roni.film_submission.model.favorite.TVShowFavModel;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository favoriteRepository;
    private LiveData<List<MovieFavModel>> movieLivesData;
    private LiveData<List<TVShowFavModel>> tvLivesData;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
        movieLivesData = favoriteRepository.getAllMovieFavs();
        tvLivesData = favoriteRepository.getAllTvFavs();
    }

    //Movie Favorite
    public void insert(MovieFavModel movieFavModel) {
        favoriteRepository.insert(movieFavModel);
    }


    public LiveData<MovieFavModel> selectMovieFav(int movieId) {
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

    //TV Favorite
    public void insertTv(TVShowFavModel tvShowFavModel) {
        favoriteRepository.insertTvFav(tvShowFavModel);
    }

    public LiveData<TVShowFavModel> selectTvFav(int tvId) {
        return favoriteRepository.selectTvAsFav(tvId);
    }

    public void deleteTv(int tvId) {
        favoriteRepository.deleteTvFav(tvId);
    }

    public LiveData<List<TVShowFavModel>> getTvLivesData() {
        if (tvLivesData == null) {
            tvLivesData = favoriteRepository.getAllTvFavs();
        }
        return tvLivesData;
    }

}
