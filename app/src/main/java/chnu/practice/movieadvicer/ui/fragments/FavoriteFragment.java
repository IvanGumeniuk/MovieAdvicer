package chnu.practice.movieadvicer.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chnu.practice.movieadvicer.R;


public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
    }

    public static FavoriteFragment getInstance(){
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        return view;
    }

}
