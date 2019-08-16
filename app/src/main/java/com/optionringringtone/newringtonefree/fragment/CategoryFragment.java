package com.optionringringtone.newringtonefree.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.gyf.loadview.LoadView;
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
import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;

public class CategoryFragment extends FragmentCommon  implements AdapterCategory.ICategory {
    private static final String TAG = "CategoriesFragment";
    private ProgressBar prLoading;
    private RecyclerView rcFolderCategory;
    private AdapterCategory adapterLstRingtone;
    private ArrayList<Category> lstCategories;
    private ArrayList<CategoryName> categoryNames;
    private Category category;
    private CategoryName categoryName;
    private static String baseUrl="https://ring.sgp1.digitaloceanspaces.com/newCategoryTest/";
    private LoadView loadView;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init(View view) {
        rcFolderCategory = view.findViewById(R.id.rc_folder_category);
        prLoading = view.findViewById(R.id.prLoading);
        lstCategories = new ArrayList<>();
        categoryNames=new ArrayList<>();
        CommonUntil.setLayoutManager(activity, rcFolderCategory, LinearLayout.VERTICAL);
        adapterLstRingtone = new AdapterCategory(activity, this);
        rcFolderCategory.setAdapter(adapterLstRingtone);

        loadView=view.findViewById(R.id.load_view);
        getDataCategory();
    }

    private List<Category> getDataCategory(){
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("categories");
            getCategoryName();
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                CategoryName category_name = categoryNames.get(i);
//                Log.d("aaaaaaaaa", "getDataCategory: "+getCategoryName().get(i).getVi());
                String url_value = jo_inside.getString("package_link");
                String url_icon=jo_inside.getString("category_icon");

                category=new Category();
                category.setCategoryName(category_name);
                category.setPackageLink(url_value);
                category.setCategoryIcon(url_icon);

                lstCategories.add(category);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Log.d("fffffffff", "getDataCategory: "+lstCategories.size());
        return lstCategories;
    }
    private List<CategoryName> getCategoryName(){
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("categories");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject ob_category = m_jArry.getJSONObject(i);
                JSONObject ob_category_name=ob_category.getJSONObject("category_name");
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


                    categoryName=new CategoryName();
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
        if (lstCategories==null){
            return 0;
        }
        return lstCategories.size();
    }

    @Override
    public void onClick(int position) {
        String url=getCategoryName().get(position).getEn()+".zip";
        if (CommonUntil.isExistFileZip(url)==true){
            return;
        }
        downloadAndExtrack(url,position);

        CommonUntil.replaceFragment(activity, new ListRingtoneCategoryFragment().setId(lstCategories.get(position) + "")
                .setTitle(getCategoryName().get(position).getVi())
        );
    }

    @Override
    public Category getData(int position) {
        return lstCategories.get(position);
    }

    private void downloadAndExtrack(String name,int position){


        File file = new File(name);
        if (!file.exists()) {
            DownloadFileAsync2 mDownloadFileAsync = new DownloadFileAsync2(name, lstCategories.get(position).getCategoryName().getEn(),
                    new DownloadFileAsync2.downloadInterface() {
                        @Override
                        public void onProgressUpdate(int progress) {
                        }

                        @Override
                        public void onFaildDownload() {
                            Toast.makeText(activity, activity.getResources().getString(R.string.download_error), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onComplete(String path) {
                            Toast.makeText(activity,activity.getResources().getString(R.string.download_complete), Toast.LENGTH_SHORT).show();
                    if (adapterLstRingtone != null)
                        adapterLstRingtone.notifyDataSetChanged();
                        }

                        @Override
                        public void onStart() {
                            if (activity != null)
                                Toast.makeText(activity, activity.getString(R.string.download_start), Toast.LENGTH_SHORT).show();
                        }
                    });
            mDownloadFileAsync.execute(lstCategories.get(position).getPackageLink());
        } else {
            if (activity!= null)
                Toast.makeText(activity, activity.getResources().getString(R.string.download_complete), Toast.LENGTH_SHORT).show();
        }
    }
}
