package com.si.directory_app.adapters;

import android.app.Dialog;
import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.UiThread;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.si.directory_app.R;
import com.si.directory_app.constants.Appconst;
import com.si.directory_app.databinding.RetailerListCellBinding;
import com.si.directory_app.models.retailer_list.RetailerListModel;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.ImageAdapter;
import com.si.directory_app.utills.Session;
import com.si.directory_app.utills.Utility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailerListAdapter extends RecyclerView.Adapter<RetailerListAdapter.ViewHolder> {


    private final Context mContext;
    private List<RetailerListModel> mRetailerListModelList;
    private OnItemClickListener onItemClickListener;
    private boolean clicked;
    private int currentPos = -1;
    private static final String TAG = "RetailerListAdapter";

    public RetailerListAdapter(Context mContext, List<RetailerListModel> mRetailerListModelList) {
        this.mRetailerListModelList = mRetailerListModelList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final RetailerListCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.retailer_list_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       final RetailerListModel mRetailerListModel = this.mRetailerListModelList.get(position);
       holder.bind(mRetailerListModel);


        String url = Retro.IMAGE_BASEURL + "users" + "/" + mRetailerListModel.getId() + "/" +mRetailerListModel.getProfileImg();

        Log.e(TAG, "onBindViewHolder: " + url);

        ImageAdapter.profile(holder.binding.ivRetailer, url);




        holder.binding.tvName.setText(mRetailerListModelList.get(position).getFullName());
        holder.binding.address.setText(mRetailerListModelList.get(position).getAddress());
        holder.binding.tvRating.setText(mRetailerListModelList.get(position).getRating());






        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return  mRetailerListModelList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<RetailerListModel> getRetailerListModel() {
        return mRetailerListModelList;
    }

    public void addChatMassgeModel(RetailerListModel mRetailerListModel) {
        try {
            this.mRetailerListModelList.add(mRetailerListModel);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRetailerListModelList(List<RetailerListModel> mRetailerListModelList) {
        this.mRetailerListModelList = mRetailerListModelList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final RetailerListCellBinding binding;

        public ViewHolder(final View view, final RetailerListCellBinding binding) {
            super(view);
            this.binding = binding;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mRetailerListModelList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final RetailerListModel mRetailerListModel) {
            this.binding.setRetailerListModel(mRetailerListModel);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mRetailerListModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mRetailerListModelList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mRetailerListModelList.size();
        mRetailerListModelList.clear();
        notifyItemRangeRemoved(0, size);
    }

    private void success_popup() {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.subscribe_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.findViewById(R.id.btOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }


}