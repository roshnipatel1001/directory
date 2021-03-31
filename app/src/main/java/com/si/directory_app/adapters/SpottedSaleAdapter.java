package com.si.directory_app.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.UiThread;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.si.directory_app.R;
import com.si.directory_app.databinding.SpottedSaleCellBinding;
import com.si.directory_app.models.profile.SpottedSale;
import com.si.directory_app.utills.Utility;

import java.util.ArrayList;
import java.util.List;

public class SpottedSaleAdapter extends RecyclerView.Adapter<SpottedSaleAdapter.ViewHolder> {


    private final Context mContext;
    private List<SpottedSale> mSpottedSaleList;
    private OnItemClickListener onItemClickListener;
    private int currentPos = -1;

    public SpottedSaleAdapter(Context mContext, List<SpottedSale> mSpottedSaleList) {
        this.mSpottedSaleList = mSpottedSaleList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final SpottedSaleCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.spotted_sale_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SpottedSale mSpottedSale = this.mSpottedSaleList.get(position);
        holder.bind(mSpottedSale);

        holder.binding.tvSaleName.setText(mSpottedSale.getSales()+" off "+mSpottedSale.getSaletype());
        String date = Utility.change_format(mSpottedSale.getEndDate(),"yy-MM-dd","dd-MM-yy");
        holder.binding.timeDate.setText(date+", "+mSpottedSale.getEndTime());




        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return mSpottedSaleList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<SpottedSale> getSpottedSale() {
        return mSpottedSaleList;
    }

    public void addChatMassgeModel(SpottedSale mSpottedSale) {
        try {
            this.mSpottedSaleList.add(mSpottedSale);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSpottedSaleList(List<SpottedSale> mSpottedSaleList) {
        this.mSpottedSaleList = mSpottedSaleList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final SpottedSaleCellBinding binding;

        public ViewHolder(final View view, final SpottedSaleCellBinding binding) {
            super(view);
            this.binding = binding;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mSpottedSaleList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final SpottedSale mSpottedSale) {
            this.binding.setSpottedSale(mSpottedSale);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mSpottedSaleList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mSpottedSaleList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mSpottedSaleList.size();
        mSpottedSaleList.clear();
        notifyItemRangeRemoved(0, size);
    }

}