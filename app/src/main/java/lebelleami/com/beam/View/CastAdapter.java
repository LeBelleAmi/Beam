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
import lebelleami.com.beam.Utils.Url;
import lebelleami.com.beam.R;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private Context context;
    private List<CastData> castDataList;

    /**
     * Contructor to initialize context and list items.
     *
     * @param applicationContext Context of the Activity on which RecyclerView is initialised
     * @param castArrayList   List of POJO object that contains the data to update the rows
     */

    public CastAdapter(Context applicationContext, List<CastData> castArrayList) {
        this.context = applicationContext;
        this.castDataList = castArrayList;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public CastAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_list_item, viewGroup, false);
        CastAdapter.CastViewHolder castViewHolder = new CastAdapter.CastViewHolder(view);
        return castViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CastAdapter.CastViewHolder castViewHolder, int i) {
        CastData castData = castDataList.get(i);
        castViewHolder.castName.setText(castData.getName());
        castViewHolder.castCharacter.setText(castData.getCharacter());
        Glide.with(context).load(Url.posterProfile(castDataList.get(i).getProfile_path())).into(castViewHolder.castPhoto);

    }


    @Override
    public int getItemCount() {
        if (castDataList == null) {
            return 0;
        } else {
            return castDataList.size();
        }
    }


    public class CastViewHolder extends RecyclerView.ViewHolder {
        TextView castName, castCharacter;
        ImageView castPhoto;


        public CastViewHolder (View itemView) {
            super(itemView);
            castName = itemView.findViewById(R.id.cast_name);
            castCharacter = itemView.findViewById(R.id.cast_character);
            castPhoto = itemView.findViewById(R.id.cast_thumbnail_image);

        }

    }

}
