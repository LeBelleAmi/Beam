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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import lebelleami.com.beam.Model.TvData;
import lebelleami.com.beam.Utils.Url;
import lebelleami.com.beam.R;
import lebelleami.com.beam.TvDetails;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder>{

    private Context context;
    private List<TvData> tvDataList;

    /**
     * Contructor to initialize context and list items.
     *
     * @param applicationContext  Context of the Activity on which RecyclerView is initialised
     * @param tvArrayList List of POJO object that contains the data to update the rows
     */

    public TvAdapter (Context applicationContext, List<TvData> tvArrayList) {
        this.context = applicationContext;
        this.tvDataList = tvArrayList;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public TvAdapter.TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        TvAdapter.TvViewHolder tvViewHolder = new TvAdapter.TvViewHolder(view);
        return tvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TvAdapter.TvViewHolder tvViewHolder, int i) {
        TvData tvData = tvDataList.get(i);
        tvViewHolder.tvTitle.setText(tvData.getName());
        String rating = String.valueOf(tvData.getVote_average());
        tvViewHolder.tvRating.setText(rating);
        tvViewHolder.tvYear.setText(tvData.getFirst_air_date().substring(0,4));
        Glide.with(context).load(Url.posterUrl(tvDataList.get(i).getPoster_path())).into(tvViewHolder.tvImage);


        tvViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = tvViewHolder.getAdapterPosition();
                TvData items = tvDataList.get(pos);
                Bundle args = new Bundle();
                args.putString("movieTitle", tvDataList.get(pos).getName().toString());
                args.putString("movieVoteAverage", tvDataList.get(pos).getVote_average().toString());
                args.putString("movieReleaseDate", tvDataList.get(pos).getFirst_air_date().toString());
                args.putString("movieAdult", tvDataList.get(pos).getOrigin_country().toString());
                args.putString("moviePopularity", tvDataList.get(pos).getPopularity().toString());
                args.putString("movieLanguage", tvDataList.get(pos).getOriginal_language().toString());
                args.putString("tvId", tvDataList.get(pos).getId().toString());
                args.putString("movieVoteCount", tvDataList.get(pos).getVote_count().toString());
                args.putString("movieBackdrop", tvDataList.get(pos).getBackdrop_path().toString());
                args.putString("movieOverview", tvDataList.get(pos).getOverview().toString());
                args.putString("movieGenre", tvDataList.get(pos).getGenre_ids().toString());
                args.putString("moviePoster", tvDataList.get(pos).getPoster_path().toString());


                Intent intent = new Intent(context, TvDetails.class);
                intent.putExtra("items", args);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (tvDataList == null) {
            return 0;
        } else {
            return tvDataList.size();
        }
    }


    public class TvViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        RelativeLayout relativeLayout;
        ImageView tvImage;
        TextView tvTitle;
        TextView tvYear;
        TextView tvRating;


        public TvViewHolder (View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.movie_card);
            relativeLayout = itemView.findViewById(R.id.movie_container);
            tvImage = itemView.findViewById(R.id.movie_thumbnail_image);
            tvYear = itemView.findViewById(R.id.movie_year);
            tvTitle = itemView.findViewById(R.id.movie_title);
            tvRating = itemView.findViewById(R.id.movie_rating);

        }

    }
}

