package com.optionringringtone.newringtonefree.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.fragment.LstRingtonesFragment;
import com.optionringringtone.newringtonefree.object.CategoryOBJ;
import java.util.ArrayList;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder> {
    ArrayList<CategoryOBJ> lstCategroies;
    Activity activity;

    public AdapterCategories(Activity activity, ArrayList<CategoryOBJ> lstCategroies) {
        this.lstCategroies = lstCategroies;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_adapter_search, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CategoryOBJ categoryOBJ = lstCategroies.get(i);

        if (!CommonUntil.isNullorEmty(categoryOBJ.getName()))
            viewHolder.txtKeySearch.setText(categoryOBJ.getName());
        else
            viewHolder.txtKeySearch.setText(R.string.na);

        viewHolder.txtKeySearch.setOnClickListener(v ->
                CommonUntil.replaceFragment(activity, new LstRingtonesFragment().setId(categoryOBJ.getId() + "")
                        .setTitle(categoryOBJ.getName())
                )
        );
    }

    @Override
    public int getItemCount() {
        return lstCategroies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtKeySearch;

        public ViewHolder(View itemView) {
            super(itemView);
            txtKeySearch = itemView.findViewById(R.id.txtKeySearch);
        }
    }
}
