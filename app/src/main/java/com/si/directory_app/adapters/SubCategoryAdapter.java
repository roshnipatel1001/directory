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
import com.si.directory_app.databinding.SubCategoryCellBinding;
import com.si.directory_app.models.sub_list.SubCategoryModel;
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

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {


    private final Context mContext;
    private List<SubCategoryModel> mSubCategoryModelList;
    private OnItemClickListener onItemClickListener;
    private int currentPos = -1;
    private static final String TAG = "SubCategoryAdapter";
    private boolean clicked;

    public SubCategoryAdapter(Context mContext, List<SubCategoryModel> mSubCategoryModelList) {
        this.mSubCategoryModelList = mSubCategoryModelList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final SubCategoryCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.sub_category_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SubCategoryModel mSubCategoryModel = this.mSubCategoryModelList.get(position);
        holder.bind(mSubCategoryModel);

        holder.binding.subName.setText(mSubCategoryModelList.get(position).getTitle());


        String url = Retro.IMAGE_BASEURL + "category" + "/" + mSubCategoryModel.getUserId() + "/" + mSubCategoryModel.getCatImage();

        Log.e(TAG, "onBindViewHolder: " + url);

        ImageAdapter.profile(holder.binding.subImage, url);


        if (mSubCategoryModel.getSubstatus()==1) {
            holder.binding.btLike.setImageResource(R.drawable.ic_heart);
            Log.e(TAG, "onClick: " + mSubCategoryModel.getSubstatus());
            Log.e(TAG, "onClick: " + mSubCategoryModel.getId());

        } else {
            holder.binding.btLike.setImageResource(R.drawable.ic_like);
            Log.e(TAG, "onClick: " + mSubCategoryModel.getSubstatus());
            Log.e(TAG, "onClick: " + mSubCategoryModel.getId());
        }


        holder.binding.btLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribe(mSubCategoryModel.getId(),holder);

//                if (clicked) {
//                    clicked = false;
//                    holder.binding.btLike.setImageResource(R.drawable.ic_like);
//                    subscribe(mSubCategoryModel.getId(),holder);
//                    Log.e(TAG, "onClick: " + mSubCategoryModel.getId());
//                    Log.e(TAG, "onClick: " + mSubCategoryModel.getSubstatus());
//
//                } else {
//                    clicked = true;
//                    holder.binding.btLike.setImageResource(R.drawable.ic_heart);
//                    success_popup();
//                    subscribe(mSubCategoryModel.getId());
//                    Log.e(TAG, "onClick: " + mSubCategoryModel.getSubstatus());
//                    Log.e(TAG, "onClick: " + mSubCategoryModel.getId());
//
//                }

            }
        });


        if (position == currentPos) {
            // selected true
        } else {
            // selected false
        }

    }

    @Override
    public int getItemCount() {
        return mSubCategoryModelList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<SubCategoryModel> getSubCategoryModel() {
        return mSubCategoryModelList;
    }

    public void addChatMassgeModel(SubCategoryModel mSubCategoryModel) {
        try {
            this.mSubCategoryModelList.add(mSubCategoryModel);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSubCategoryModelList(List<SubCategoryModel> mSubCategoryModelList) {
        this.mSubCategoryModelList = mSubCategoryModelList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final SubCategoryCellBinding binding;

        public ViewHolder(final View view, final SubCategoryCellBinding binding) {
            super(view);
            this.binding = binding;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mSubCategoryModelList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final SubCategoryModel mSubCategoryModel) {
            this.binding.setSubCategoryModel(mSubCategoryModel);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mSubCategoryModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mSubCategoryModelList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mSubCategoryModelList.size();
        mSubCategoryModelList.clear();
        notifyItemRangeRemoved(0, size);
    }

    private void success_popup() {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.category_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.findViewById(R.id.btOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    private void subscribe(String id,ViewHolder holder) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(mContext));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(mContext));
        hashMap.put(Appconst.UID, Session.getId(mContext));
        hashMap.put(Appconst.TYPE, "category");
        hashMap.put(Appconst.TYPE_ID, id);

        Log.e(TAG, "get_subscribe" + hashMap);

        Retro.service(mContext).subscribe(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String res = null;
                try {
                    res = response.body().string().trim();
                    JSONObject jsonObject = new JSONObject(res);
                    Integer stat = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    Log.e(TAG, "onResponse" + res);

                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    if (stat == 1) {
                        holder.binding.btLike.setImageResource(R.drawable.ic_heart);
                        success_popup();
                    } else if (stat == 0){
                        holder.binding.btLike.setImageResource(R.drawable.ic_like);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "onResponseChange: " + res);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

}