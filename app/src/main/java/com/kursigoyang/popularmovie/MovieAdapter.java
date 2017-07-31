package com.kursigoyang.popularmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.kursigoyang.popularmovie.constant.URLCons;
import com.kursigoyang.popularmovie.model.Movie;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fajar Rianda on 30/06/2017.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
  Context context;
  private List<Movie> listData = new ArrayList<>();
  private ListItemClickListener mOnClickListener;

  public MovieAdapter(Context context) {
    this.context = context;
  }

  public void setOnListItemClickListener(ListItemClickListener listItemClickListener) {
    mOnClickListener = listItemClickListener;
  }

  @Override public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new MovieHolder(
        LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false));
  }

  @Override public void onBindViewHolder(MovieHolder holder, int position) {
    Movie movie = listData.get(position);
    Glide.with(context)
        .load(URLCons.URL_BASE_GET_IMAGE.concat(movie.getPoster()))
        .into(holder.imgContent);
  }

  @Override public int getItemCount() {
    return listData.size();
  }

  public void clearListData() {
    listData.clear();
    notifyDataSetChanged();
  }


  public void pushData(List<Movie> movies) {
    listData.clear();
    listData.addAll(movies);
    notifyDataSetChanged();
  }

  public interface ListItemClickListener {
    void onListItemClick(int clickedItemIndex, Movie movie);
  }

  class MovieHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.imgContent) ImageView imgContent;

    public MovieHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.imgContent) public void onImageClick() {
      int clickedPosition = getAdapterPosition();
      mOnClickListener.onListItemClick(clickedPosition, listData.get(clickedPosition));
    }
  }
}
