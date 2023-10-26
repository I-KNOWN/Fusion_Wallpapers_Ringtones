package com.livewallpaper.ringtones.callertune.Retrofit;


import com.livewallpaper.ringtones.callertune.Model.ImageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ImageCall {
    @POST("app-image-data/?access_token=Y29tLmxpdmV3YWxscGFwZXIucmluZ3RvbmVzLmNhbGxlcnR1bmU")
    Call<List<ImageModel>> getSpecificImageCategory(@Body BodyModel model);
}
