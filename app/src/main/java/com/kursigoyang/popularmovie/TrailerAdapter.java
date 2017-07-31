package com.kursigoyang.popularmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kursigoyang.popularmovie.model.Trailer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fajar Rianda on 30/06/2017.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {
  Context context;
  private List<Trailer> listData = new ArrayList<>();
  private ListItemClickListener mOnClickListener;

  public TrailerAdapter(Context context) {
    this.context = context;
  }

  public void setOnListItemClickListener(ListItemClickListener listItemClickListener) {
    mOnClickListener = listItemClickListener;
  }

  @Override public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TrailerHolder(
        LayoutInflater.from(context).inflate(R.layout.list_item_trailer, parent, false));
  }

  @Override public void onBindViewHolder(TrailerHolder holder, int position) {
    Trailer trailer = listData.get(position);
    holder.txtTrailer.setText(trailer.getName());
  }

  @Override public int getItemCount() {
    return listData.size();
  }

  public void pushData(List<Trailer> trailers) {
    listData.clear();
    listData.addAll(trailers);
    notifyDataSetChanged();
  }

  public interface ListItemClickListener {
    void onListItemClick(int clickedItemIndex, Trailer trailer);
  }

  class TrailerHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txtTrailer) TextView txtTrailer;

    public TrailerHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.imgTrailer) public void onImageClick() {
      int clickedPosition = getAdapterPosition();
      mOnClickListener.onListItemClick(clickedPosition, listData.get(clickedPosition));
    }
  }
}
