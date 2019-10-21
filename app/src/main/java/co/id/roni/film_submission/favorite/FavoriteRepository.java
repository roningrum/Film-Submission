package co.id.roni.film_submission.favorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

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

    public MovieFavModel selectMovieAsFav(int movieId) {
        MovieFavModel movieFavModel = null;
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

        public InsertMovieFavsAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MovieFavModel... movieFavModels) {
            movieDao.insert(movieFavModels[0]);
            return null;
        }
    }


    private class SelectMovieFavAsycTask extends AsyncTask<Integer, Void, MovieFavModel> {
        private MovieDao movieDao;

        public SelectMovieFavAsycTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected MovieFavModel doInBackground(Integer... integers) {
            return movieDao.selectByIdMovie(integers[0]);
        }
    }
}
