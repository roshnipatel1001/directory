
package com.si.directory_app.models.profile;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ProfileFacilityModel {

    @SerializedName("title")
    private String mTitle;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
