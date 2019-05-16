package lebelleami.com.beam.Api;

import lebelleami.com.beam.Model.TvCredits;
import lebelleami.com.beam.Model.TvSeriesDetails;
import lebelleami.com.beam.Utils.Url;
import lebelleami.com.beam.Model.Cast;
import lebelleami.com.beam.Model.Movie;
import lebelleami.com.beam.Model.Review;
import lebelleami.com.beam.Model.Trailer;
import lebelleami.com.beam.Model.Tv;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    //POPULAR MOVIES
    @GET(Url.TYPE_A)
    //Call<Movie> getPopularMovieData(@Query("api_key") String apikey);
    Call<Movie> getPopularMovieData(@Query("api_key") String apikey, @Query("page") int page);


    //THEATER MOVIES
    @GET(Url.TYPE_B)
    Call<Movie> getTheaterMovieData(@Query("api_key") String apikey , @Query("page") int page);


    //UPCOMING MOVIES
    @GET(Url.TYPE_C)
    Call<Movie> getUpcomingMovieData(@Query("api_key") String apikey, @Query("page") int page);


    //TOP RATED MOVIES
    @GET(Url.TYPE_D)
    Call<Movie> getTopRatedMovieData(@Query("api_key") String apikey , @Query("page") int page);


    //POPULAR TV SHOWS
    @GET(Url.TYPE_E)
    Call<Tv> getPopularTvData(@Query("api_key") String apikey , @Query("page") int page);

    //TOP RATED TV SHOWS
    @GET(Url.TYPE_F)
    Call<Tv> getTopRatedTvData(@Query("api_key") String apikey , @Query("page") int page);

    //TV SHOWS ON AIR TODAY
    @GET(Url.TYPE_G)
    Call<Tv> getAiringTodayTvData(@Query("api_key") String apikey , @Query("page") int page);

    //Trailer
    //movie/{movie_id}/videos
    @GET(Url.TRAILER_MOVIE + "{movie_id}/videos")
    Call<Trailer> getMovieTrailerData(@Path("movie_id") int id, @Query("api_key") String apikey);

    // tv/{tv_id}/videos
    @GET(Url.TRAILER_TV + "{tv_id}/videos")
    Call<Trailer> getTvTrailerData(@Path("tv_id") int id, @Query("api_key") String apikey);

    // movie/{movie_id}/reviews
    @GET(Url.TRAILER_MOVIE + "{movie_id}" + Url.REVIEWS)
    Call<Review> getReviewsData(@Path("movie_id") int id, @Query("api_key") String apikey);

    // tv/{tv_id}/reviews
    @GET(Url.TRAILER_TV + "{tv_id}" + Url.REVIEWS)
    Call<Review> getTvReviewsData(@Path("tv_id") int id, @Query("api_key") String apikey);


    // Movie Cast /movie/{movie_id}/casts
    @GET(Url.TRAILER_MOVIE + "{movie_id}" + Url.CASTS)
    Call<Cast> getCastsData(@Path("movie_id") int id, @Query("api_key") String apikey);

    // TV Cast /tv/{tv_id}/credits
    @GET(Url.TRAILER_TV + "{tv_id}" + Url.CREDITS)
    Call<TvCredits> getTvCastsData(@Path("tv_id") int id, @Query("api_key") String apikey);


    // tv/{tv_id} /tv/{tv_id}/season/{season_number}
    @GET(Url.TRAILER_TV + "{tv_id}")
    Call<TvSeriesDetails> getTvSeasonData(@Path("tv_id") int id, @Query("api_key") String apikey);

}
