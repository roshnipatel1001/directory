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
import com.si.directory_app.databinding.CategoryListCellBinding;
import com.si.directory_app.models.cat_list.CategoryDatum;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.ImageAdapter;
import com.si.directory_app.utills.Session;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {


    private final Context mContext;
    private List<CategoryDatum> mCategoryListModelList;
    private OnItemClickListener onItemClickListener;
    private int currentPos = -1;
    private static final String TAG = "CategoryListAdapter";

    public CategoryListAdapter(Context mContext, List<CategoryDatum> mCategoryListModelList) {
        this.mCategoryListModelList = mCategoryListModelList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final CategoryListCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.category_list_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
     final CategoryDatum mCategoryListModel = this.mCategoryListModelList.get(position);
      holder.bind(mCategoryListModel);

       holder.binding.tv1.setText(mCategoryListModel.getTitle());
          mCategoryListModel.getId();

        String url=  Retro.IMAGE_BASEURL+"category"+"/"+ mCategoryListModel.getmUserId()+"/"+mCategoryListModel.getCatImage();

        Log.e(TAG, "onBindViewHolder: "+url);

        ImageAdapter.profile(holder.binding.image1,url);


        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return  mCategoryListModelList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<CategoryDatum> getCategoryListModel() {
        return mCategoryListModelList;
    }

    public void addChatMassgeModel(CategoryDatum mCategoryListModel) {
        try {
            this.mCategoryListModelList.add(mCategoryListModel);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCategoryListModelList(List<CategoryDatum> mCategoryListModelList) {
        this.mCategoryListModelList = mCategoryListModelList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CategoryListCellBinding binding;

        public ViewHolder(final View view, final CategoryListCellBinding binding) {
            super(view);
            this.binding = binding;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mCategoryListModelList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final CategoryDatum mCategoryListModel) {
            this.binding.setCategoryListModel(mCategoryListModel);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mCategoryListModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mCategoryListModelList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mCategoryListModelList.size();
        mCategoryListModelList.clear();
        notifyItemRangeRemoved(0, size);
    }

}