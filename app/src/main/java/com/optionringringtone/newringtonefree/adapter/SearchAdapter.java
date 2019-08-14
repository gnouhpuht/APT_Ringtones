package com.optionringringtone.newringtonefree.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import com.optionringringtone.newringtonefree.object.KeyWord;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.RecyclerViewHolder> {

    private List<KeyWord> keySearchObjects = new ArrayList<>();
    private Context context;
    public itemClickListSearch onItemClickListSearch;

    public SearchAdapter(List<KeyWord> keySearchObjects, Context context, itemClickListSearch onItemClickListSearch ) {
        this.keySearchObjects = keySearchObjects;
        this.context = context;
        this.onItemClickListSearch = onItemClickListSearch;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_adapter_search, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtKeySearch.setText(keySearchObjects.get(position).getName());

        holder.txtKeySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListSearch.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return keySearchObjects.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtKeySearch;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtKeySearch = (TextView) itemView.findViewById(R.id.txtKeySearch);
        }
    }

    public interface itemClickListSearch{
        void onClick(int position);
    }
}
