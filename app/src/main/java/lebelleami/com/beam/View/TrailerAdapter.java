package lebelleami.com.beam.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import lebelleami.com.beam.Model.TrailerData;
import lebelleami.com.beam.R;
import lebelleami.com.beam.Utils.Url;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {


    private final static String LOG_TAG = TrailerAdapter.class.getSimpleName();
    private Context context;
    private List<TrailerData> trailerDataList;

    /**
     * Contructor to initialize context and list items.
     *
     * @param applicationContext Context of the Activity on which RecyclerView is initialised
     * @param trailerArrayList   List of POJO object that contains the data to update the rows
     */

    public TrailerAdapter(Context applicationContext, List<TrailerData> trailerArrayList) {
        this.context = applicationContext;
        this.trailerDataList = trailerArrayList;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_list_item, viewGroup, false);
        TrailerAdapter.TrailerViewHolder trailerViewHolder = new TrailerAdapter.TrailerViewHolder(view);
        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailerAdapter.TrailerViewHolder trailerViewHolder, int i) {
        TrailerData trailerData = trailerDataList.get(i);
        trailerViewHolder.trailerTitle.setText(trailerData.getName());
        String thumbnailUrl = "https://img.youtube.com/vi/" + trailerData.getKey() + "/0.jpg";
        Glide.with(context).load(thumbnailUrl).into(trailerViewHolder.movieTrailer);
        Log.i(LOG_TAG,"thumbnailUrl -> " + thumbnailUrl);

        trailerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = trailerViewHolder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    TrailerData clickedMovie = trailerDataList.get(pos);
                    String videoId = trailerDataList.get(pos).getKey();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId));
                    intent.putExtra("Video ID", videoId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (trailerDataList == null) {
            return 0;
        } else {
            return trailerDataList.size();
        }
    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        ImageView movieTrailer, playVideo;
        TextView trailerTitle;


        public TrailerViewHolder(View itemView) {
            super(itemView);
            playVideo = itemView.findViewById(R.id.movie_play);
            movieTrailer = itemView.findViewById(R.id.movie_thumbnail_trailer);
            trailerTitle = itemView.findViewById(R.id.trailer_video_title);

        }

    }

}
