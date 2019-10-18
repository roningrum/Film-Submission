package co.id.roni.film_submission.favorite.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import co.id.roni.film_submission.favorite.movie.MovieFavModel;

@Database(entities = {MovieFavModel.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public static MovieDatabase instance = null;
    public static MovieDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (MovieDatabase.class) {
                instance = Room.databaseBuilder(context, MovieDatabase.class, "movie_db")
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return instance;
    }
}
