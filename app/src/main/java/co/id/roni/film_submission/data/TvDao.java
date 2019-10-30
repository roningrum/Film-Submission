package co.id.roni.film_submission.data;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import co.id.roni.film_submission.model.favorite.MovieFavModel;
import co.id.roni.film_submission.model.favorite.TVShowFavModel;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TvDao {

    @Insert(onConflict = REPLACE)
    void insert(TVShowFavModel tvShowFavModel);

    @Insert(onConflict = REPLACE)
    long insertMovieToCursor(MovieFavModel movieFavModel);

    @Query("SELECT * FROM tbTVShowFav ORDER BY id DESC")
    LiveData<List<TVShowFavModel>> getAllTvFavs();

    @Query("SELECT * FROM tbTVShowFav WHERE id = :id")
    LiveData<TVShowFavModel> selectByIdTv(int id);

    @Query("DELETE FROM tbTVShowFav WHERE id = :id")
    void deleteFavorite(int id);

    @Query("SELECT * FROM tbMovieFav")
    Cursor getTvFavsAll();

}
