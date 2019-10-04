package com.optionringringtone.newringtonefree.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.facebook.ads.NativeBannerAdView;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.mysetting.Setting2;
import com.optionringringtone.newringtonefree.object.Category;
import java.util.Locale;
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class AdapterCategory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ICategory inter;
    private Activity activity;
    private NativeBannerAd mNativeBannerAd;

    public AdapterCategory(Activity activity, ICategory inter) {
        this.activity = activity;
        this.inter = inter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position%7==0){
            return 2;
        }
        return 0;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        switch (itemViewType) {
            case 0:
                return new ViewHolderCategory(
                        LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_categories, viewGroup,
                                false));
            case 2:
                return new ViewHolderNativeBanner(
                        LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_navite_banner,viewGroup,
                                false));
            default:
                return new ViewHolderCategory(
                        LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_categories, viewGroup,
                                false));
        }
//        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//        View view = inflater.inflate(R.layout.item_categories, viewGroup, false);
//        return new AdapterCategory.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Category categoryOBJ = inter.getData(position);
        switch (getItemViewType(position)) {
            case 0:
                ViewHolderCategory holder=(ViewHolderCategory)viewHolder;
                if (!CommonUntil.isNullorEmty(categoryOBJ.getCategoryName()))
                    holder.txtKeySearch.setText(categoryOBJ.getNameByLanguage(Locale.getDefault().getLanguage()));
                else
                    holder.txtKeySearch.setText(R.string.na);

                Glide.with(activity)
                        .load(categoryOBJ.getCategoryIcon())
                        .error(R.drawable.folder)
                        .placeholder(R.drawable.folder)
                        .into(holder.imageView);
                String name = categoryOBJ.getCategoryName().getEn();
                if (CommonUntil.isExistFileZip(name) == true) {
                    holder.tvDownload.setVisibility(View.GONE);
                } else {
                    holder.tvDownload.setVisibility(View.VISIBLE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        inter.onClick(holder.getAdapterPosition());

                    }
                });
                break;
            case 2:

                ViewHolderNativeBanner holderNative=(ViewHolderNativeBanner) viewHolder;
                mNativeBannerAd = new NativeBannerAd(activity, "YOUR_PLACEMENT_ID");
                AdSettings.setIntegrationErrorMode(AdSettings.IntegrationErrorMode.INTEGRATION_ERROR_CALLBACK_MODE);
                AdSettings.addTestDevice("HASHED ID");
                mNativeBannerAd.setAdListener(new NativeAdListener() {
                    @Override
                    public void onMediaDownloaded(Ad ad) {

                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {

                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        View adView = NativeBannerAdView.render(activity, mNativeBannerAd, NativeBannerAdView.Type.HEIGHT_100);
                        // Add the Native Banner Ad View to your ad container

                        holderNative.nativeBannerAdContainer.addView(adView);
                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                });
                mNativeBannerAd.loadAd();
                break;
            default:
                break;
        }


    }

    @Override
    public int getItemCount() {
        return inter.getItemCount();
    }

    static public class ViewHolderCategory extends RecyclerView.ViewHolder {
        private TextView txtKeySearch;
        private ImageView imageView;
        // private TextView tvDownload;
        private ImageView tvDownload;


        public ViewHolderCategory(@NonNull View itemView) {
            super(itemView);
            txtKeySearch = itemView.findViewById(R.id.tv_name_folder);
            imageView = itemView.findViewById(R.id.iv_folder);
            tvDownload = itemView.findViewById(R.id.download);
        }
    }

    static public class ViewHolderNativeBanner extends RecyclerView.ViewHolder{
        LinearLayout nativeBannerAdContainer;
        public ViewHolderNativeBanner(@NonNull View itemView) {
            super(itemView);
            nativeBannerAdContainer=itemView.findViewById(R.id.banner_container);
        }
    }
    public interface ICategory {
        int getItemCount();

        void onClick(int position);

        Category getData(int position);
    }
}
