package com.si.directory_app.adapters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.UiThread;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.si.directory_app.R;
import com.si.directory_app.databinding.ProfileGalleryCellBinding;
import com.si.directory_app.models.profile.ProfileGalleryModel;
import com.si.directory_app.models.profile.Retailer;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.ImageAdapter;

import java.util.List;

public class ProfileGalleryAdapter extends RecyclerView.Adapter<ProfileGalleryAdapter.ViewHolder> {


    private final Context mContext;
    private List<ProfileGalleryModel> mProfileGalleryModelList;
    private List<Retailer> retailerList;
    private OnItemClickListener onItemClickListener;
    private int currentPos = -1;
    private static final String TAG = "ProfileGalleryAdapter";
    private Retailer retailer;

    public ProfileGalleryAdapter(Context mContext, List<ProfileGalleryModel> mProfileGalleryModelList,Retailer retailer) {
        this.mProfileGalleryModelList = mProfileGalleryModelList;
        this.mContext = mContext;
        this.retailer = retailer;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ProfileGalleryCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.profile_gallery_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ProfileGalleryModel mProfileGalleryModel = this.mProfileGalleryModelList.get(position);
        holder.bind(mProfileGalleryModel);


        String url = Retro.IMAGE_BASEURL + "gellery" + "/" + retailer.getId()+ "/" + mProfileGalleryModel.getFilepath();

        Log.e(TAG, "onBindViewHolder: " + url);

        ImageAdapter.profile(holder.binding.ivImage, url);




        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return  mProfileGalleryModelList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<ProfileGalleryModel> getProfileGalleryModel() {
        return mProfileGalleryModelList;
    }

    public void addChatMassgeModel(ProfileGalleryModel mProfileGalleryModel) {
        try {
            this.mProfileGalleryModelList.add(mProfileGalleryModel);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProfileGalleryModelList(List<ProfileGalleryModel> mProfileGalleryModelList,Retailer retailer) {
        this.mProfileGalleryModelList = mProfileGalleryModelList;
        this.retailer = retailer;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ProfileGalleryCellBinding binding;

        public ViewHolder(final View view, final ProfileGalleryCellBinding binding) {
            super(view);
            this.binding = binding;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mProfileGalleryModelList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final ProfileGalleryModel mProfileGalleryModel) {
            this.binding.setProfileGalleryModel(mProfileGalleryModel);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mProfileGalleryModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mProfileGalleryModelList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mProfileGalleryModelList.size();
        mProfileGalleryModelList.clear();
        notifyItemRangeRemoved(0, size);
    }

}