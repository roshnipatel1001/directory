package com.si.directory_app.utills;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;


import com.si.directory_app.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageAdapter {
    private static final String TAG = "ImageAdapter";

    private static final int profileSquareDrawable = R.drawable.ic_user_dummy;
    private static final int profileRoundDrawable = R.drawable.ic_user_dummy;
    private static final int BannerDrawable = R.drawable.ic_user_dummy;

    @BindingAdapter({"profile"})
    public static void profile (ImageView view, String imageUrl){
        Log.e(TAG, "profile: "+imageUrl );
        imageUrl =  imageUrl.replaceAll(" ", "%20");
        Picasso.get()
                .load(imageUrl)
                .fit()
                .placeholder(profileSquareDrawable)
                .error(profileSquareDrawable)
                .centerCrop()
                .into(view);
    }

    @BindingAdapter({"profile"})
    public static void profile (CircleImageView view, String imageUrl){
        Log.e(TAG, "profile: "+imageUrl );
        imageUrl =  imageUrl.replaceAll(" ", "%20");
        Picasso.get()
                .load(imageUrl)
                .fit()
                .placeholder(profileRoundDrawable)
                .error(profileRoundDrawable)
                .centerCrop()
                .into(view);
    }

    @BindingAdapter({"banners"})
    public static void banners(ImageView view, String imageUrl){
        Log.e(TAG, "banners: "+imageUrl );
        imageUrl =  imageUrl.replaceAll(" ", "%20");

        Picasso.get()
                .load(imageUrl)
                .fit()
                .placeholder(BannerDrawable)
                .error(BannerDrawable)
                .centerCrop()
                .into(view);
    }

    public static void loadProfileImageFile(ImageView view, File imageUrl){
        Log.e(TAG, "loadProfileImageFile: "+imageUrl );
        Picasso.get()
                .load(imageUrl)
                .fit()
                .placeholder(profileSquareDrawable)
                .error(profileSquareDrawable)
                .centerCrop()
                .into(view);
    }

    public static void loadProfileImageFile(CircleImageView view, File imageUrl){
        Log.e(TAG, "loadProfileImageFile: "+imageUrl );
        Picasso.get()
                .load(imageUrl)
                .fit()
                .placeholder(profileRoundDrawable)
                .error(profileRoundDrawable)
                .centerCrop()
                .into(view);
    }

    public static void loadBannerImageFile(ImageView view, File imageUrl){
        Log.e(TAG, "loadBannerImageFile: "+imageUrl );
        Picasso.get()
                .load(imageUrl)
                .fit()
                .placeholder(BannerDrawable)
                .error(BannerDrawable)
                .centerCrop()
                .into(view);
    }
}
