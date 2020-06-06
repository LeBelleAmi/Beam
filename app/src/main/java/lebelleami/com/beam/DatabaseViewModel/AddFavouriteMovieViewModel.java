package lebelleami.com.beam.DatabaseViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import lebelleami.com.beam.Database.AppDatabase;
import lebelleami.com.beam.Database.MovieEntry;

public class AddFavouriteMovieViewModel extends ViewModel {

    private LiveData<MovieEntry> movieEntry;

    public AddFavouriteMovieViewModel(AppDatabase database, int movieId){
        movieEntry = database.movieDao().loadFavouriteMovieById(movieId);
    }

    public LiveData<MovieEntry> getMovies(){
        return movieEntry;
    }
}

