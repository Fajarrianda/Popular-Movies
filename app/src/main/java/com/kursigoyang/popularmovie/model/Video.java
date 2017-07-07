package com.kursigoyang.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fajar Rianda on 06/07/2017.
 */
public class Video implements Parcelable {

  @SerializedName("id") private String id;
  @SerializedName("key") private String key;
  @SerializedName("name") private String name;
  @SerializedName("site") private String site;
  @SerializedName("size") private int size;
  @SerializedName("type") private String type;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  protected Video(Parcel in) {
    id = in.readString();
    key = in.readString();
    name = in.readString();
    site = in.readString();
    size = in.readInt();
    type = in.readString();
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(key);
    dest.writeString(name);
    dest.writeString(site);
    dest.writeInt(size);
    dest.writeString(type);
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<Video> CREATOR = new Creator<Video>() {
    @Override public Video createFromParcel(Parcel in) {
      return new Video(in);
    }

    @Override public Video[] newArray(int size) {
      return new Video[size];
    }
  };
}
