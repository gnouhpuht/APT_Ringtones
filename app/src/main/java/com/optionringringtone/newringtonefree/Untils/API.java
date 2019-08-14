package com.optionringringtone.newringtonefree.Untils;


import com.optionringringtone.newringtonefree.object.Category;
import com.optionringringtone.newringtonefree.object.CategoryOBJ;
import com.optionringringtone.newringtonefree.object.Example;
import com.optionringringtone.newringtonefree.object.KeyWord;
import com.optionringringtone.newringtonefree.object.Location;
import com.optionringringtone.newringtonefree.object.RequestRingtoneResponse;
import com.optionringringtone.newringtonefree.object.ResponseDTO;
import com.optionringringtone.newringtonefree.object.RingTone;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @GET("popular")
    Call<ResponseDTO<ArrayList<RingTone>>> getPopularRingtones(@Query("lang") String lang);

    @GET("categories")
    Call<ResponseDTO<ArrayList<Category>>> getCategoriesRingtonesZip(@Query("lang") String lang);


    @GET("categories")
    Call<ResponseDTO<ArrayList<CategoryOBJ>>> getCategoriesRingtones(@Query("lang") String lang);


    // TODO: 20/06/2019 Search ringtone
    @GET("search")
    Call<ResponseDTO<ArrayList<RingTone>>> getRingtoneByKey(@Query("keyword") String keyword);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("request-ring-tone")
    Call<RequestRingtoneResponse> requestRingtone(@Field("title") String title,
                                                  @Field("singer") String singer,
                                                  @Field("email") String email,
                                                  @Field("fcm_token") String fcm_token);

    @GET("suggest")
    Call<ResponseDTO<ArrayList<KeyWord>>> getSuggestKeyWord(@Query("lang") String lang);

    @GET("ring-tone/category/{id}")
    Call<ArrayList<RingTone>> getDetailsCategories(@Path("id") String id);

    @GET("location")
    Call<Location> getLocation();

}
