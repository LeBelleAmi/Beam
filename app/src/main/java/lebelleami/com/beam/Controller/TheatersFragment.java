package lebelleami.com.beam.Controller;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lebelleami.com.beam.Api.Client;
import lebelleami.com.beam.Api.Service;
import lebelleami.com.beam.R;
import lebelleami.com.beam.Utils.Url;
import lebelleami.com.beam.Model.Movie;
import lebelleami.com.beam.Model.MovieData;
import lebelleami.com.beam.View.MovieAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheatersFragment extends Fragment {

    View view;

    private SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar load_more;
    TextView emptyState;


    private RecyclerView recyclerView;
    /*recycler view layout manager*/
    LinearLayoutManager llm;
    private MovieAdapter movieAdapter;
    private List<MovieData> movieDataList;
    Movie movie;

    //pagination constants
    // boolean for awaiting data loading
    private boolean loading = true;
    // The minimum amount of items to have below your current scroll position before loading more
    private int visibleThreshold = 10;

    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;

    public int current_page = 1;



    public TheatersFragment(){

    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.list_layout_fragment, container, false);

        load_more = view.findViewById(R.id.progress_bar);
        emptyState = view.findViewById(R.id.empty_state);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                current_page = 1;
                loadMovieData();
                Toast.makeText(getActivity().getApplicationContext(), "Refreshing Beam", Toast.LENGTH_SHORT).show();
            }
        });

        initViews();
        loadMovieData();
        movieDataList = new ArrayList<>();

        return view;
    }

    public void initViews() {
        // set up recycler view
        recyclerView = view.findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(movieAdapter);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));

        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 4));
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //Get the integer values of the no of items in the screen, the total item available and the
                //items already seen and save them in the corresponding variables.
                visibleItemCount = llm.getChildCount();
                totalItemCount = llm.getItemCount();
                firstVisibleItem = llm.findFirstVisibleItemPosition();

                if (dy > 0) //check for scroll down
                {

                    //If loading is true as you scroll down, and you haven't gotten to the end
                    //of the list, assign loading to false.
                    //
                    if (loading && (totalItemCount > previousTotal)) {
                        loading = false;
                        previousTotal = totalItemCount;

                    }

                    //if loading is false and you are at the end of the list increment current_page
                    //by 1 and then check if you are not at the last page using the value in current_page
                    //if it is true, then pass the current page number to the Url and fetch the data from the
                    //internet and then update the info displayed in the RV if successful.
                    if (loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        current_page ++;
                        if (current_page < 10) {
                            //show the progress bar for reloading.
                            load_more.setVisibility(View.VISIBLE);
                            loadMovieData();
                            loading = false;
                        } else {
                            Snackbar snackbar =
                                    Snackbar.make(getActivity().findViewById(R.id.main_content), "No more Movies to disp" +
                                            "lay, Probably end of the List!", Snackbar.LENGTH_INDEFINITE).
                                            setAction("Dismiss", new View.OnClickListener(){
                                                @Override
                                                public void onClick(View view){}});

                            snackbar.setActionTextColor(Color.YELLOW);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            snackbar.show();
                            loading = true;
                            load_more.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

    }


    private void loadMovieData() {
        try {
            Service apiService =
                    Client.getClient().create(Service.class);
            Call<Movie> call = apiService.getTheaterMovieData(Url.API_KEY, current_page);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful()) {

                        swipeRefreshLayout.setRefreshing(false);
                        load_more.setVisibility(View.GONE);
                        emptyState.setVisibility(View.GONE);

                        //Log.i(TAG, "movies: " + response.body().getResults().toString());
                        //Toast.makeText(getActivity().getApplicationContext(), response.body().toString() + "string", Toast.LENGTH_LONG).show();
                        movie = response.body();
                        List<MovieData> movieList = movie.getResults();
                        movieAdapter = new MovieAdapter(getActivity().getApplicationContext(), movieList);
                        recyclerView.smoothScrollToPosition(0);
                        recyclerView.setAdapter(movieAdapter);
                        movieAdapter.notifyDataSetChanged();
                    }
                }


                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    emptyState.setVisibility(View.VISIBLE);
                    // showing snack bar with response failure option
                    Snackbar snackbar = Snackbar
                            .make(getActivity().findViewById(R.id.main_content), "Movie data loading failed, please refresh!", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("REFRESH", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // refresh is selected, refresh the app
                            loadMovieData();
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    snackbar.show();
                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());

            Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
