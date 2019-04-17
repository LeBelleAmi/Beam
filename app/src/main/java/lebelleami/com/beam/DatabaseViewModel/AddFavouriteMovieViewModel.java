package lebelleami.com.beam.DatabaseViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import lebelleami.com.beam.Database.AppDatabase;
import lebelleami.com.beam.Database.MovieEntity;

public class AddFavouriteMovieViewModel extends ViewModel {

    private LiveData<List<MovieEntity>> movieEntity;

    public AddFavouriteMovieViewModel(AppDatabase database, int movieId){
        movieEntity = database.movieDao().loadFavouriteMovieById(movieId);
    }

    public LiveData<List<MovieEntity>> getMovies(){
        return movieEntity;
    }
}
