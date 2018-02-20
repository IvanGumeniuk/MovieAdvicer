package chnu.practice.movieadvicer.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chnu.practice.movieadvicer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {


    public TopFragment() {
    }

    public static TopFragment getInstance(){
        TopFragment fragment = new TopFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

}
