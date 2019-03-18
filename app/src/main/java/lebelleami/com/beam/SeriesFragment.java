package lebelleami.com.beam;

import android.content.res.Configuration;
import android.graphics.Color;
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
import lebelleami.com.beam.Utils.Url;
import lebelleami.com.beam.Model.Tv;
import lebelleami.com.beam.View.TvAdapter;
import lebelleami.com.beam.Model.TvData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesFragment extends Fragment {

    View view;

    private RecyclerView recyclerView;
    /*recycler view layout manager*/
    LinearLayoutManager llm;
    private TvAdapter tvAdapter;
    private List<TvData> tvData;
    Tv tv;

    public SeriesFragment(){

    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.list_layout_fragment, container, false);

        initViews();
        loadTvData();
        tvData = new ArrayList<>();


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
        recyclerView.setAdapter(tvAdapter);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));

        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 4));
        }
    }


    private void loadTvData() {
        try {
            Service apiService =
                    Client.getClient().create(Service.class);
            Call<Tv> call = apiService.getPopularTvData(Url.API_KEY);
            call.enqueue(new Callback<Tv>() {
                @Override
                public void onResponse(Call<Tv> call, Response<Tv> response) {
                    if (response.isSuccessful()) {

                        //Log.i(TAG, "movies: " + response.body().getResults().toString());
                        //Toast.makeText(getActivity().getApplicationContext(), response.body().toString() + "string", Toast.LENGTH_LONG).show();
                        tv = response.body();
                        List<TvData> tvList = tv.getResults();
                        tvAdapter = new TvAdapter(getActivity().getApplicationContext(), tvList);
                        recyclerView.smoothScrollToPosition(0);
                        recyclerView.setAdapter(tvAdapter);
                        tvAdapter.notifyDataSetChanged();
                    }
                }


                @Override
                public void onFailure(Call<Tv> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    // showing snack bar with response failure option
                    Snackbar snackbar = Snackbar
                            .make(getActivity().findViewById(R.id.main_content), "Movie data loading failed, please refresh!", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("REFRESH", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // refresh is selected, refresh the app
                            loadTvData();
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
