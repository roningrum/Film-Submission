package co.id.roni.film_submission.favorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import co.id.roni.film_submission.favorite.data.MovieDao;
import co.id.roni.film_submission.favorite.data.MovieDatabase;

class FavoriteMovieRepository {
    private MovieDao movieDao;
    private LiveData<List<MovieFavModel>> allMovieFavs;

    FavoriteMovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        movieDao = db.movieDao();
        allMovieFavs = movieDao.getAllMovieFavs();
    }

    LiveData<List<MovieFavModel>> getAllMovieFavs() {
        return allMovieFavs;
    }

    void insert(MovieFavModel movieFavModel) {
        new insertAsyncTask(movieDao).execute(movieFavModel);
    }


    private static class insertAsyncTask extends AsyncTask<MovieFavModel, Void, Void> {

        private MovieDao movieAsyncTaskDao;

        insertAsyncTask(MovieDao movieDao) {
            movieAsyncTaskDao = movieDao;
        }

        @Override
        protected Void doInBackground(MovieFavModel... movieFavModels) {
            movieAsyncTaskDao.insert(movieFavModels[0]);
            return null;
        }
    }
}
