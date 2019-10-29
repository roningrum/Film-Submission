package co.id.roni.film_submission.favorite;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import co.id.roni.film_submission.data.MovieDao;
import co.id.roni.film_submission.data.MovieDatabase;
import co.id.roni.film_submission.data.TvDao;
import co.id.roni.film_submission.model.favorite.MovieFavModel;
import co.id.roni.film_submission.model.favorite.TVShowFavModel;

public class FavoriteRepository {
    private MovieDao movieDao;
    private TvDao tvDao;
    private LiveData<List<MovieFavModel>> allMovieFavs;
    private LiveData<List<TVShowFavModel>> allTvFavs;
    private Cursor favoriteCursor;

    public FavoriteRepository(Application application) {
        MovieDatabase database = MovieDatabase.getDatabase(application);
        movieDao = database.movieDao();
        tvDao = database.tvDao();
        allMovieFavs = movieDao.getAllMovieFavs();
        allTvFavs = tvDao.getAllTvFavs();
    }

    public FavoriteRepository(Context context) {
        MovieDatabase.getDatabase(context);
//        favoriteCursor = movieDao.getMovieFavsAll();
    }

    //Movie Favorite
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

    public Cursor getAllMovieCursor() {
        Cursor favoriteCursor = movieDao.getMovieFavsAll();
        try {
            new SelectMovieFavForCursor(movieDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return favoriteCursor;
    }
    public LiveData<List<MovieFavModel>> getAllMovieFavs() {
        return allMovieFavs;
    }

    public long insertMovieFavoriteCursor(MovieFavModel movieFavModel) {
        new InsertMovieFavCursor(movieDao).execute(movieFavModel);
        return 0;
    }

    //Tv Favorite
    public void insertTvFav(TVShowFavModel tvShowFavModel) {
        new InsertTvFavsAsyncTask(tvDao).execute(tvShowFavModel);
    }

    public void deleteTvFav(int tvId) {
        try {
            new DeleteTvFavsAsyncTask(tvDao).execute(tvId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<TVShowFavModel> selectTvAsFav(int tvId) {
        LiveData<TVShowFavModel> tvFavModel = new MutableLiveData<>();
        try {
            tvFavModel = new SelectTvFavAsyncTask(tvDao).execute(tvId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return tvFavModel;
    }

    public LiveData<List<TVShowFavModel>> getAllTvFavs() {
        return allTvFavs;
    }

    private static class InsertMovieFavsAsyncTask extends AsyncTask<MovieFavModel, Void, Void> {
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

    private static class SelectMovieFavAsycTask extends AsyncTask<Integer, Void, LiveData<MovieFavModel>> {
        private MovieDao movieDao;

        SelectMovieFavAsycTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected LiveData<MovieFavModel> doInBackground(Integer... integers) {
            return movieDao.selectByIdMovie(integers[0]);
        }
    }

    private static class DeleteMovieFavsAsyncTask extends AsyncTask<Integer, Void, Void> {
        private MovieDao movieDao;

        DeleteMovieFavsAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;

        }

        @Override
        protected Void doInBackground(Integer... integers) {
            movieDao.deleteFavorite(integers[0]);
            return null;
        }
    }

    private static class SelectMovieFavForCursor extends AsyncTask<Void, Void, Cursor> {
        private MovieDao movieDao;

        SelectMovieFavForCursor(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return movieDao.getMovieFavsAll();
        }
    }

    private static class InsertMovieFavCursor extends AsyncTask<MovieFavModel, Void, Long> {
        private MovieDao movieDao;

        InsertMovieFavCursor(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Long doInBackground(MovieFavModel... movieFavModels) {
            return movieDao.insertMovieToCursor(movieFavModels[0]);
        }
    }
    //tambah, hapus, menampilkan data tv favorit ke TvFragment
    private static class InsertTvFavsAsyncTask extends AsyncTask<TVShowFavModel, Void, Void> {
        private TvDao tvDao;

        InsertTvFavsAsyncTask(TvDao tvDao) {
            this.tvDao = tvDao;
        }

        @Override
        protected Void doInBackground(TVShowFavModel... tvShowFavModels) {
            tvDao.insert(tvShowFavModels[0]);
            return null;
        }
    }

    private static class DeleteTvFavsAsyncTask extends AsyncTask<Integer, Void, Void> {
        private TvDao tvDao;

        DeleteTvFavsAsyncTask(TvDao tvDao) {
            this.tvDao = tvDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            tvDao.deleteFavorite(integers[0]);
            return null;
        }
    }

    private static class SelectTvFavAsyncTask extends AsyncTask<Integer, Void, LiveData<TVShowFavModel>> {
        private TvDao tvDao;

        SelectTvFavAsyncTask(TvDao tvDao) {
            this.tvDao = tvDao;
        }

        @Override
        protected LiveData<TVShowFavModel> doInBackground(Integer... integers) {
            return tvDao.selectByIdTv(integers[0]);
        }
    }

}
