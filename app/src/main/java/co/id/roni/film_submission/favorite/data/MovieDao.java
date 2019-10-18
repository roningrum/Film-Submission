package co.id.roni.film_submission.favorite.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.id.roni.film_submission.favorite.movie.MovieFavModel;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {
    @Insert(onConflict = REPLACE)
    void insert(MovieFavModel movieFavModel);

    @Query("SELECT * FROM tbMovieFav")
    LiveData<List<MovieFavModel>> getAllMovieFavs();

//    @Query("SELECT EXISTS (SELECT 1 FROM tbMovieFav WHERE id=id)")
//    int isFavorite(int id);

    @Query("DELETE FROM tbMovieFav")
    void deleteAll();
}
