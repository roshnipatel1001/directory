package com.si.directory_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.UiThread;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.si.directory_app.R;
import com.si.directory_app.databinding.ProfileProductCellBinding;
import com.si.directory_app.models.profile.ProfileProductModel;

import java.util.List;

public class ProfileProductAdapter extends RecyclerView.Adapter<ProfileProductAdapter.ViewHolder> {


    private final Context mContext;
    private List<ProfileProductModel> mProfileProductModelList;
    private OnItemClickListener onItemClickListener;
    private int currentPos = -1;

    public ProfileProductAdapter(Context mContext, List<ProfileProductModel> mProfileProductModelList) {
        this.mProfileProductModelList = mProfileProductModelList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ProfileProductCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.profile_product_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      final ProfileProductModel mProfileProductModel = this.mProfileProductModelList.get(position);
       holder.bind(mProfileProductModel);

       holder.binding.tvName.setText(mProfileProductModel.getTitle());

        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return  mProfileProductModelList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<ProfileProductModel> getProfileProductModel() {
        return mProfileProductModelList;
    }

    public void addChatMassgeModel(ProfileProductModel mProfileProductModel) {
        try {
            this.mProfileProductModelList.add(mProfileProductModel);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProfileProductModelList(List<ProfileProductModel> mProfileProductModelList) {
        this.mProfileProductModelList = mProfileProductModelList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ProfileProductCellBinding binding;

        public ViewHolder(final View view, final ProfileProductCellBinding binding) {
            super(view);
            this.binding = binding;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mProfileProductModelList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final ProfileProductModel mProfileProductModel) {
            this.binding.setProfileProductModel(mProfileProductModel);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mProfileProductModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mProfileProductModelList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mProfileProductModelList.size();
        mProfileProductModelList.clear();
        notifyItemRangeRemoved(0, size);
    }

}