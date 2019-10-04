package com.optionringringtone.newringtonefree.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.facebook.ads.NativeBannerAdView;
import com.gyf.loadview.LoadView;
import com.optionringringtone.newringtonefree.FacebookAds;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.DownloadFileAsync2;
import com.optionringringtone.newringtonefree.Untils.FragmentCommon;
import com.optionringringtone.newringtonefree.adapter.AdapterCategory;
import com.optionringringtone.newringtonefree.object.Category;
import com.optionringringtone.newringtonefree.object.CategoryName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class CategoryFragment extends FragmentCommon implements AdapterCategory.ICategory {
    private static final String TAG = "CategoryFragment";
    private ProgressBar prLoading;
    private RecyclerView rcFolderCategory;
    private AdapterCategory adapterLstRingtone;
    private ArrayList<Category> lstCategories;
    private ArrayList<CategoryName> categoryNames;
    private Category category;
    private CategoryName categoryName;
    private static String baseUrl = "https://ring.sgp1.digitaloceanspaces.com/newCategoryTest/";
    private LoadView loadView;
    private com.facebook.ads.InterstitialAd interstitialAd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init(View view) {
        rcFolderCategory = view.findViewById(R.id.rc_folder_category);
        prLoading = view.findViewById(R.id.prLoading);
        lstCategories = new ArrayList<>();
        categoryNames = new ArrayList<>();
        CommonUntil.setLayoutManager(activity, rcFolderCategory, LinearLayout.VERTICAL);
        adapterLstRingtone = new AdapterCategory(activity, this);
        rcFolderCategory.setAdapter(adapterLstRingtone);

        loadView = view.findViewById(R.id.load_view);
        getDataCategory();
        AdSettings.setIntegrationErrorMode(AdSettings.IntegrationErrorMode.INTEGRATION_ERROR_CALLBACK_MODE);
        AdSettings.addTestDevice("HASHED ID");
        adapterLstRingtone.notifyDataSetChanged();
    }

    private List<Category> getDataCategory() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("categories");
            getCategoryName();
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                CategoryName category_name = categoryNames.get(i);
//                Log.d("aaaaaaaaa", "getDataCategory: "+getCategoryName().get(i).getVi());
                String url_value = jo_inside.getString("package_link");
                String url_icon = jo_inside.getString("category_icon");

                category = new Category();
                category.setCategoryName(category_name);
                category.setPackageLink(url_value);
                category.setCategoryIcon(url_icon);

                lstCategories.add(category);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lstCategories;
    }

    private List<CategoryName> getCategoryName() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("categories");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject ob_category = m_jArry.getJSONObject(i);
                JSONObject ob_category_name = ob_category.getJSONObject("category_name");
                String en = ob_category_name.getString("en");
                String ar = ob_category_name.getString("ar");
                String cs = ob_category_name.getString("cs");
                String da = ob_category_name.getString("da");
                String de = ob_category_name.getString("de");
                String es = ob_category_name.getString("es");
                String fr = ob_category_name.getString("fr");
                String hr = ob_category_name.getString("hr");
                String hu = ob_category_name.getString("hu");
                String id = ob_category_name.getString("id");
                String it = ob_category_name.getString("it");
                String ja = ob_category_name.getString("ja");
                String ko = ob_category_name.getString("ko");
                String ms = ob_category_name.getString("ms");
                String nb = ob_category_name.getString("nb");
                String nl = ob_category_name.getString("nl");
                String pl = ob_category_name.getString("pl");
                String pt = ob_category_name.getString("pt");
                String ro = ob_category_name.getString("ro");
                String ru = ob_category_name.getString("ru");
                String sr = ob_category_name.getString("sr");
                String sv = ob_category_name.getString("sv");
                String th = ob_category_name.getString("th");
                String tr = ob_category_name.getString("tr");
                String vi = ob_category_name.getString("vi");
                String zh = ob_category_name.getString("zh");


                categoryName = new CategoryName();
                categoryName.setEn(en);
                categoryName.setAr(ar);
                categoryName.setCs(cs);
                categoryName.setDa(da);
                categoryName.setDe(de);
                categoryName.setEs(es);
                categoryName.setFr(fr);
                categoryName.setHr(hr);
                categoryName.setHu(hu);
                categoryName.setId(id);
                categoryName.setIt(it);
                categoryName.setJa(ja);
                categoryName.setKo(ko);
                categoryName.setMs(ms);
                categoryName.setNl(nl);
                categoryName.setNb(nb);
                categoryName.setRo(ro);
                categoryName.setPl(pl);
                categoryName.setPt(pt);
                categoryName.setRu(ru);
                categoryName.setSr(sr);
                categoryName.setSv(sv);
                categoryName.setTh(th);
                categoryName.setTr(tr);
                categoryName.setVi(vi);
                categoryName.setZh(zh);

                categoryNames.add(categoryName);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoryNames;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("categories.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_list_folder_category;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public int getItemCount() {
        if (lstCategories == null) {
            return 0;
        }
        return lstCategories.size();
    }

    @Override
    public void onClick(int position) {
        Random random=new Random();
        int n=random.nextInt(lstCategories.size());
        String url = getCategoryName().get(position).getEn() + ".zip";
        String name = getCategoryName().get(position).getEn();
        if (CommonUntil.isExistFileZip(name) == true) {
            CommonUntil.replaceFragment(activity, new ListRingtoneCategoryFragment().setId(lstCategories.get(position) + "")
                    .setTitle(categoryNames.get(position).getEn())
            );
            if (n==position){
                loadVideo();
            }

        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("");
            builder.setMessage("Are you sure you want to download? ");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loadVideo();
                    downloadAndExtrack(url, position);
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(getActivity(), FacebookAds.class);
                    startActivity(intent);
                    dialog.dismiss();

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    @Override
    public Category getData(int position) {
        return lstCategories.get(position);
    }

    private ProgressDialog creProgress(@NonNull Activity activity, @NonNull String mess) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(mess);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    private void downloadAndExtrack(String name, int position) {
        ProgressDialog progressDialog = creProgress(activity, activity.getString(R.string.downloading_pls_wait));
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        File file = new File(name);
        if (!file.exists()) {
            DownloadFileAsync2 mDownloadFileAsync = new DownloadFileAsync2(name, lstCategories.get(position).getCategoryName().getEn(),
                    new DownloadFileAsync2.downloadInterface() {
                        @Override
                        public void onProgressUpdate(int progress) {
                            progressDialog.setProgress(progress);
                        }

                        @Override
                        public void onFaildDownload() {
                            Toast.makeText(activity, activity.getResources().getString(R.string.download_error), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onComplete(String path) {
                            progressDialog.dismiss();
                            Toast.makeText(activity, activity.getResources().getString(R.string.download_complete), Toast.LENGTH_SHORT).show();
                            if (adapterLstRingtone != null)
                                adapterLstRingtone.notifyDataSetChanged();
//                            CommonUntil.replaceFragment(activity, new ListRingtoneCategoryFragment().setId(lstCategories.get(position) + "")
//                                    .setTitle(categoryNames.get(position).getEn())
//                            );
                        }

                        @Override
                        public void onStart() {
                            if (activity != null)
                                progressDialog.show();
                            Toast.makeText(activity, activity.getString(R.string.download_start), Toast.LENGTH_SHORT).show();
                        }
                    });
            mDownloadFileAsync.execute(lstCategories.get(position).getPackageLink());
        } else {
            if (activity != null)
                Toast.makeText(activity, activity.getResources().getString(R.string.download_complete), Toast.LENGTH_SHORT).show();
        }
    }
    private void loadVideo() {
        interstitialAd = new com.facebook.ads.InterstitialAd(getActivity(), "YOUR_PLACEMENT_ID");
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
