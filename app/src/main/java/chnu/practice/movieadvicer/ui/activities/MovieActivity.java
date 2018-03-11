package chnu.practice.movieadvicer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import chnu.practice.movieadvicer.R;
import chnu.practice.movieadvicer.consts.BundleKeys;
import chnu.practice.movieadvicer.contracts.IMoviesContract;
import chnu.practice.movieadvicer.models.MovieModel.Movies;
import chnu.practice.movieadvicer.models.MovieModel.Result;
import chnu.practice.movieadvicer.presenters.MoviesPresenter;
import chnu.practice.movieadvicer.ui.adapters.MoviesRecyclerAdapter;

public class MovieActivity extends BaseActivity implements
        MoviesRecyclerAdapter.MoviesItemClickListener, IMoviesContract.IView{

    private MoviesRecyclerAdapter mAdapter;
    private IMoviesContract.IPresenter mPresenter;
    private SwipeRefreshLayout mRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent != null){
            getSupportActionBar().setTitle(intent.getStringExtra(BundleKeys.GENRE_NAME.name()));
        }

        mRefreshLayout = findViewById(R.id.moviesSwipeRefreshLayout);
        mAdapter = new MoviesRecyclerAdapter(this, BundleKeys.MOVIES_BY_GENRE_MODE.ordinal());
        mPresenter = new MoviesPresenter(this);
        RecyclerView mRecyclerView = findViewById(R.id.movieRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void OnMovieClick(Result result) {
            Toast.makeText(this, "need to open new activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnShowMoreClick(int page) {
            mPresenter.loadNextPage(page);
    }

    @Override
    public void OnFavoriteImageClick(Result result) {
        if(!result.isFavorite()){
            result.setFavorite(true);
            mPresenter.addFavorite(result);
        } else {
            result.setFavorite(false);
            mPresenter.removeFavorite(result);
        }
        mPresenter.updateMoviesByGenre();
    }

    @Override
    public void showMovies(Movies movies) {
        mAdapter.setData(movies);
    }

    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
       mRefreshLayout.setRefreshing(false);
    }


}
