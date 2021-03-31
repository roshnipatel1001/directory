
package com.si.directory_app.models.profile;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ProfileGalleryModel {

    @SerializedName("filepath")
    private String mFilepath;

    public String getFilepath() {
        return mFilepath;
    }

    public void setFilepath(String filepath) {
        mFilepath = filepath;
    }

}
