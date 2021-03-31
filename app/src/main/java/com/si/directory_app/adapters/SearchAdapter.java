package com.si.directory_app.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.UiThread;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.si.directory_app.R;
import com.si.directory_app.databinding.SearchModelCellBinding;
import com.si.directory_app.models.search.SearchModel;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {


    private final Context mContext;
    private List<SearchModel> mSearchModelList;
    private OnItemClickListener onItemClickListener;
    private int currentPos = -1;

    public SearchAdapter(Context mContext, List<SearchModel> mSearchModelList) {
        this.mSearchModelList = mSearchModelList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final SearchModelCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.search_model_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SearchModel mSearchModel = this.mSearchModelList.get(position);
        holder.bind(mSearchModel);

        if (mSearchModelList.get(position).getTitle()!=null){
            holder.binding.tvTitle.setText(mSearchModelList.get(position).getTitle());
        }else {
            holder.binding.tvTitle.setText(mSearchModelList.get(position).getFullName());
        }

        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return mSearchModelList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<SearchModel> getSearchModel() {
        return mSearchModelList;
    }

    public void addChatMassgeModel(SearchModel mSearchModel) {
        try {
            this.mSearchModelList.add(mSearchModel);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSearchModelList(List<SearchModel> mSearchModelList) {
        this.mSearchModelList = mSearchModelList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final SearchModelCellBinding binding;

        public ViewHolder(final View view, final SearchModelCellBinding binding) {
            super(view);
            this.binding = binding;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mSearchModelList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final SearchModel mSearchModel) {
            this.binding.setSearchModel(mSearchModel);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mSearchModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mSearchModelList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mSearchModelList.size();
        mSearchModelList.clear();
        notifyItemRangeRemoved(0, size);
    }

}