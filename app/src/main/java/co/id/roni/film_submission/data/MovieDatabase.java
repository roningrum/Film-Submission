package co.id.roni.film_submission.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import co.id.roni.film_submission.model.favorite.MovieFavModel;

@Database(entities = {MovieFavModel.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();

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

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieDao movieDao;

        public PopulateDbAsyncTask(MovieDatabase db) {
            movieDao = db.movieDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.insert(new MovieFavModel());
            return null;
        }
    }
}
