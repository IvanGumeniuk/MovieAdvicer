package chnu.practice.movieadvicer.consts;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ваня on 07.02.2018.
 */

public class Constants {
    public static final long SPLASH_DELAY = TimeUnit.SECONDS.toMillis(2);
    public static final String API_KEY = "cdfc4ab5a9d6849d6596428aa52851cb";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String LANGUAGE_PARAM = "en-EN";
    public static final String SORT_BY = "created_at.asc";
    public static final short SAVING_PAGE_LIMIT = 3;
}
