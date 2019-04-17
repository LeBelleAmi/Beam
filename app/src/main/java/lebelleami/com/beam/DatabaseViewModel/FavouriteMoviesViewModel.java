package lebelleami.com.beam.DatabaseViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import lebelleami.com.beam.Database.AppDatabase;
import lebelleami.com.beam.Database.MovieEntity;

public class FavouriteMoviesViewModel extends AndroidViewModel{

    private AppDatabase appDatabase;

    private LiveData<List<MovieEntity>> favouritemovies;
    private LiveData<List<MovieEntity>> favouritemovie;

    public FavouriteMoviesViewModel (@NonNull Application application) {
        super(application);

        favouritemovies = appDatabase.movieDao().getAllFavouriteMovies();
    }


    public LiveData<List<MovieEntity>> getMovies() {
        return favouritemovies;
    }

    public LiveData<List<MovieEntity>> getFavouriteMovie(int mMovieId){
        favouritemovie = appDatabase.movieDao().loadFavouriteMovieById(mMovieId);
        return favouritemovie;
    }

}
