package com.kursigoyang.popularmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.kursigoyang.popularmovie.model.Review;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fajar Rianda on 30/06/2017.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
  Context context;
  private List<Review> listData = new ArrayList<>();
  private ListItemClickListener mOnClickListener;

  public ReviewAdapter(Context context) {
    this.context = context;
  }

  public void setOnListItemClickListener(ListItemClickListener listItemClickListener) {
    mOnClickListener = listItemClickListener;
  }

  @Override public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ReviewHolder(
        LayoutInflater.from(context).inflate(R.layout.list_item_review, parent, false));
  }

  @Override public void onBindViewHolder(ReviewHolder holder, int position) {
    Review review = listData.get(position);
    holder.txtAuthor.setText(review.getAuthor());
    holder.txtReview.setText(review.getContent());
  }

  @Override public int getItemCount() {
    return listData.size();
  }

  public void pushData(List<Review> Reviews) {
    listData.clear();
    listData.addAll(Reviews);
    notifyDataSetChanged();
  }

  public interface ListItemClickListener {
    void onListItemClick(int clickedItemIndex, Review Review);
  }

  class ReviewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txtAuthor) TextView txtAuthor;
    @Bind(R.id.txtReview) TextView txtReview;

    public ReviewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    //@OnClick(R.id.imgReview) public void onImageClick() {
    //  int clickedPosition = getAdapterPosition();
    //  mOnClickListener.onListItemClick(clickedPosition, listData.get(clickedPosition));
    //}
  }
}
