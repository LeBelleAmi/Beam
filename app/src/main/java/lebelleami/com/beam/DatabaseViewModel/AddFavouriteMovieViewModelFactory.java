package lebelleami.com.beam.DatabaseViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import lebelleami.com.beam.Database.AppDatabase;

public class AddFavouriteMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase appDatabase;
    private final int movieId;

    public AddFavouriteMovieViewModelFactory(AppDatabase database, int mMovieId){
        appDatabase = database;
        movieId = mMovieId;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        //no inspection unchecked
        return (T) new AddFavouriteMovieViewModel(appDatabase, movieId);
    }
}
