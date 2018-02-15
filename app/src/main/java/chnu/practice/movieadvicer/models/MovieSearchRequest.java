package chnu.practice.movieadvicer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchRequest {

    @SerializedName("page")
    public Integer page;

    @SerializedName("total_results")
    public Integer totalResults;

    @SerializedName("total_pages")
    public Integer totalPages;

    @SerializedName("results")
    public List<Result> results = null;

}
