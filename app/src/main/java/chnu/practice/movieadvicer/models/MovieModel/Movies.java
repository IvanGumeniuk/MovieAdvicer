package chnu.practice.movieadvicer.models.MovieModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movies implements Serializable{

    @SerializedName("page")
    public Integer page;

    @SerializedName("total_results")
    public Integer totalResults;

    @SerializedName("total_pages")
    public Integer totalPages;

    @SerializedName("results")
    public List<Result> results = null;

}
