package com.si.directory_app.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.UiThread;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.si.directory_app.R;
import com.si.directory_app.databinding.TimeCellBinding;
import com.si.directory_app.models.profile.Time;

import java.util.ArrayList;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {


    private final Context mContext;
    private List<Time> mTimeList;
    private OnItemClickListener onItemClickListener;
    private int currentPos = -1;

    public TimeAdapter(Context mContext, List<Time> mTimeList) {
        this.mTimeList = mTimeList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final TimeCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.time_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Time mTime = this.mTimeList.get(position);
        holder.bind(mTime);

        holder.binding.tvDay.setText(mTimeList.get(position).getDay());
        holder.binding.tvTime.setText(mTimeList.get(position).getOpeningTime()+" to "+mTimeList.get(position).getClosingTime());

        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return mTimeList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<Time> getTime() {
        return mTimeList;
    }

    public void addChatMassgeModel(Time mTime) {
        try {
            this.mTimeList.add(mTime);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTimeList(List<Time> mTimeList) {
        this.mTimeList = mTimeList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TimeCellBinding binding;

        public ViewHolder(final View view, final TimeCellBinding binding) {
            super(view);
            this.binding = binding;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mTimeList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final Time mTime) {
            this.binding.setTime(mTime);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mTimeList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mTimeList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mTimeList.size();
        mTimeList.clear();
        notifyItemRangeRemoved(0, size);
    }

}