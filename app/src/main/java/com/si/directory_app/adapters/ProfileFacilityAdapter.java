package com.si.directory_app.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.UiThread;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.si.directory_app.R;
import com.si.directory_app.databinding.ProfileFacilityCellBinding;
import com.si.directory_app.models.profile.ProfileFacilityModel;

import java.util.List;

public class ProfileFacilityAdapter extends RecyclerView.Adapter<ProfileFacilityAdapter.ViewHolder> {


    private final Context mContext;
    private List<ProfileFacilityModel> mProfileFacilityModelList;
    private OnItemClickListener onItemClickListener;
    private int currentPos = -1;

    public ProfileFacilityAdapter(Context mContext, List<ProfileFacilityModel> mProfileFacilityModelList) {
        this.mProfileFacilityModelList = mProfileFacilityModelList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ProfileFacilityCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.profile_facility_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ProfileFacilityModel mProfileFacilityModel = this.mProfileFacilityModelList.get(position);
        holder.bind(mProfileFacilityModel);

        holder.binding.tvFacility.setText(mProfileFacilityModel.getTitle());

        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return  mProfileFacilityModelList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<ProfileFacilityModel> getProfileFacilityModel() {
        return mProfileFacilityModelList;
    }

    public void addChatMassgeModel(ProfileFacilityModel mProfileFacilityModel) {
        try {
            this.mProfileFacilityModelList.add(mProfileFacilityModel);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProfileFacilityModelList(List<ProfileFacilityModel> mProfileFacilityModelList) {
        this.mProfileFacilityModelList = mProfileFacilityModelList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ProfileFacilityCellBinding binding;

        public ViewHolder(final View view, final ProfileFacilityCellBinding binding) {
            super(view);
            this.binding = binding;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mProfileFacilityModelList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final ProfileFacilityModel mProfileFacilityModel) {
            this.binding.setProfileFacilityModel(mProfileFacilityModel);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mProfileFacilityModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mProfileFacilityModelList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mProfileFacilityModelList.size();
        mProfileFacilityModelList.clear();
        notifyItemRangeRemoved(0, size);
    }

}