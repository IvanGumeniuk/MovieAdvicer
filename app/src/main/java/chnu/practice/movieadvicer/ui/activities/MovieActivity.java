package chnu.practice.movieadvicer.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import chnu.practice.movieadvicer.R;
import chnu.practice.movieadvicer.contracts.IMoviesContract;
import chnu.practice.movieadvicer.models.MovieModel.Movies;
import chnu.practice.movieadvicer.models.MovieModel.Result;
import chnu.practice.movieadvicer.presenters.MoviesPresenter;
import chnu.practice.movieadvicer.ui.adapters.MoviesRecyclerAdapter;

public class MovieActivity extends BaseActivity implements
        MoviesRecyclerAdapter.MoviesItemClickListener, IMoviesContract.IView{

    private RecyclerView mRecyclerView;
    private MoviesRecyclerAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private IMoviesContract.IPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mAdapter = new MoviesRecyclerAdapter(this);
        mPresenter = new MoviesPresenter(this);
        mRecyclerView = findViewById(R.id.movieRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void OnClick(Result result) {
            Toast.makeText(this, "need to open new activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnClick(int page) {
            Toast.makeText(this, String.valueOf(page), Toast.LENGTH_SHORT).show();
            mPresenter.loadNextPage(page);
    }


    @Override
    public void showMovies(Movies movies) {
        mAdapter.setData(movies);
    }

    @Override
    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
