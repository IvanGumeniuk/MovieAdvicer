package chnu.practice.movieadvicer.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chnu.practice.movieadvicer.R;
import chnu.practice.movieadvicer.app.Navigation;
import chnu.practice.movieadvicer.contracts.IGenresContract;
import chnu.practice.movieadvicer.models.GenreModel.Genre;
import chnu.practice.movieadvicer.models.GenreModel.Genres;
import chnu.practice.movieadvicer.presenters.GenresPresenter;
import chnu.practice.movieadvicer.ui.adapters.GenreRecyclerAdapter;

public class GenresFragment extends BaseFragment implements IGenresContract.IView,
        GenreRecyclerAdapter.GenreRecyclerAdapterInterface {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GenreRecyclerAdapter mAdapter;
    private IGenresContract.IPresenter presenter;
    private RecyclerView recyclerView;

    public GenresFragment() {
    }

    public static GenresFragment getInstance(String data) {
        GenresFragment fragment = new GenresFragment();
        Bundle bundle = new Bundle();
        bundle.putString("arg", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genres, container, false);

        Bundle bundle = getArguments();

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.genre_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GenreRecyclerAdapter(this);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new GenresPresenter(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.genresRequest();
            }
        });
        presenter.genresRequest();
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
    public void showGenres(Genres genres) {
        mAdapter.setData(genres);
    }

    @Override
    public void toMovieActivity() {
        Navigation.toMovieScreen(getActivity());
    }

    @Override
    public Activity getViewActivity() {
            return getActivity();
    }

    @Override
    public void onGenreClick(Genre genre) {
        presenter.moviesRequest(genre.getId());
    }


}
