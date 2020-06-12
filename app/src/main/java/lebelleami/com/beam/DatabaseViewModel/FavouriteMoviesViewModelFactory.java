package lebelleami.com.beam.DatabaseViewModel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.lang.reflect.InvocationTargetException;

import lebelleami.com.beam.Database.DataRepository;

public class FavouriteMoviesViewModelFactory implements ViewModelProvider.Factory {

    private final DataRepository mRepository;

    public static FavouriteMoviesViewModelFactory createFactory(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Not yet attached to Application");
        }
        return new FavouriteMoviesViewModelFactory(DataRepository.getInstance(application));
    }

    private FavouriteMoviesViewModelFactory(DataRepository repository) {
        mRepository = repository;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(DataRepository.class).newInstance(mRepository);
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
