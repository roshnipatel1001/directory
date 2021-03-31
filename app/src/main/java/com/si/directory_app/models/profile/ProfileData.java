
package com.si.directory_app.models.profile;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ProfileData {

    @SerializedName("category")
    private List<ProfileProductModel> mCategory;
    @SerializedName("facilities")
    private List<ProfileFacilityModel> mFacilities;
    @SerializedName("gallery")
    private List<ProfileGalleryModel> mGallery;
    @SerializedName("retailer")
    private Retailer mRetailer;
    @SerializedName("spotted_sale")
    private List<SpottedSale> mSpottedSale;
    @SerializedName("staff")
    private List<Staff> mStaff;
    @SerializedName("time")
    private List<Time> mTime;

    public List<ProfileProductModel> getCategory() {
        return mCategory;
    }

    public void setCategory(List<ProfileProductModel> category) {
        mCategory = category;
    }

    public List<ProfileFacilityModel> getFacilities() {
        return mFacilities;
    }

    public void setFacilities(List<ProfileFacilityModel> facilities) {
        mFacilities = facilities;
    }

    public List<ProfileGalleryModel> getGallery() {
        return mGallery;
    }

    public void setGallery(List<ProfileGalleryModel> gallery) {
        mGallery = gallery;
    }

    public Retailer getRetailer() {
        return mRetailer;
    }

    public void setRetailer(Retailer retailer) {
        mRetailer = retailer;
    }

    public List<SpottedSale> getSpottedSale() {
        return mSpottedSale;
    }

    public void setSpottedSale(List<SpottedSale> spottedSale) {
        mSpottedSale = spottedSale;
    }

    public List<Staff> getStaff() {
        return mStaff;
    }

    public void setStaff(List<Staff> staff) {
        mStaff = staff;
    }

    public List<Time> getTime() {
        return mTime;
    }

    public void setTime(List<Time> time) {
        mTime = time;
    }

}
