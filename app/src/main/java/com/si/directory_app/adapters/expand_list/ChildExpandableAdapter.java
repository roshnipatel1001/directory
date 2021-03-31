package com.si.directory_app.adapters.expand_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.si.directory_app.R;
import com.si.directory_app.databinding.ExpandSubListBinding;
import com.si.directory_app.listener.FilterCheckListener;
import com.si.directory_app.models.product_list.Subcategory;

import java.util.List;

public class ChildExpandableAdapter extends RecyclerView.Adapter<ChildExpandableAdapter.ViewHolder> {

    private final Context mContext;
    private List<Subcategory> subcategoryList;
    private static final String TAG = "ChildExpandableAdapter";
    private long val = 1;
    private FilterCheckListener filterCheckListener;

    public ChildExpandableAdapter(Context mContext, List<Subcategory> subcategoryList, FilterCheckListener filterCheckListener) {
        this.subcategoryList = subcategoryList;
        this.mContext = mContext;
        this.filterCheckListener = filterCheckListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ExpandSubListBinding binding = DataBindingUtil.inflate(inflater, R.layout.expand_sub_list, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.binding.tvSub.setText(subcategoryList.get(position).getTitle());
        holder.bind(subcategoryList.get(position));

        holder.binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subcategoryList.get(position).setChecked(isChecked);
                filterCheckListener.getChecked(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subcategoryList.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ExpandSubListBinding binding;

        public ViewHolder(final View view, final ExpandSubListBinding binding) {
            super(view);
            this.binding = binding;
        }

        public void bind(Subcategory featuresModel) {
            if (featuresModel.isChecked()) {
                binding.checkbox.setChecked(true);
            } else {
                binding.checkbox.setChecked(false);
            }

           /* binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    featuresModel.setChecked(isChecked);
                }
            });*/

           /* itemView.setOnClickListener(view -> {

            });*/
        }
    }


}