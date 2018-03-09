package chnu.practice.movieadvicer.dataSource;


import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import chnu.practice.movieadvicer.models.MovieModel.Movies;
import chnu.practice.movieadvicer.models.MovieModel.Result;

public class FileDataSource {

    private static final String TAGG = "FILE_DATA_SOURCE";
    private static final String MOVIES_BY_GENRES_FILE_NAME = "MOVIES_BY_GENRE_FILE.txt";
    private static final String MOVIES_FAVORITE_FILE_NAME = "MOVIES_FAVORITE_FILE.txt";
    private static FileDataSource sInstance = new FileDataSource();
    private File mGenresMoviesFile, mFavoriteMovies;
    private Gson gson;
    private Movies mMoviesByGenre;
    private Set<Integer> mMoviesFavorite = new HashSet<>();
    private int genreId;

    private FileDataSource() {
        gson = new Gson();
        openDataSource();
    }

    public static FileDataSource getInstance() {
        return sInstance;
    }

    private void openDataSource() {
        File sdcard = Environment.getExternalStorageDirectory();
        mGenresMoviesFile = new File(sdcard, MOVIES_BY_GENRES_FILE_NAME);
        mFavoriteMovies = new File(sdcard, MOVIES_FAVORITE_FILE_NAME);
        readFavoriteMovies();
        readGenresMovies();
    }

    public void readFavoriteMovies() {
        readFromFile(mFavoriteMovies);
        if(mMoviesFavorite == null){
            mMoviesFavorite = new HashSet<>();
        }
    }

    public void readGenresMovies() {
        readFromFile(mGenresMoviesFile);
        if(mMoviesByGenre == null){
            mMoviesByGenre = new Movies();
        }
    }

    private void updateFavoriteMovies() {
        saveToFile(mFavoriteMovies);
    }

    public void addFavoriteMovie(Result result) {
//        if(mMoviesFavorite == null){
//            mMoviesFavorite = new HashSet<>();
//        }
        mMoviesFavorite.add(result.id);
        updateFavoriteMovies();
    }

    public void removeFavoriteMovie(Result result){
        mMoviesFavorite.remove(result.id);
        updateFavoriteMovies();
    }



    public void addNewPage(Movies movies) {
        this.mMoviesByGenre.results.addAll(movies.results);
        this.mMoviesByGenre.page++;
    }

    public void saveMoviesByGenre(int genreId, Movies movies) {
        this.genreId = genreId;
        mMoviesByGenre = movies;
        synchronizeWithFavorite();
        updateGenresMoviesFile();
    }

    public void updateGenresMoviesFile() {
        saveToFile(mGenresMoviesFile);
    }

 /*   public Set<Integer> getMoviesFavorite() {
        return mMoviesFavorite;
    }
*/
    public Movies getMoviesByGenre() {
        return mMoviesByGenre;
    }

    public int getGenreId() {
        return genreId;
    }

    public void synchronizeWithFavorite(){
        if(mMoviesFavorite != null && mMoviesByGenre !=null) {
            for (int favorite : mMoviesFavorite) {
                for (int i = 0; i < mMoviesByGenre.results.size(); i++) {
                    if (favorite == mMoviesByGenre.results.get(i).id) {
                        mMoviesByGenre.results.get(i).setFavorite(true);
                        Log.d("TAGG", "in sync"+ mMoviesByGenre.results.get(i).title+": "+
                        mMoviesByGenre.results.get(i).isFavorite());
                    }
                }
            }
        }
    }

    private void saveToFile(File file) {
        String data = "";
        switch (file.getName()) {
            case MOVIES_BY_GENRES_FILE_NAME:
                data = gson.toJson(mMoviesByGenre);
                break;
            case MOVIES_FAVORITE_FILE_NAME:
                data = gson.toJson(mMoviesFavorite);
                break;
            default:
                break;
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.append(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readFromFile(File file) {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
        }
        transformDataFromFile(text.toString(), file.getName());

    }

    private void transformDataFromFile(String data, String name) {
        switch (name) {
            case MOVIES_BY_GENRES_FILE_NAME:
                Type typeMovies = new TypeToken<Movies>() {
                }.getType();
                mMoviesByGenre = gson.fromJson(data, typeMovies);
                break;
            case MOVIES_FAVORITE_FILE_NAME:
                Type typeFavoriteMovies = new TypeToken<Set<Integer>>() {
                }.getType();
                mMoviesFavorite = gson.fromJson(data, typeFavoriteMovies);
                break;
            default:
                break;
        }
    }

}
