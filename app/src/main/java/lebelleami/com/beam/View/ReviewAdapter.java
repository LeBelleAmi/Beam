package lebelleami.com.beam.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lebelleami.com.beam.Model.ReviewData;
import lebelleami.com.beam.R;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private List<ReviewData> reviewDataList;

    /**
     * Contructor to initialize context and list items.
     *
     * @param applicationContext Context of the Activity on which RecyclerView is initialised
     * @param reviewArrayList   List of POJO object that contains the data to update the rows
     */

    public ReviewAdapter(Context applicationContext, List<ReviewData> reviewArrayList) {
        this.context = applicationContext;
        this.reviewDataList = reviewArrayList;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_item, viewGroup, false);
        ReviewAdapter.ReviewViewHolder reviewViewHolder = new ReviewAdapter.ReviewViewHolder(view);
        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewAdapter.ReviewViewHolder reviewViewHolder, int i) {
        ReviewData reviewData = reviewDataList.get(i);
        reviewViewHolder.reviewerName.setText(reviewData.getAuthor());
        reviewViewHolder.reviewerText.setText(reviewData.getContent());

    }


    @Override
    public int getItemCount() {
        if (reviewDataList == null) {
            return 0;
        } else {
            return reviewDataList.size();
        }
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewerName, reviewerText;


        public ReviewViewHolder (View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.movie_reviewer);
            reviewerText = itemView.findViewById(R.id.movie_review_text);

        }

    }
}
