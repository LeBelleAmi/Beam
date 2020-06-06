package lebelleami.com.beam.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

import lebelleami.com.beam.Controller.TvDetails;
import lebelleami.com.beam.Model.Season;
import lebelleami.com.beam.Model.TvData;
import lebelleami.com.beam.R;
import lebelleami.com.beam.Utils.Url;

public class TvDetailsAdapter extends RecyclerView.Adapter<TvDetailsAdapter.TvDetailsViewHolder>{

    private Context context;
    private List<Season> seasonList;

    /**
     * Contructor to initialize context and list items.
     *
     * @param applicationContext  Context of the Activity on which RecyclerView is initialised
     * @param seasonArrayList List of POJO object that contains the data to update the rows
     */

    public TvDetailsAdapter (Context applicationContext, List<Season> seasonArrayList) {
        this.context = applicationContext;
        this.seasonList = seasonArrayList;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public TvDetailsAdapter.TvDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.season_tv_item, viewGroup, false);
        TvDetailsAdapter.TvDetailsViewHolder tvDetailsViewHolder = new TvDetailsAdapter.TvDetailsViewHolder(view);
        return tvDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TvDetailsAdapter.TvDetailsViewHolder tvDetailsViewHolder, int i) {
        Season season = seasonList.get(i);
        tvDetailsViewHolder.tvTitle.setText(season.getName());
        String episode = String.valueOf(season.getEpisodeCount());
        tvDetailsViewHolder.tvRating.setText(episode);
        tvDetailsViewHolder.tvYear.setText(season.getAirDate().substring(0,4));
        Glide.with(context).load(Url.posterUrlSeason(seasonList.get(i).getPosterPath())).into(tvDetailsViewHolder.tvImage);


        /*tvDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = tvDetailsViewHolder.getAdapterPosition();
                Season items = seasonList.get(pos);
                *//*Bundle args = new Bundle();
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
*//*

                //pass the 'context' here
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Season Overview");
                alertDialog.setMessage("your message ");
                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // DO SOMETHING HERE

                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });
        */
    }


    @Override
    public int getItemCount() {
        if (seasonList == null) {
            return 0;
        } else {
            return seasonList.size();
        }
    }


    public class TvDetailsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        RelativeLayout relativeLayout;
        ImageView tvImage;
        TextView tvTitle;
        TextView tvYear;
        TextView tvRating;


        public TvDetailsViewHolder (View itemView){
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


