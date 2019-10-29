package co.id.roni.film_submission.favorite;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import co.id.roni.film_submission.data.MovieDao;
import co.id.roni.film_submission.data.MovieDatabase;

public class FavoriteProviderRepository {
    private MovieDao movieDao;
    private Cursor favoriteCursor;

    public FavoriteProviderRepository(Context context) {
        MovieDatabase database = MovieDatabase.getDatabase(context);
        movieDao = database.movieDao();
        favoriteCursor = movieDao.getMovieFavsAll();
    }

    public Cursor getFavoriteCursor() {
        try {
            new SelectMovieCursor(movieDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return favoriteCursor;
    }

    private static class SelectMovieCursor extends AsyncTask<Void, Void, Cursor> {
        private MovieDao movieDao;

        public SelectMovieCursor(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return movieDao.getMovieFavsAll();
        }
    }
}
