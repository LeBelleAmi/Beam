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

    @Query("SELECT * FROM favourite_movies ORDER BY title")
    LiveData<List<MovieEntry>> getAllFavouriteMovies();

    @Query("SELECT * FROM favourite_movies WHERE title = :title")
    List<MovieEntry> loadAll(String title);

    @Insert
    void insertFavouriteMovie(MovieEntry movieEntry);

    @Delete
    void deleteFavouriteMovie(MovieEntry movieEntry);

    @Query("DELETE FROM favourite_movies WHERE movieid = :movieId")
    void deleteFavouriteMovieById(int movieId);
}

