package com.pinkhatapps.masterdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("albumId")
    @Expose
    public int albumId;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("thumbnailUrl")
    @Expose
    public String thumbnailUrl;
}
