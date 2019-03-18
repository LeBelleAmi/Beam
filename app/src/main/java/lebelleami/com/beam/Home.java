package lebelleami.com.beam;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import lebelleami.com.beam.View.ViewPagerAdapter;

public class Home extends AppCompatActivity {

    //private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    //private ViewPager viewPager;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        appBarLayout = findViewById(R.id.appbar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Beam");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ViewPager viewPager = findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    //add fragments
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MoviesFragment(), "MOVIES");
        adapter.addFragment(new TheatersFragment(), "THEATERS");
        adapter.addFragment(new UpcomingFragment(), "UPCOMING");
        adapter.addFragment(new SeriesFragment(), "TV SERIES");
        adapter.addFragment(new AiringFragment(), "AIRING TODAY");
        adapter.addFragment(new PopularFragment(), "POPULAR");

        //set up adapter
        viewPager.setAdapter(adapter);
    }


}

