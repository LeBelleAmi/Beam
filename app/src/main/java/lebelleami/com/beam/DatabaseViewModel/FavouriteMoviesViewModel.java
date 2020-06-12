package lebelleami.com.beam.DatabaseViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import lebelleami.com.beam.Database.AppDatabase;
import lebelleami.com.beam.Database.DataRepository;
import lebelleami.com.beam.Database.MovieEntry;

public class FavouriteMoviesViewModel extends ViewModel {

    private DataRepository mRepository;

    public FavouriteMoviesViewModel (DataRepository repository) {
        mRepository = repository;
    }


    public void saveFavMovie(int movie_id, double movieVoteAverage, double moviePopularity, String movieTitle, String moviePoster,
                             String movieLanguage, String movieBackdrop, String movieOverview, String movieReleaseDate) {

        MovieEntry movieEntry = new MovieEntry(movie_id, movieVoteAverage, moviePopularity, movieTitle, moviePoster,
                movieLanguage, movieBackdrop, movieOverview, movieReleaseDate);
        save(movieEntry);
    }

    public void save(MovieEntry movieEntry) {
        mRepository.saveFavourite(movieEntry);
    }

    public void deleteById(int movieEntry) {
        mRepository.deleteFavouriteById(movieEntry);
    }

    public void delete(MovieEntry movieEntry) {
        mRepository.deleteFavourite(movieEntry);
    }

    /**
     * @return a live data of updated favourite movies.
     */
    public LiveData<List<MovieEntry>> getFavMovies() {
        return mRepository.getFavourites();
    }

    /**
     * @return a list data of added favourite movies.
     */
    public List<MovieEntry> getListOfFavMovies(String title) {
        return mRepository.getAllFavourites(title);
    }


}