package co.id.roni.film_submission.favorite.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import co.id.roni.film_submission.favorite.MovieFavModel;

@Database(entities = {MovieFavModel.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    private static volatile MovieDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    public static MovieDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "movie_db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract MovieDao movieDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final MovieDao movieDao;

        PopulateDbAsync(MovieDatabase instance) {
            movieDao = instance.movieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAll();
            MovieFavModel movieFavModel = new MovieFavModel();
            movieDao.insert(movieFavModel);
//            movieDao.isFavorite(movieFavModel.getId());
            return null;
        }
    }

}
