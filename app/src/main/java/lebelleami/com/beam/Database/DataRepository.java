package lebelleami.com.beam.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataRepository {
    private final MovieDao mDao;
    private final ExecutorService mIoExecutor;
    private static volatile DataRepository sInstance = null;

    public static DataRepository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    AppDatabase database = AppDatabase.getsINSTANCE(application);
                    sInstance = new DataRepository(database.movieDao(),
                            Executors.newSingleThreadExecutor());
                }
            }
        }
        return sInstance;
    }

    public DataRepository(MovieDao dao, ExecutorService executor) {
        mIoExecutor = executor;
        mDao = dao;
    }

    /*public DataSource.Factory<Integer, MovieEntry> getMovies() {
        try {
            return mDao.getAllMovies();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }*/

    public LiveData<List<MovieEntry>> getFavourites() {
        try {
            return mDao.getAllFavouriteMovies();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<MovieEntry> getAllFavourites( String title) {
        try {
            return mDao.loadAll(title);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void saveFavourite (final MovieEntry movieEntry) {
        mIoExecutor.execute(() -> mDao.insertFavouriteMovie(movieEntry));
    }

    public void deleteFavourite (final MovieEntry movieEntry) {
        mIoExecutor.execute(() -> mDao.deleteFavouriteMovie(movieEntry));
    }

    public void deleteFavouriteById (int movieEntry) {
        mIoExecutor.execute(() -> mDao.deleteFavouriteMovieById(movieEntry));
    }

}
