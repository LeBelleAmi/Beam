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

import lebelleami.com.beam.Model.CastData;
import lebelleami.com.beam.Model.TvCast;
import lebelleami.com.beam.R;
import lebelleami.com.beam.Utils.Url;

public class TvCastAdapter extends RecyclerView.Adapter<TvCastAdapter.TvCastViewHolder> {

    private Context context;
    private List<TvCast> tvcastDataList;

    /**
     * Contructor to initialize context and list items.
     *
     * @param applicationContext Context of the Activity on which RecyclerView is initialised
     * @param tvcastArrayList   List of POJO object that contains the data to update the rows
     */

    public TvCastAdapter(Context applicationContext, List<TvCast> tvcastArrayList) {
        this.context = applicationContext;
        this.tvcastDataList = tvcastArrayList;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public TvCastAdapter.TvCastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_list_item, viewGroup, false);
        TvCastAdapter.TvCastViewHolder tvcastViewHolder = new TvCastAdapter.TvCastViewHolder(view);
        return tvcastViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TvCastAdapter.TvCastViewHolder tvcastViewHolder, int i) {
        TvCast tvcastData = tvcastDataList.get(i);
        tvcastViewHolder.castName.setText(tvcastData.getName());
        tvcastViewHolder.castCharacter.setText(tvcastData.getCharacter());
        Glide.with(context).load(Url.posterProfile(tvcastDataList.get(i).getProfilePath())).into(tvcastViewHolder.castPhoto);

    }


    @Override
    public int getItemCount() {
        if (tvcastDataList == null) {
            return 0;
        } else {
            return tvcastDataList.size();
        }
    }


    public class TvCastViewHolder extends RecyclerView.ViewHolder {
        TextView castName, castCharacter;
        ImageView castPhoto;


        public TvCastViewHolder (View itemView) {
            super(itemView);
            castName = itemView.findViewById(R.id.cast_name);
            castCharacter = itemView.findViewById(R.id.cast_character);
            castPhoto = itemView.findViewById(R.id.cast_thumbnail_image);

        }

    }

}

