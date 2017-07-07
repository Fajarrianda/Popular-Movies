package com.kursigoyang.popularmovie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fajar Rianda on 30/06/2017.
 */
public class Responses<T> {

  @SerializedName("results") T result;
  @SerializedName("page") private int page;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }
}
