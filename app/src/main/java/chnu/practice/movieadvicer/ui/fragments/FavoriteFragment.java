package chnu.practice.movieadvicer.ui.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import chnu.practice.movieadvicer.R;
import chnu.practice.movieadvicer.consts.BundleKeys;
import chnu.practice.movieadvicer.contracts.IFavoriteContract;
import chnu.practice.movieadvicer.models.MovieModel.Result;
import chnu.practice.movieadvicer.presenters.FavoritePresenter;
import chnu.practice.movieadvicer.ui.adapters.MoviesRecyclerAdapter;


public class FavoriteFragment extends BaseFragment implements IFavoriteContract.IView,
        MoviesRecyclerAdapter.MoviesItemClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MoviesRecyclerAdapter mAdapter;
    private IFavoriteContract.IPresenter mPresenter;
    private RecyclerView mRecyclerView;

    public FavoriteFragment() {
    }

    public static FavoriteFragment getInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.favoriteSwipeRefreshLayout);
        mRecyclerView = view.findViewById(R.id.favoriteRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new MoviesRecyclerAdapter(this, BundleKeys.FAVORITE_MODE.ordinal());
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new FavoritePresenter(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showMovies(List<Result> favorites) {
        mAdapter.setData(favorites);
    }

    @Override
    public void OnMovieClick(Result result) {

    }

    @Override
    public void OnShowMoreClick(int page) {
    }

    @Override
    public void OnFavoriteImageClick(final Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.delete);
        builder.setMessage(getString(R.string.confirm_remove)+result.title+getString(R.string.from_favorite));
        builder.setIcon(R.drawable.ic_error);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    result.setFavorite(false);
                    mPresenter.removeFavorite(result);
                    showToast(getString(R.string.removed) + result.title);
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

}
