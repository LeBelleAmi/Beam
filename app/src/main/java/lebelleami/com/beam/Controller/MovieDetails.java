package lebelleami.com.beam.Controller;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import lebelleami.com.beam.Api.Client;
import lebelleami.com.beam.Api.Service;
import lebelleami.com.beam.Database.MovieEntry;
import lebelleami.com.beam.DatabaseViewModel.FavouriteMoviesViewModel;
import lebelleami.com.beam.DatabaseViewModel.FavouriteMoviesViewModelFactory;
import lebelleami.com.beam.R;
import lebelleami.com.beam.Utils.GenreHelper;
import lebelleami.com.beam.Utils.Url;
import lebelleami.com.beam.Model.Cast;
import lebelleami.com.beam.View.CastAdapter;
import lebelleami.com.beam.Model.CastData;
import lebelleami.com.beam.Model.Review;
import lebelleami.com.beam.View.ReviewAdapter;
import lebelleami.com.beam.Model.ReviewData;
import lebelleami.com.beam.Model.Trailer;
import lebelleami.com.beam.View.TrailerAdapter;
import lebelleami.com.beam.Model.TrailerData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {

    private FavouriteMoviesViewModel favouriteMoviesViewModel;
    ImageView posterImg;
    KenBurnsView kbv;
    MaterialFavoriteButton materialFavoriteButton;
    TextView movietitle, year, rating, reviews, language, movierating, genre, synopsis;
    CardView posterCard;
    RecyclerView trailerrecyclerView, reviewrecyclerView, castrecyclerView;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private CastAdapter castAdapter;
    private List<TrailerData> trailerList;
    private List<ReviewData> reviewList;
    private List<CastData> castList;
    List<MovieEntry> entries;
    Trailer trailer;
    Review review;
    Cast cast;
    LinearLayoutManager layoutManager, layoutManagerOne, layoutManagerTwo;

    //poster animation
    private static final int PERCENTAGE_TO_ANIMATE_POSTER = 20;
    private boolean mIsPosterShown = true;


    String movieTitle, movieVoteAverage, movieReleaseDate, movieAdult, moviePopularity, movieLanguage,
            movieId, movieVoteCount, movieBackdrop, movieOverview, movieGenre, moviePoster;

    int movie_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FavouriteMoviesViewModelFactory favouriteMoviesViewModelFactory = FavouriteMoviesViewModelFactory.createFactory(this);
        favouriteMoviesViewModel = ViewModelProviders.of(this, favouriteMoviesViewModelFactory).get(FavouriteMoviesViewModel.class);
        setContentView(R.layout.activity_movie_details);

        entries = new ArrayList<>();
        posterImg = findViewById(R.id.movie_thumbnail_image2);
        movietitle = findViewById(R.id.movie_title1);
        year = findViewById(R.id.movie_year1);
        rating = findViewById(R.id.movie_rating1);
        reviews = findViewById(R.id.movie_reviews);
        language = findViewById(R.id.original_lang);
        genre = findViewById(R.id.genre);
        movierating = findViewById(R.id.rated);
        synopsis = findViewById(R.id.overview);
        posterCard = findViewById(R.id.movie_card1);
        kbv = findViewById(R.id.backdropOne);
        materialFavoriteButton = findViewById(R.id.favorite_button);


        //get items from the adapter intent and set in views
        Bundle data = getIntent().getBundleExtra("items");
        movieTitle = data.getString("movieTitle");
        movieVoteAverage = data.getString("movieVoteAverage");
        movieReleaseDate = data.getString("movieReleaseDate");
        movieAdult = data.getString("movieAdult");
        moviePopularity = data.getString("moviePopularity");
        movieLanguage = data.getString("movieLanguage");
        movieId = data.getString("movieId");
        movieVoteCount = data.getString("movieVoteCount");
        movieBackdrop = data.getString("movieBackdrop");
        movieOverview = data.getString("movieOverview");
        movieGenre = data.getString("movieGenre");
        moviePoster = data.getString("moviePoster");


        String replace = movieGenre.replace("[", "");
        String replace1 = replace.replace("]", "");
        List<String> arrayList = new ArrayList<>(Arrays.asList(replace1.split(",")));
        List<Integer> movieGenreList = new ArrayList<Integer>();
        for (String fav : arrayList) {
            movieGenreList.add(Integer.parseInt(fav.trim()));
        }


        //set movie details info
        assert movieId != null;
        movie_Id = Integer.parseInt(movieId);

        movietitle.setText(movieTitle);
        year.setText(getFormattedReleaseDate(movieReleaseDate));
        rating.setText(movieVoteAverage + "/10");
        reviews.setText(movieVoteCount + " Reviews");
        String langua = movieLanguage.toUpperCase();
        language.setText(langua);
        synopsis.setText(movieOverview);
        genre.setText(GenreHelper.getGenreNamesList(movieGenreList));


        Glide.with(this).load(Url.posterUrl(moviePoster)).into(posterImg);
        Glide.with(this).load(Url.posterUrlBackdrop(movieBackdrop)).into(kbv);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar1);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //set collapsingToolbar title just like twitter

        final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar1);

        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        //implement offset listener for appbar and avatar
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) ;
                int percentage;
                {
                    scrollRange = appBarLayout.getTotalScrollRange();
                    percentage = (Math.abs(verticalOffset)) * 100 / scrollRange;

                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(movieTitle);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

                if (percentage >= PERCENTAGE_TO_ANIMATE_POSTER && mIsPosterShown) {
                    mIsPosterShown = false;

                    posterCard.animate()
                            .scaleY(0).scaleX(0)
                            .setDuration(200)
                            .start();

                    materialFavoriteButton.animate()
                            .scaleY(0).scaleX(0)
                            .setDuration(200)
                            .start();

                }

                if (percentage <= PERCENTAGE_TO_ANIMATE_POSTER && !mIsPosterShown) {
                    mIsPosterShown = true;

                    posterCard.animate()
                            .scaleY(1).scaleX(1)
                            .start();

                    materialFavoriteButton.animate()
                            .scaleY(1).scaleX(1)
                            .setDuration(200)
                            .start();
                }
            }

        });

        initializeTrailer();
        initializeReview();
        initializeCast();
        loadTrailer();
        loadReview();
        loadCast();
        checkFavouriteStatus(movieTitle);
    }


    private void initializeTrailer() {
        trailerList = new ArrayList<>();
        trailerAdapter = new TrailerAdapter(this, trailerList);
        trailerrecyclerView = findViewById(R.id.trailer_list_view);
        trailerrecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        trailerrecyclerView.setLayoutManager(layoutManager);
        trailerrecyclerView.setAdapter(trailerAdapter);

    }


    private void loadTrailer() {
        String movieId = getIntent().getBundleExtra("items").getString("movieId");
        assert movieId != null;
        int trailerId = Integer.parseInt(movieId);

        try {

            Client client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);


            Call<Trailer> call = apiService.getMovieTrailerData(trailerId, Url.API_KEY);

            call.enqueue(new Callback<Trailer>() {
                @Override
                public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                    trailer = response.body();
                    assert trailer != null;
                    List<TrailerData> trailerDataList = trailer.getResults();
                    //Log.i("trailer test:", response.body().toString());
                    trailerAdapter = new TrailerAdapter(getApplicationContext(), trailerDataList);
                    trailerrecyclerView.smoothScrollToPosition(0);
                    trailerrecyclerView.setAdapter(trailerAdapter);
                    trailerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Trailer> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initializeReview() {
        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, reviewList);
        reviewrecyclerView = findViewById(R.id.reviews_list_view);
        reviewrecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManagerOne = new LinearLayoutManager(getApplicationContext());
        layoutManagerOne.setOrientation(LinearLayoutManager.VERTICAL);
        reviewrecyclerView.setLayoutManager(layoutManagerOne);
        reviewrecyclerView.setAdapter(reviewAdapter);
    }

    private void loadReview() {
        String movieId = getIntent().getBundleExtra("items").getString("movieId");
        assert movieId != null;
        int reviewId = Integer.parseInt(movieId);

        try {

            Client client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);


            Call<Review> call = apiService.getReviewsData(reviewId, Url.API_KEY);

            call.enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    review = response.body();
                    assert review != null;
                    List<ReviewData> reviewDataList = review.getResults();
                    //Log.i("review test:", response.body().toString());
                    reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewDataList);
                    reviewrecyclerView.smoothScrollToPosition(0);
                    reviewrecyclerView.setAdapter(reviewAdapter);
                    reviewAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Review> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initializeCast() {

        castList = new ArrayList<>();
        castAdapter = new CastAdapter(this, castList);
        castrecyclerView = findViewById(R.id.cast_list_view);
        castrecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManagerTwo = new LinearLayoutManager(getApplicationContext());
        layoutManagerTwo.setOrientation(LinearLayoutManager.HORIZONTAL);
        castrecyclerView.setLayoutManager(layoutManagerTwo);
        castrecyclerView.setAdapter(castAdapter);

    }


    private void loadCast() {
        String movieId = getIntent().getBundleExtra("items").getString("movieId");
        assert movieId != null;
        int castId = Integer.parseInt(movieId);

        try {

            Client client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);


            Call<Cast> call = apiService.getCastsData(castId, Url.API_KEY);

            call.enqueue(new Callback<Cast>() {
                @Override
                public void onResponse(Call<Cast> call, Response<Cast> response) {
                    cast = response.body();
                    assert cast != null;
                    List<CastData> castDataList = cast.getCast();
                    //Log.i("cast test:", response.body().toString());
                    castAdapter = new CastAdapter(getApplicationContext(), castDataList);
                    castrecyclerView.smoothScrollToPosition(0);
                    castrecyclerView.setAdapter(castAdapter);
                    castAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Cast> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //save favourite movie
    public void saveFavorite() {
        double pop = Double.parseDouble(moviePopularity);
        double voteAverage = Double.parseDouble(movieVoteAverage);

        favouriteMoviesViewModel.saveFavMovie(movie_Id, voteAverage, pop, movieTitle, moviePoster,
                movieLanguage, movieBackdrop, movieOverview, movieReleaseDate);
    }


    //delete favourite movie
    private void deleteFavorite(int movie_id) {
        favouriteMoviesViewModel.deleteById(movie_id);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Return the formatted date string (i.e. "January 1, 2019") from a Date object.
     */

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


    public static String getFormattedReleaseDate(String releaseDate) {
        if (TextUtils.isEmpty(releaseDate)) return "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DATE_FORMAT.parse(releaseDate));
            return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " "
                    + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            return "";
        }
    }


    @SuppressLint("StaticFieldLeak")
    private void checkFavouriteStatus(String movieTitle) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                entries.clear();
                entries = favouriteMoviesViewModel.getListOfFavMovies(movieTitle);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (entries.size() > 0) {
                    materialFavoriteButton.setFavorite(true);
                    materialFavoriteButton.setOnFavoriteChangeListener((buttonView, favorite) -> {
                            if (favorite) {
                                saveFavorite();
                                final Snackbar snackbar = Snackbar
                                        .make(findViewById(R.id.main_content), "Movie has been added to favourite list!", Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction("Dismiss", view -> snackbar.dismiss());
                                snackbar.setActionTextColor(Color.YELLOW);
                                View snackBarView = snackbar.getView();
                                snackBarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                snackbar.show();
                            } else {
                                deleteFavorite(movie_Id);
                                final Snackbar snackbar = Snackbar
                                        .make(findViewById(R.id.main_content), "Movie has been removed to favourite list!", Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction("Dismiss", view -> snackbar.dismiss());
                                snackbar.setActionTextColor(Color.YELLOW);
                                View snackBarView = snackbar.getView();
                                snackBarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                snackbar.show();
                            }
                        });
                } else {
                    materialFavoriteButton.setOnFavoriteChangeListener(
                            (buttonView, favorite) -> {
                                if (favorite) {
                                    saveFavorite();
                                    final Snackbar snackbar = Snackbar
                                            .make(findViewById(R.id.main_content), "Movie has been added to favourite list!", Snackbar.LENGTH_INDEFINITE);
                                    snackbar.setAction("Dismiss", view -> snackbar.dismiss());
                                    snackbar.setActionTextColor(Color.YELLOW);
                                    View snackBarView = snackbar.getView();
                                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                    snackbar.show();
                                } else {
                                    int movie_id = Objects.requireNonNull(getIntent().getExtras()).getInt("id");
                                    deleteFavorite(movie_id);
                                    final Snackbar snackbar = Snackbar
                                            .make(findViewById(R.id.main_content), "Movie has been removed to favourite list!", Snackbar.LENGTH_INDEFINITE);
                                    snackbar.setAction("Dismiss", view -> snackbar.dismiss());
                                    snackbar.setActionTextColor(Color.YELLOW);
                                    View snackBarView = snackbar.getView();
                                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                    snackbar.show();

                                }
                            });
                }

            }

        }.execute();
    }
}

