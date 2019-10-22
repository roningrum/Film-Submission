package co.id.roni.film_submission.favorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import co.id.roni.film_submission.favorite.data.MovieDao;
import co.id.roni.film_submission.favorite.data.MovieDatabase;
import co.id.roni.film_submission.favorite.movie.MovieFavModel;

public class FavoriteRepository {
    private MovieDao movieDao;
    private LiveData<List<MovieFavModel>> allMovieFavs;

    public FavoriteRepository(Application application) {
        MovieDatabase database = MovieDatabase.getDatabase(application);
        movieDao = database.movieDao();
        allMovieFavs = movieDao.getAllMovieFavs();
    }

    public void insert(MovieFavModel movieFavModel) {
        new InsertMovieFavsAsyncTask(movieDao).execute(movieFavModel);
    }

    public void delete(int movieId) {
        try {
            new DeleteMovieFavsAsyncTask(movieDao).execute(movieId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<MovieFavModel> selectMovieAsFav(int movieId) {
        LiveData<MovieFavModel> movieFavModel = new MutableLiveData<>();
        try {
            movieFavModel = new SelectMovieFavAsycTask(movieDao).execute(movieId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return movieFavModel;
    }

    public LiveData<List<MovieFavModel>> getAllMovieFavs() {
        return allMovieFavs;
    }


    private class InsertMovieFavsAsyncTask extends AsyncTask<MovieFavModel, Void, Void> {
        private MovieDao movieDao;

        InsertMovieFavsAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MovieFavModel... movieFavModels) {
            movieDao.insert(movieFavModels[0]);
            return null;
        }
    }


    private class SelectMovieFavAsycTask extends AsyncTask<Integer, Void, LiveData<MovieFavModel>> {
        private MovieDao movieDao;

        SelectMovieFavAsycTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected LiveData<MovieFavModel> doInBackground(Integer... integers) {
            return movieDao.selectByIdMovie(integers[0]);
        }
    }

    private class DeleteMovieFavsAsyncTask extends AsyncTask<Integer, Void, Void> {
        private MovieDao movieDao;

        public DeleteMovieFavsAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;

        }

        @Override
        protected Void doInBackground(Integer... integers) {
            movieDao.deleteFavorite(integers[0]);
            return null;
        }
    }
}
