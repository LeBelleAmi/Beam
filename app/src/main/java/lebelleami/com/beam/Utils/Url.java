package lebelleami.com.beam.Utils;

public class Url {

    //constants that make up the url
/*
    movie database api
    Popular Movies
    https://api.themoviedb.org/3/movie/popular?api_key=[Your_Api_Key]
*/
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String TYPE_A = "movie/popular";
    public static final String TYPE_B = "movie/now_playing";
    public static final String TYPE_C = "movie/upcoming";
    public static final String TYPE_D = "movie/top_rated";
    public static final String TYPE_E = "tv/popular";
    public static final String TYPE_F = "tv/top_rated";
    public static final String TYPE_G = "tv/airing_today";
    public static final String API_KEY = "YOUR_API_KEY";
    public static final String TRAILER_MOVIE = "movie/";
    public static final String TRAILER_TV = "tv/";
    public static final String REVIEWS = "/reviews";
    public static final String CASTS = "/casts";
    public static final String CREDITS = "/credits";


    public static String posterUrl(String posterPath) {
        return "https://image.tmdb.org/t/p/w500/" + posterPath;
    }

    public static String posterUrlBackdrop(String posterPath) {
        return "https://image.tmdb.org/t/p/w1280/" + posterPath;
    }

    public static String posterProfile(String posterPath) {
        return "https://image.tmdb.org/t/p/h632/" + posterPath;
    }

    public static String posterUrlSeason(String posterPath) {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }


}
