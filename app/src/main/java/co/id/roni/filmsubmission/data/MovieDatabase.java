package co.id.roni.filmsubmission.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import co.id.roni.filmsubmission.model.favorite.MovieFavModel;
import co.id.roni.filmsubmission.model.favorite.TVShowFavModel;

@Database(entities = {MovieFavModel.class, TVShowFavModel.class}, version = 4)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
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

//    }
}
