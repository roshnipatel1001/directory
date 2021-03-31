package com.si.directory_app.adapters;

import android.content.Context;
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
import com.si.directory_app.databinding.LikedRetailerCellBinding;
import com.si.directory_app.models.liked_retailer.LikedRetailerModel;
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

public class LikedRetailerAdapter extends RecyclerView.Adapter<LikedRetailerAdapter.ViewHolder> {


    private final Context mContext;
    private List<LikedRetailerModel> mLikedRetailerModelList;
    private OnItemClickListener onItemClickListener;
    private static final String TAG = "LikedRetailerAdapter";
    private int currentPos = -1;

    public LikedRetailerAdapter(Context mContext, List<LikedRetailerModel> mLikedRetailerModelList) {
        this.mLikedRetailerModelList = mLikedRetailerModelList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final LikedRetailerCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.liked_retailer_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      final LikedRetailerModel mLikedRetailerModel = this.mLikedRetailerModelList.get(position);
     holder.bind(mLikedRetailerModel);


        holder.binding.tvName.setText(mLikedRetailerModelList.get(position).getFullName());

        String url=  Retro.IMAGE_BASEURL+"users"+"/"+ mLikedRetailerModel.getId()+"/"+mLikedRetailerModel.getProfileImg();

        Log.e(TAG, "onBindViewHolder: "+url);


        ImageAdapter.profile(holder.binding.ivRetailer,url);

        holder.binding.address.setText(mLikedRetailerModelList.get(position).getAddress());
        holder.binding.tvRating.setText(mLikedRetailerModelList.get(position).getRating());



        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return mLikedRetailerModelList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<LikedRetailerModel> getLikedRetailerModel() {
        return mLikedRetailerModelList;
    }

    public void addChatMassgeModel(LikedRetailerModel mLikedRetailerModel) {
        try {
            this.mLikedRetailerModelList.add(mLikedRetailerModel);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLikedRetailerModelList(List<LikedRetailerModel> mLikedRetailerModelList) {
        this.mLikedRetailerModelList = mLikedRetailerModelList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final LikedRetailerCellBinding binding;

        public ViewHolder(final View view, final LikedRetailerCellBinding binding) {
            super(view);
            this.binding = binding;
            binding.btLike.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mLikedRetailerModelList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final LikedRetailerModel mLikedRetailerModel) {
            this.binding.setLikedRetailerModel(mLikedRetailerModel);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mLikedRetailerModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mLikedRetailerModelList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mLikedRetailerModelList.size();
        mLikedRetailerModelList.clear();
        notifyItemRangeRemoved(0, size);
    }



}