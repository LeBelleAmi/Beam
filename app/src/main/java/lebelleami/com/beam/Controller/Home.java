package lebelleami.com.beam.Controller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import lebelleami.com.beam.R;
import lebelleami.com.beam.View.ViewPagerAdapter;

public class Home extends AppCompatActivity {

    //private TabLayout tabLayout;
    //private ViewPager viewPager;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    LinearLayout layoutFabVideo, layoutFabTicket, layoutFabFavourite, layoutFabSettings, layoutFabWatchlist, buttonFrame;
    FrameLayout fabframeLayout;
    FloatingActionButton fabMenu, fabVideo, fabTicket, fabFavourite, fabWatchlist, fabSettings;
    private boolean fabExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fabMenu = this.findViewById(R.id.fabMenu);
        fabVideo = this.findViewById(R.id.fabVideo);
        fabTicket = this.findViewById(R.id.fabTicket);
        fabFavourite = this.findViewById(R.id.fabPhoto);
        fabWatchlist = this.findViewById(R.id.fabWatchlistIcon);
        fabSettings = this.findViewById(R.id.fabSettingIcon);

        buttonFrame = this.findViewById(R.id.mainFrame);
        layoutFabVideo = this.findViewById(R.id.layoutFabVideo);
        layoutFabTicket = this.findViewById(R.id.layoutFabTicket);
        layoutFabFavourite = this.findViewById(R.id.layoutFabFavourite);
        layoutFabSettings = this.findViewById(R.id.layoutFabSettings);
        layoutFabWatchlist = this.findViewById(R.id.layoutFabWatchlist);
        fabframeLayout = this.findViewById(R.id.fabFrame);


        //When main Fab (Settings) is clicked, it expands if not expanded already.
        //Collapses if main FAB was open already.
        //This gives FAB (Settings) open/close behavior
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();


        //extra fab buttons
        fabVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Video", Toast.LENGTH_LONG).show();
            }
        });

        fabTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Ticket", Toast.LENGTH_LONG).show();
            }
        });

        fabWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Watchlist", Toast.LENGTH_LONG).show();
            }
        });

        fabFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Favourite", Toast.LENGTH_LONG).show();
            }
        });

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(Home.this, Settings.class);
                Home.this.startActivity(settingsIntent);

            }
        });


        //app toolbar
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

    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabVideo.setVisibility(View.INVISIBLE);
        layoutFabTicket.setVisibility(View.INVISIBLE);
        layoutFabFavourite.setVisibility(View.INVISIBLE);
        layoutFabSettings.setVisibility(View.INVISIBLE);
        layoutFabWatchlist.setVisibility(View.INVISIBLE);
        buttonFrame.setBackgroundResource(0);
        fabMenu.setImageResource(R.drawable.popcorn);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabVideo.setVisibility(View.VISIBLE);
        layoutFabTicket.setVisibility(View.VISIBLE);
        layoutFabFavourite.setVisibility(View.VISIBLE);
        layoutFabSettings.setVisibility(View.VISIBLE);
        layoutFabWatchlist.setVisibility(View.VISIBLE);
        buttonFrame.setBackground(getResources().getDrawable(R.color.colorBackground));
        //Change settings icon to 'X' icon
        fabMenu.setImageResource(R.drawable.ic_close);
        fabExpanded = true;
    }


}

