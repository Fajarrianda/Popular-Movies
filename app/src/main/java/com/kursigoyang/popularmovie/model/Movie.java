package com.kursigoyang.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fajar Rianda on 30/06/2017.
 */
public class Movie implements Parcelable {

  @SerializedName("vote_count") private String voteCount;
  @SerializedName("id") private int id;
  @SerializedName("video") private boolean isVideo;
  @SerializedName("vote_average") private float voteAverage;
  @SerializedName("title") private String title;
  @SerializedName("popularity") private float popularity;
  @SerializedName("poster_path") private String poster;
  @SerializedName("backdrop_path") private String backdrop;
  @SerializedName("overview") private String overview;
  @SerializedName("release_date") private String releaseDate;

  protected Movie(Parcel in) {
    voteCount = in.readString();
    id = in.readInt();
    isVideo = in.readByte() != 0;
    voteAverage = in.readFloat();
    title = in.readString();
    popularity = in.readFloat();
    poster = in.readString();
    backdrop = in.readString();
    overview = in.readString();
    releaseDate = in.readString();
  }

  public static final Creator<Movie> CREATOR = new Creator<Movie>() {
    @Override public Movie createFromParcel(Parcel in) {
      return new Movie(in);
    }

    @Override public Movie[] newArray(int size) {
      return new Movie[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(voteCount);
    parcel.writeInt(id);
    parcel.writeByte((byte) (isVideo
        ? 1
        : 0));
    parcel.writeFloat(voteAverage);
    parcel.writeString(title);
    parcel.writeFloat(popularity);
    parcel.writeString(poster);
    parcel.writeString(backdrop);
    parcel.writeString(overview);
    parcel.writeString(releaseDate);
  }

  public String getBackdrop() {
    return backdrop;
  }

  public void setBackdrop(String backdrop) {
    this.backdrop = backdrop;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isVideo() {
    return isVideo;
  }

  public void setVideo(boolean video) {
    isVideo = video;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public float getPopularity() {
    return popularity;
  }

  public void setPopularity(float popularity) {
    this.popularity = popularity;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public float getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(float voteAverage) {
    this.voteAverage = voteAverage;
  }

  public String getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(String voteCount) {
    this.voteCount = voteCount;
  }
}
