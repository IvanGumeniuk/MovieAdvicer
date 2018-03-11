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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chnu.practice.movieadvicer.models.MovieModel.Movies;
import chnu.practice.movieadvicer.models.MovieModel.Result;

public class FileDataSource {

    private static final String TAGG = "FILE_DATA_SOURCE";
    private static final String MOVIES_BY_GENRES_FILE_NAME = "MOVIES_BY_GENRE_FILE.txt";
    private static final String ID_FAVORITE_FILE_NAME = "ID_FAVORITE_FILE.txt";
    private static final String MOVIES_FAVORITE_FILE_NAME = "MOVIES_FAVORITE_FILE.txt";
    private static FileDataSource sInstance = new FileDataSource();
    private File mGenresMoviesFile, mFavoriteIdFile, mFavoriteMoviesFile;
    private Gson gson;
    private Movies mMoviesByGenre;
    private Set<Integer> mFavoriteId;
    private List<Result> mFavoriteMovies;
    private int genreId;
    private favListener mListener;

    public interface favListener{
        void onChange(List<Result> results);
    }
    private FileDataSource() {
        gson = new Gson();

        openDataSource();
    }

    public void setOnChange(favListener listener){
        this.mListener = listener;
    }

    public static FileDataSource getInstance() {
        return sInstance;
    }

    private void openDataSource() {
        File sdcard = Environment.getExternalStorageDirectory();
        mGenresMoviesFile = new File(sdcard, MOVIES_BY_GENRES_FILE_NAME);
        mFavoriteIdFile = new File(sdcard, ID_FAVORITE_FILE_NAME);
        mFavoriteMoviesFile = new File(sdcard, MOVIES_FAVORITE_FILE_NAME);
        readFavoriteMovies();
        readFavoriteId();
        readGenresMovies();
    }

    public void readFavoriteMovies(){
        readFromFile(mFavoriteMoviesFile);
        if(mFavoriteMovies == null){
            mFavoriteMovies = new ArrayList<>();
        }
    }

    public void readFavoriteId() {
        readFromFile(mFavoriteIdFile);
        if(mFavoriteId == null){
            mFavoriteId = new HashSet<>();
        }
    }

    public void readGenresMovies() {
        readFromFile(mGenresMoviesFile);
        if(mMoviesByGenre == null){
            mMoviesByGenre = new Movies();
        }
    }

    private void updateFavorite() {
        saveToFile(mFavoriteIdFile);
        saveToFile(mFavoriteMoviesFile);
        if(mListener != null) {
            mListener.onChange(mFavoriteMovies);
            Log.d("TAGG", mFavoriteId.toString());
        }
    }

    public void addFavoriteMovie(Result result) {
        mFavoriteId.add(result.id);
        mFavoriteMovies.add(result);
        updateFavorite();
    }

    public void removeFavoriteMovie(Result result){
        mFavoriteId.remove(result.id);
        mFavoriteMovies.remove(result);
        updateFavorite();
    }


    public void addNewPage(Movies movies) {
        this.mMoviesByGenre.results.addAll(movies.results);
        this.mMoviesByGenre.page++;
        updateGenresMoviesFile();
    }

    public void saveMoviesByGenre(int genreId, Movies movies) {
        this.genreId = genreId;
        mMoviesByGenre = movies;
        updateGenresMoviesFile();
    }

    public void updateGenresMoviesFile() {
        synchronizeWithFavorite();
        saveToFile(mGenresMoviesFile);
    }

    public List<Result> getFavoriteMovies() {
        return mFavoriteMovies;
    }

    public Movies getMoviesByGenre() {
        return mMoviesByGenre;
    }

    public int getGenreId() {
        return genreId;
    }

    public void synchronizeWithFavorite(){
        if(mFavoriteId != null && mMoviesByGenre !=null) {
            for (int favorite : mFavoriteId) {
                for (int i = 0; i < mMoviesByGenre.results.size(); i++) {
                    if (favorite == mMoviesByGenre.results.get(i).id) {
                        mMoviesByGenre.results.get(i).setFavorite(true);
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
            case ID_FAVORITE_FILE_NAME:
                data = gson.toJson(mFavoriteId);
                break;
            case MOVIES_FAVORITE_FILE_NAME:
                data = gson.toJson(mFavoriteMovies);
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
        Type dataType = getDataType(name);
        switch (name) {
            case MOVIES_BY_GENRES_FILE_NAME:
                mMoviesByGenre = gson.fromJson(data, dataType);
                break;
            case ID_FAVORITE_FILE_NAME:
                mFavoriteId = gson.fromJson(data, dataType);
                break;
            case MOVIES_FAVORITE_FILE_NAME:
                mFavoriteMovies = gson.fromJson(data, dataType);
                break;
            default:
                break;
        }
    }

    private Type getDataType(String dataName){
        Type type = null;
        switch (dataName){
            case MOVIES_BY_GENRES_FILE_NAME:
                type = new TypeToken<Movies>() {}.getType();
                break;
            case ID_FAVORITE_FILE_NAME:
                type = new TypeToken<Set<Integer>>() {}.getType();
                break;
            case MOVIES_FAVORITE_FILE_NAME:
                type = new TypeToken<List<Result>>(){}.getType();
                break;
        }
        return type;
    }

}
