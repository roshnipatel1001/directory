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
import com.si.directory_app.databinding.NotificationCellBinding;
import com.si.directory_app.models.notification.NotificationModel;
import com.si.directory_app.retro.Retro;
import com.si.directory_app.utills.Session;
import com.si.directory_app.utills.Utility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    private final Context mContext;
    private List<NotificationModel> mNotificationModelList;
    private OnItemClickListener onItemClickListener;
    private int currentPos = -1;
    private static final String TAG = "NotificationAdapter";

    public NotificationAdapter(Context mContext, List<NotificationModel> mNotificationModelList) {
        this.mNotificationModelList = mNotificationModelList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final NotificationCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.notification_cell, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NotificationModel mNotificationModel = this.mNotificationModelList.get(position);
        holder.bind(mNotificationModel);

        holder.binding.tvTitle.setText(mNotificationModelList.get(position).getTitle());
        holder.binding.tvDescription.setText(mNotificationModelList.get(position).getMessage());

        String read = mNotificationModelList.get(position).getRead();

        Log.e(TAG, "onBindViewHolder: "+read);


        if (mNotificationModelList.get(position).getRead().equals("1")){

            holder.binding.ivRead.setVisibility(View.GONE);
        }else {
            holder.binding.ivRead.setVisibility(View.VISIBLE);

        }

        holder.binding.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.ivRead.setVisibility(View.GONE);
                notification_update();
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
        return mNotificationModelList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public List<NotificationModel> getNotificationModel() {
        return mNotificationModelList;
    }

    public void addChatMassgeModel(NotificationModel mNotificationModel) {
        try {
            this.mNotificationModelList.add(mNotificationModel);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNotificationModelList(List<NotificationModel> mNotificationModelList) {
        this.mNotificationModelList = mNotificationModelList;
        notifyDataSetChanged();
    }

    private void dataSetChanged() {
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final NotificationCellBinding binding;

        public ViewHolder(final View view, final NotificationCellBinding binding) {
            super(view);
            this.binding = binding;
            binding.ivRead.setOnClickListener(this);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            try {
                if (onItemClickListener != null) {
                    setCurrentPos(getAdapterPosition());
                    notifyItemRangeChanged(0, mNotificationModelList.size());
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @UiThread
        public void bind(final NotificationModel mNotificationModel) {
            this.binding.setNotificationModel(mNotificationModel);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void deleteitem(int position) {
        mNotificationModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mNotificationModelList.size());
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void clearAdapter() {
        int size = mNotificationModelList.size();
        mNotificationModelList.clear();
        notifyItemRangeRemoved(0, size);
    }

    private void notification_update(){

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Appconst.DEVICE_ID, Utility.getDeviceId(mContext));
        hashMap.put(Appconst.DEVICE_TYPE, Appconst.DEVICE_TYPE_VALUE);
        hashMap.put(Appconst.API_KEY, Appconst.API_KEY_VALUE);
        hashMap.put(Appconst.ACCESS_TOKEN, Session.getAccessToken(mContext));
        hashMap.put(Appconst.UID, Session.getId(mContext));
        hashMap.put("read","1");

        Log.e(TAG, "notification_update: "+hashMap);

        Retro.service(mContext).notification_update(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                Log.e(TAG, "onResponse: " + response);
                String res = null;

                try {
                    res = response.body().string().trim();
                    JSONObject json = new JSONObject(res);

                    Integer stat = json.getInt("status");
                    String message = json.getString("message");
                    Log.e(TAG, "onResponse" + res);

                    if (stat == 1) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponseNotify: "+res);



                    }else {

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse" + res);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });






    }


}