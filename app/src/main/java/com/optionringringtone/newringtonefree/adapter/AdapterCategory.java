package com.optionringringtone.newringtonefree.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.object.Category;
import com.optionringringtone.newringtonefree.object.detailcategory.Ringtone;
import java.util.Locale;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {
    private ICategory inter;
    private Activity activity;

    public AdapterCategory(Activity activity,  ICategory inter) {
        this.activity = activity;
        this.inter=inter;
    }



    @NonNull
    @Override
    public AdapterCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_categories, viewGroup, false);
        return new AdapterCategory.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category categoryOBJ=inter.getData(position);
        if (!CommonUntil.isNullorEmty(categoryOBJ.getCategoryName()))
            holder.txtKeySearch.setText(categoryOBJ.getNameByLanguage(Locale.getDefault().getLanguage()));
        else
            holder.txtKeySearch.setText(R.string.na);

        Glide.with(activity)
                .load(categoryOBJ.getCategoryIcon())
                .error(R.drawable.folder)
                .placeholder(R.drawable.folder)
                .into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.onClick(holder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemCount() {
        return inter.getItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtKeySearch;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtKeySearch = itemView.findViewById(R.id.tv_name_folder);
            imageView=itemView.findViewById(R.id.iv_folder);
        }
    }

    public interface ICategory{
        int getItemCount();
        void onClick (int position);
        Category getData(int position);
    }
}
