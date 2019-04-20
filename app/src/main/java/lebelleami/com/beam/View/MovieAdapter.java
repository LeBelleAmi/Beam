package lebelleami.com.beam.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

import lebelleami.com.beam.Utils.Url;
import lebelleami.com.beam.Model.MovieData;
import lebelleami.com.beam.Controller.MovieDetails;
import lebelleami.com.beam.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private Context context;
    private List<MovieData> movieDataList;

    /**
     * Contructor to initialize context and list items.
     *
     * @param applicationContext  Context of the Activity on which RecyclerView is initialised
     * @param movieArrayList List of POJO object that contains the data to update the rows
     */

    public MovieAdapter (Context applicationContext, List<MovieData> movieArrayList) {
        this.context = applicationContext;
        this.movieDataList = movieArrayList;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MovieViewHolder movieViewHolder, int i) {
        MovieData movieData = movieDataList.get(i);
        movieViewHolder.movieTitle.setText(movieData.getTitle());
        String rating = String.valueOf(movieData.getVote_average());
        movieViewHolder.movieRating.setText(rating);
        movieViewHolder.movieYear.setText(movieData.getRelease_date().substring(0,4));
        Glide.with(context).load(Url.posterUrl(movieDataList.get(i).getPoster_path())).into(movieViewHolder.movieImage);


        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = movieViewHolder.getAdapterPosition();
                MovieData items = movieDataList.get(pos);
                Bundle args = new Bundle();
                args.putString("movieTitle", movieDataList.get(pos).getTitle().toString());
                args.putString("movieVoteAverage", movieDataList.get(pos).getVote_average().toString());
                args.putString("movieReleaseDate", movieDataList.get(pos).getRelease_date().toString());
                args.putString("movieAdult", movieDataList.get(pos).getAdult().toString());
                args.putString("moviePopularity", movieDataList.get(pos).getPopularity().toString());
                args.putString("movieLanguage", movieDataList.get(pos).getOriginal_language().toString());
                args.putString("movieId", movieDataList.get(pos).getId().toString());
                args.putString("movieVoteCount", movieDataList.get(pos).getVote_count().toString());
                args.putString("movieBackdrop", movieDataList.get(pos).getBackdrop_path().toString());
                args.putString("movieOverview", movieDataList.get(pos).getOverview().toString());
                args.putString("movieGenre", movieDataList.get(pos).getGenre_ids().toString());
                args.putString("moviePoster", movieDataList.get(pos).getPoster_path().toString());


                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("items", args);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        if (movieDataList == null) {
            return 0;
        } else {
            return movieDataList.size();
        }
    }



    public class MovieViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        RelativeLayout relativeLayout;
        LinearLayout linearLayout;
        ImageView movieImage;
        TextView movieTitle;
        TextView movieYear;
        TextView movieRating;


        public MovieViewHolder (View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.movie_card);
            relativeLayout = itemView.findViewById(R.id.movie_container);
            movieImage = itemView.findViewById(R.id.movie_thumbnail_image);
            movieYear = itemView.findViewById(R.id.movie_year);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieRating = itemView.findViewById(R.id.movie_rating);
        }


    }
}
