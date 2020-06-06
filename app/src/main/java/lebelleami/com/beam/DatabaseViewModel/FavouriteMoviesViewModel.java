package lebelleami.com.beam.DatabaseViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import lebelleami.com.beam.Database.AppDatabase;
import lebelleami.com.beam.Database.MovieEntry;

public class FavouriteMoviesViewModel extends AndroidViewModel{

    // Constant for logging
    private static final String TAG = FavouriteMoviesViewModel.class.getSimpleName();
    private AppDatabase appDatabase;

    private LiveData<List<MovieEntry>> favouritemovie;

    public FavouriteMoviesViewModel (@NonNull Application application) {
        super(application);

        favouritemovie = appDatabase.movieDao().getAllFavouriteMovies();
    }


    public LiveData<List<MovieEntry>> getMovies() {
        return favouritemovie;
    }

}