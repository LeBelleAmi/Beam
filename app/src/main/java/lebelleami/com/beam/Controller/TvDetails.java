package lebelleami.com.beam.Controller;

import android.content.res.Configuration;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import lebelleami.com.beam.Api.Client;
import lebelleami.com.beam.Api.Service;
import lebelleami.com.beam.Model.Season;
import lebelleami.com.beam.Model.TvCast;
import lebelleami.com.beam.Model.TvCredits;
import lebelleami.com.beam.Model.TvSeriesDetails;
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
import lebelleami.com.beam.View.TvCastAdapter;
import lebelleami.com.beam.View.TvDetailsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvDetails extends AppCompatActivity {

    ImageView backdropImg, posterImg;
    KenBurnsView kbv;
    TextView tvtitle, year, rating, reviews, language, tvrating, genre, synopsis;
    CardView posterCard;
    RecyclerView trailerrecyclerView, reviewrecyclerView, castrecyclerView, seasonrecyclerView;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private TvCastAdapter tvcastAdapter;
    private TvDetailsAdapter tvDetailsAdapter;
    private List<TrailerData> trailerList;
    private List<ReviewData>reviewList;
    private List<TvCast>tvcastList;
    private List<Season> seasonList;
    Trailer trailer;
    Review review;
    TvCredits tvCredits;
    TvSeriesDetails tvSeriesDetails;
    LinearLayoutManager layoutManager, layoutManagerOne,layoutManagerTwo, layoutManagerThree;

    //poster animation
    private static final int PERCENTAGE_TO_ANIMATE_POSTER = 20;
    private boolean mIsPosterShown = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_details);

        //backdropImg = findViewById(R.id.backdrop);
        posterImg = findViewById(R.id.movie_thumbnail_image2);
        tvtitle = findViewById(R.id.movie_title1);
        year = findViewById(R.id.movie_year1);
        rating = findViewById(R.id.movie_rating1);
        reviews = findViewById(R.id.movie_reviews);
        language = findViewById(R.id.original_lang);
        genre = findViewById(R.id.genre);
        tvrating = findViewById(R.id.rated);
        synopsis = findViewById(R.id.overview);
        posterCard = findViewById(R.id.movie_card1);
        kbv = findViewById(R.id.backdropTwo);


        //get items from the adapter intent and set in views
        Bundle data = getIntent().getBundleExtra("items");
        final String tvTitle = data.getString("movieTitle");
        String movieVoteAverage = data.getString("movieVoteAverage");
        String movieReleaseDate = data.getString("movieReleaseDate");
        String moviePopularity = data.getString("moviePopularity");
        String movieLanguage = data.getString("movieLanguage");
        String tvId = data.getString("tvId");
        String movieVoteCount = data.getString("movieVoteCount");
        String movieBackdrop = data.getString("movieBackdrop");
        String movieOverview = data.getString("movieOverview");
        String movieGenre = data.getString("movieGenre");
        String moviePoster = data.getString("moviePoster");


        String replace = movieGenre.replace("[","");
        String replace1 = replace.replace("]","");
        List<String> arrayList = new ArrayList<String>    (Arrays.asList(replace1.split(",")));
        List<Integer> tvGenreList = new ArrayList<Integer>();
        for(String fav:arrayList){
            tvGenreList.add(Integer.parseInt(fav.trim()));
        }

        //set movie details info
        tvtitle.setText(tvTitle);
        year.setText(getFormattedReleaseDate(movieReleaseDate));
        rating.setText(movieVoteAverage + "/10");
        reviews.setText(movieVoteCount + " Reviews");
        String langua = movieLanguage.toUpperCase();
        language.setText(langua);
        synopsis.setText(movieOverview);
        genre.setText(GenreHelper.getGenreNamesList(tvGenreList));
        Glide.with(this).load(Url.posterUrl(moviePoster)).into(posterImg);
        //Glide.with(this).load(Url.posterUrlBackdrop(movieBackdrop)).into(backdropImg);
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
                    collapsingToolbar.setTitle(tvTitle);
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
                }

                if (percentage <= PERCENTAGE_TO_ANIMATE_POSTER && !mIsPosterShown) {
                    mIsPosterShown = true;

                    posterCard.animate()
                            .scaleY(1).scaleX(1)
                            .start();
                }
            }

        });

        initializeTrailer();
        initializeReview();
        initializeCast();
        initializeSeason();
        loadTrailer();
        loadReview();
        loadTvCast();
        loadTvSeason();
    }

    private void initializeTrailer(){
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
        String tvId = getIntent().getBundleExtra("items").getString("tvId");
        int trailerId = Integer.parseInt(tvId);

        try{

            Client client = new Client();
            Service apiService =
                    client.getClient().create(Service.class);


            Call<Trailer> call = apiService.getTvTrailerData(trailerId, Url.API_KEY);

            call.enqueue(new Callback<Trailer>() {
                @Override
                public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                    trailer = response.body();
                    List<TrailerData> trailerDataList = trailer.getResults();
                    //Log.i("trailer test:", response.body().toString());
                    trailerAdapter = new TrailerAdapter(getApplicationContext(), trailerDataList);
                    trailerrecyclerView.smoothScrollToPosition(0);
                    trailerrecyclerView.setAdapter(trailerAdapter);
                    trailerAdapter.notifyDataSetChanged();}

                @Override
                public void onFailure(Call<Trailer> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        }catch (Exception e){
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
        String tvId = getIntent().getBundleExtra("items").getString("tvId");
        int reviewId = Integer.parseInt(tvId);

        try{

            Client client = new Client();
            Service apiService =
                    client.getClient().create(Service.class);


            Call<Review> call = apiService.getTvReviewsData(reviewId, Url.API_KEY);

            call.enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    review = response.body();
                    List<ReviewData> reviewDataList = review.getResults();
                    //Log.i("review test:", response.body().toString());
                    reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewDataList);
                    reviewrecyclerView.smoothScrollToPosition(0);
                    reviewrecyclerView.setAdapter(reviewAdapter);
                    reviewAdapter.notifyDataSetChanged();}

                @Override
                public void onFailure(Call<Review> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        }catch (Exception e){
        }
    }


    private void initializeCast() {

        tvcastList = new ArrayList<>();
        tvcastAdapter = new TvCastAdapter(this, tvcastList);
        castrecyclerView = findViewById(R.id.cast_list_view);
        castrecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManagerTwo = new LinearLayoutManager(getApplicationContext());
        layoutManagerTwo.setOrientation(LinearLayoutManager.HORIZONTAL);
        castrecyclerView.setLayoutManager(layoutManagerTwo);
        castrecyclerView.setAdapter(tvcastAdapter);

    }


    private void loadTvCast() {
        String tvId = getIntent().getBundleExtra("items").getString("tvId");
        int castId = Integer.parseInt(tvId);


        try{

            Client client = new Client();
            Service apiService =
                    client.getClient().create(Service.class);

            Call<TvCredits> call = apiService.getTvCastsData(castId, Url.API_KEY);

            call.enqueue(new Callback<TvCredits>() {
                @Override
                public void onResponse(Call<TvCredits> call, Response<TvCredits> response) {
                    tvCredits = response.body();
                    List<TvCast> castDataList = tvCredits.getCast();
                    //Log.i("cast test:", response.body().toString());
                    //Toast.makeText(getApplicationContext(), "Cast" + response.body().toString(), Toast.LENGTH_LONG).show();
                    tvcastAdapter = new TvCastAdapter(getApplicationContext(), castDataList);
                    castrecyclerView.smoothScrollToPosition(0);
                    castrecyclerView.setAdapter(tvcastAdapter);
                    tvcastAdapter.notifyDataSetChanged();}

                @Override
                public void onFailure(Call<TvCredits> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        }catch (Exception e){
        }

    }

    private void initializeSeason() {

        seasonList = new ArrayList<>();
        tvDetailsAdapter = new TvDetailsAdapter(this, seasonList);
        seasonrecyclerView = findViewById(R.id.season_list_view);
        seasonrecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManagerThree = new LinearLayoutManager(getApplicationContext());
        layoutManagerThree.setOrientation(LinearLayoutManager.HORIZONTAL);
        seasonrecyclerView.setLayoutManager(layoutManagerThree);
        seasonrecyclerView.setAdapter(tvDetailsAdapter);

    }


    private void loadTvSeason() {
        String tvId = getIntent().getBundleExtra("items").getString("tvId");
        int seasonId = Integer.parseInt(tvId);


        try{

            Client client = new Client();
            Service apiService =
                    client.getClient().create(Service.class);

            Call<TvSeriesDetails> call = apiService.getTvSeasonData(seasonId, Url.API_KEY);

            call.enqueue(new Callback<TvSeriesDetails>() {
                @Override
                public void onResponse(Call<TvSeriesDetails> call, Response<TvSeriesDetails> response) {
                    tvSeriesDetails = response.body();
                    List<Season> seasonList = tvSeriesDetails.getSeasons();
                    //Log.i("season test:", response.body().toString());
                    //Toast.makeText(getApplicationContext(), "Cast" + response.body().toString(), Toast.LENGTH_LONG).show();
                    tvDetailsAdapter = new TvDetailsAdapter(getApplicationContext(), seasonList);
                    seasonrecyclerView.smoothScrollToPosition(0);
                    seasonrecyclerView.setAdapter(tvDetailsAdapter);
                    tvDetailsAdapter.notifyDataSetChanged();}

                @Override
                public void onFailure(Call<TvSeriesDetails> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        }catch (Exception e){
        }

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
            return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " "+calendar.get(Calendar.DAY_OF_MONTH) +", " + calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            return "";
        }
    }

}
