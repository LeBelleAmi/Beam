package lebelleami.com.beam.Database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao

public interface MovieDao {

    @Query("SELECT * FROM favourite_movies ORDER BY id")
    LiveData<List<MovieEntity>> getAllFavouriteMovies();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavouriteMovie(MovieEntity movieEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavouriteMovie(MovieEntity movieEntity);

    @Delete
    void deleteFavouriteMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM favourite_movies WHERE id = :movieId")
    LiveData<List<MovieEntity>> loadFavouriteMovieById(int movieId);

    @Query("DELETE FROM favourite_movies WHERE id = :movieId")
    void deleteFavouriteMovieById(int movieId);
}
