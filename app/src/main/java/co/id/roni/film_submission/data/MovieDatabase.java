package co.id.roni.film_submission.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import co.id.roni.film_submission.model.favorite.MovieFavModel;
import co.id.roni.film_submission.model.favorite.TVShowFavModel;

@Database(entities = {MovieFavModel.class, TVShowFavModel.class}, version = 4)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static MovieDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (MovieDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "movie_db")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
        }
        return instance;
    }

    public abstract MovieDao movieDao();

    public abstract TvDao tvDao();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieDao movieDao;
        private TvDao tvDao;

        PopulateDbAsyncTask(MovieDatabase db) {
            movieDao = db.movieDao();
            tvDao = db.tvDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.insert(new MovieFavModel());
            tvDao.insert(new TVShowFavModel());
            return null;
        }
    }
}
