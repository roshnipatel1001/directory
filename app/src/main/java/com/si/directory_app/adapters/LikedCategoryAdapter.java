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
import com.si.directory_app.databinding.LikedCategoryCellBinding;
import com.si.directory_app.models.liked_cat.LikedCatModel;
import com.si.directory_app.models.liked_cat.LikedCategoryModel;
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

public class LikedCategoryAdapter extends RecyclerView.Adapter<LikedCategoryAdapter.ViewHolder> {


    private final Context mContext;
    private List<LikedCategoryModel> mLikedCategoryModelList;
    private OnItemClickListener onItemClickListener;
    private int currentPos = -1;
    private static final String TAG = "LikedCategoryAdapter";

    public LikedCategoryAdapter(Context mContext, List<LikedCategoryModel> mLikedCategoryModelList) {
        this.mLikedCategoryModelList = mLikedCategoryModelList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final LikedCategoryCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.liked_category_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       final LikedCategoryModel mLikedCategoryModel = this.mLikedCategoryModelList.get(position);
       holder.bind(mLikedCategoryModel);

       holder.binding.subName.setText(mLikedCategoryModelList.get(position).getTitle());

        String url=  Retro.IMAGE_BASEURL+"category"+"/"+ mLikedCategoryModel.getUserId()+"/"+mLikedCategoryModel.getCatImage();

        Log.e(TAG, "onBindViewHolder: "+url);


//        holder.binding.btLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//               // subscribe(mLikedCategoryModel.getId());
//
//               // deleteitem(position);
//            }
//        });

        ImageAdapter.profile(holder.binding.subImage,url);

        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return  mLikedCategoryModelList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<LikedCategoryModel> getLikedCategoryModel() {
        return mLikedCategoryModelList;
    }

    public void addChatMassgeModel(LikedCategoryModel mLikedCategoryModel) {
        try {
            this.mLikedCategoryModelList.add(mLikedCategoryModel);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLikedCategoryModelList(List<LikedCategoryModel> mLikedCategoryModelList) {
        this.mLikedCategoryModelList = mLikedCategoryModelList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final LikedCategoryCellBinding binding;

        public ViewHolder(final View view, final LikedCategoryCellBinding binding) {
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
                    notifyItemRangeChanged(0, mLikedCategoryModelList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final LikedCategoryModel mLikedCategoryModel) {
            this.binding.setLikedCategoryModel(mLikedCategoryModel);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mLikedCategoryModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mLikedCategoryModelList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mLikedCategoryModelList.size();
        mLikedCategoryModelList.clear();
        notifyItemRangeRemoved(0, size);
    }





}