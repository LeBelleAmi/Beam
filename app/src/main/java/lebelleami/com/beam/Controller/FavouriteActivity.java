package lebelleami.com.beam.Controller;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lebelleami.com.beam.Database.MovieEntry;
import lebelleami.com.beam.DatabaseViewModel.FavouriteMoviesViewModel;
import lebelleami.com.beam.DatabaseViewModel.FavouriteMoviesViewModelFactory;
import lebelleami.com.beam.R;
import lebelleami.com.beam.View.FavouriteMovieAdapter;

public class FavouriteActivity extends AppCompatActivity {

    private FavouriteMoviesViewModel favouriteMoviesViewModel;

    TextView emptyState;

    private RecyclerView recyclerView;
    /*recycler view layout manager*/
    LinearLayoutManager llm;
    private FavouriteMovieAdapter favouriteMovieAdapter;
    List<MovieEntry> favMovieEntryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FavouriteMoviesViewModelFactory favouriteMoviesViewModelFactory = FavouriteMoviesViewModelFactory.createFactory(this);
        favouriteMoviesViewModel = ViewModelProviders.of(this, favouriteMoviesViewModelFactory).get(FavouriteMoviesViewModel.class);

        setContentView(R.layout.activity_favourite);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbarFav);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favourites");

        emptyState = findViewById(R.id.empty_state);

        favMovieEntryList = new ArrayList<>();

        initViews();
    }

    private void initViews() {
        // set up recycler view
        recyclerView = findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        favouriteMovieAdapter = new FavouriteMovieAdapter(getApplicationContext(), favMovieEntryList);

        favouriteMoviesViewModel.getFavMovies().observe(this, favouriteMovieAdapter::setMovieEntries);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(favouriteMovieAdapter);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}