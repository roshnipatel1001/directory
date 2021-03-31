
package com.si.directory_app.models.profile;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Staff {

    @SerializedName("name")
    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
