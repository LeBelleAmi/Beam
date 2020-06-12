package lebelleami.com.beam.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import lebelleami.com.beam.Database.MovieEntry;
import lebelleami.com.beam.R;
import lebelleami.com.beam.Utils.Url;

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.FavouriteMovieViewHolder> {

    private Context context;
    private List<MovieEntry> movieEntryDataList;

    /**
     * Contructor to initialize context and list items.
     *
     * @param applicationContext Context of the Activity on which RecyclerView is initialised
     * @param favMovieArrayList     List of POJO object that contains the data to update the rows
     */

    public FavouriteMovieAdapter(Context applicationContext, List<MovieEntry> favMovieArrayList) {
        this.context = applicationContext;
        this.movieEntryDataList = favMovieArrayList;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public FavouriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new FavouriteMovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMovieViewHolder holder, int position) {
        MovieEntry item = movieEntryDataList.get(position);
            holder.bindTo(item);
    }

    @Override
    public int getItemCount() {
        if ( movieEntryDataList == null) {
            return 0;
        } else {
            return  movieEntryDataList.size();
        }
    }

    public void setMovieEntries(List<MovieEntry> movieEntries) {
        movieEntryDataList = movieEntries;
        notifyDataSetChanged();
    }


    public static class FavouriteMovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private TextView mMovieName;
        private TextView mMovieYear;
        private TextView mMovieRating;
        private MovieEntry mMovieEntry;

        FavouriteMovieViewHolder (View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.movie_thumbnail_image);
            mMovieName = itemView.findViewById(R.id.movie_title);
            mMovieYear = itemView.findViewById(R.id.movie_year);
            mMovieRating = itemView.findViewById(R.id.movie_rating);
        }

        public MovieEntry getMovie() {
            return mMovieEntry;
        }


        void bindTo(MovieEntry movieEntry) {
            mMovieEntry = movieEntry;
            mMovieName.setText(movieEntry.getTitle());
            String rating = String.valueOf(movieEntry.getVote_average());
            mMovieRating.setText(rating);
            mMovieYear.setText(movieEntry.getRelease_date().substring(0,4));
            Glide.with(mImage.getContext()).load(Url.posterUrl(movieEntry.getPoster_path())).into(mImage);
        }

    }


}
