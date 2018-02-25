package chnu.practice.movieadvicer.dataSource;


import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import chnu.practice.movieadvicer.models.MovieModel.Movies;

public class FileDataSource {

    private static final String TAGG = "FILE_DATA_SOURCE";
    private static final String MOVIES_BY_GENRES_FILE_NAME = "MOVIES_BY_GENRE_FILE.txt";
    private static final String MOVIES_FAVORITE_FILE_NAME = "MOVIES_FAVORITE_FILE.txt";
    private OnDataChangeListener changeListener;
    private File mGenresMoviesFile;//, mFavoriteMovies;
    private Gson gson;
    private Movies mMoviesByGenre;
    private int genreId;

    private static FileDataSource sInstance;

    public static FileDataSource getInstance(OnDataChangeListener changeListener){
        if(sInstance == null){
            sInstance = new FileDataSource();
        }
        sInstance.changeListener = changeListener;
        sInstance.openDataSource();
        return sInstance;
    }

    public interface OnDataChangeListener {
        void onDataSaved(String message);
        void onDataOpened(Movies results);
    }


    public FileDataSource() {
        gson = new Gson();
    }

    private void openDataSource() {
        File sdcard = Environment.getExternalStorageDirectory();
        mGenresMoviesFile = new File(sdcard, MOVIES_BY_GENRES_FILE_NAME);
        readFromFile(mGenresMoviesFile);
    }


    public void saveMoviesByGenre(int genreId, Movies movies) {
        this.genreId = genreId;
        mMoviesByGenre = movies;
        saveToFile(mGenresMoviesFile);
    }


    public Movies getMoviesByGenre() {
        return mMoviesByGenre;
    }

    public void updateMovies(Movies movies){
        this.mMoviesByGenre.page++;
        this.mMoviesByGenre.results.addAll(movies.results);
    }

    public int getGenreId() {
        return genreId;
    }

    private void saveToFile(File file) {
        String data = "";
        switch (file.getName()) {
            case MOVIES_BY_GENRES_FILE_NAME:
                data = gson.toJson(mMoviesByGenre);
                break;
            case MOVIES_FAVORITE_FILE_NAME:
                break;
            default:
                break;
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.append(data);
            writer.flush();
            writer.close();
            changeListener.onDataSaved("saved");
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
        if (changeListener != null && mMoviesByGenre != null) {
            changeListener.onDataOpened(mMoviesByGenre);
        }
    }

    private void transformDataFromFile(String data, String name) {
        switch (name) {
            case MOVIES_BY_GENRES_FILE_NAME:
                Type typeMoviesGenre = new TypeToken<Movies>() {
                }.getType();
                mMoviesByGenre = gson.fromJson(data, typeMoviesGenre);
                break;
            case MOVIES_FAVORITE_FILE_NAME:
                break;
            default:
                break;
        }
    }


}
