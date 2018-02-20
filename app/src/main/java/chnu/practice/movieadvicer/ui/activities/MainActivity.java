package chnu.practice.movieadvicer.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import chnu.practice.movieadvicer.R;
import chnu.practice.movieadvicer.consts.Constants;
import chnu.practice.movieadvicer.ui.adapters.SectionPageAdapter;
import chnu.practice.movieadvicer.ui.fragments.FavoriteFragment;
import chnu.practice.movieadvicer.ui.fragments.GenresFragment;
import chnu.practice.movieadvicer.ui.fragments.TopFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.view_pager_container);
        setUpViewPager(viewPager);


        final TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPageAdapter pageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        pageAdapter.addFragment(new GenresFragment(), getString(R.string.genres));
        pageAdapter.addFragment(new FavoriteFragment(), getString(R.string.favorite));
        pageAdapter.addFragment(new TopFragment(), getString(R.string.top));
        viewPager.setOffscreenPageLimit(Constants.SAVING_PAGE_LIMIT);
        viewPager.setAdapter(pageAdapter);
    }

}
