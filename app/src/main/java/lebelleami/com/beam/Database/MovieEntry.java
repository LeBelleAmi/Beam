package lebelleami.com.beam.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;



@Entity(tableName = "favourite_movies")
public class MovieEntry {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;


    @ColumnInfo(name = "movieid")
    private Integer movieId;

    @ColumnInfo(name = "vote_average")
    private Double vote_average;

    @ColumnInfo(name = "popularity")
    private Double popularity;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "poster_path")
    private String poster_path;

    @ColumnInfo(name = "original_language")
    private String original_language;

    @ColumnInfo(name = "backdrop_path")
    private String backdrop_path;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "release_date")
    private String release_date;

    @Ignore
    public MovieEntry(Integer movieId, Double vote_average, Double popularity, String title,
                      String poster_path, String original_language, String backdrop_path,
                      String overview, String release_date) {

        this.movieId = movieId;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public MovieEntry(@NonNull Long id, Integer movieId, Double vote_average, Double popularity, String title,
                      String poster_path, String original_language, String backdrop_path,
                      String overview, String release_date) {

        this.id = id;
        this.movieId = movieId;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }


    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
