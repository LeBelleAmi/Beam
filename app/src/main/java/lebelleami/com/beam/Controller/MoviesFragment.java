package lebelleami.com.beam.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MoviesFragment extends Fragment {

    View view;

    private RecyclerView recyclerView;
    /*recycler view layout manager*/
    LinearLayoutManager llm;
    private MovieAdapter movieAdapter;
    private List<MovieData> movieDataList;
    Movie movie;
    private boolean connected;


    public MoviesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_layout_fragment, container, false);
        connected();
        initViews();
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
    }


    private void loadMovieData() {
        try {
            Service apiService =
                    Client.getClient().create(Service.class);
            Call<Movie> call = apiService.getPopularMovieData(Url.API_KEY);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful()) {

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


    private void connected() {
        connected = isNetworkConnected(getActivity().getApplicationContext());
        if (!connected) {
            // showing snack bar with network option
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(R.id.main_content), "You seem to be Offline, please check connection!", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // retry is selected, refresh the app
                    loadMovieData();
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);

            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            snackbar.show();
        } else {
            loadMovieData();
        }
    }


    public static boolean isNetworkConnected(Context context) {
        boolean result = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = true;
                    }
                }
            }
        } else {
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        result = true;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

}
