package co.id.roni.film_submission.favorite.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import co.id.roni.film_submission.favorite.movie.MovieFavModel;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {
    @Insert(onConflict = REPLACE)
    void insert(MovieFavModel movieFavModel);

    @Query("SELECT * FROM tbMovieFav ORDER BY id DESC")
    LiveData<List<MovieFavModel>> getAllMovieFavs();

    @Query("SELECT * FROM tbMovieFav WHERE id = :id")
    LiveData<MovieFavModel> selectByIdMovie(int id);

    @Query("DELETE FROM tbMovieFav WHERE id = :id")
    void deleteFavorite(int id);

    @Query("DELETE FROM tbMovieFav")
    void deleteAllFavorite();
}
