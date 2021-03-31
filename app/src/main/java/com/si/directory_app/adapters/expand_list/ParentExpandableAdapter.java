package com.si.directory_app.adapters.expand_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.si.directory_app.R;
import com.si.directory_app.databinding.ExpandCategoryListBinding;
import com.si.directory_app.listener.FilterCheckListener;
import com.si.directory_app.models.product_list.ProductsModel;

import java.util.List;

public class ParentExpandableAdapter extends RecyclerView.Adapter<ParentExpandableAdapter.ViewHolder> {

    private final Context mContext;
    private int childViewPos = -1, clickedPos = -1;
    private List<ProductsModel> productsList;
    private static final String TAG = "ParentExpandableAdapter";

    public ParentExpandableAdapter(Context mContext, List<ProductsModel> productsList) {
        this.productsList = productsList;
        this.mContext = mContext;
    }

    public List<ProductsModel> getProductsList() {
        return productsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ExpandCategoryListBinding binding = DataBindingUtil.inflate(inflater, R.layout.expand_category_list, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.binding.tvListHeader.setText(productsList.get(position).getTitle());
        if (childViewPos == position) {
            holder.binding.llChildItem.setVisibility(View.VISIBLE);
            holder.binding.image.setRotation(-180);
        } else {
            holder.binding.llChildItem.setVisibility(View.GONE);
            if (clickedPos == position) {
                holder.binding.image.setRotation(0);
            }
        }
        holder.binding.header.setOnClickListener(v -> {
            clickedPos = position;
            if (holder.binding.llChildItem.getVisibility() == View.GONE) {
                childViewPos = position;
                holder.binding.llChildItem.setVisibility(View.VISIBLE);
            } else {
                childViewPos = -1;
                holder.binding.llChildItem.setVisibility(View.GONE);
            }
            notifyDataSetChanged();
        });

        ChildExpandableAdapter childAdapter = new ChildExpandableAdapter(mContext, productsList.get(position).getSubcategory(), new FilterCheckListener() {
            @Override
            public void getChecked(int childPosition, boolean isCheck) {
                productsList.get(position).getSubcategory().get(childPosition).setChecked(isCheck);
            }
        });
        holder.binding.rvChild.setLayoutManager(new LinearLayoutManager(mContext));
        holder.binding.rvChild.setHasFixedSize(false);
        holder.binding.rvChild.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ExpandCategoryListBinding binding;

        public ViewHolder(final View view, final ExpandCategoryListBinding binding) {
            super(view);
            this.binding = binding;

        }
    }
}