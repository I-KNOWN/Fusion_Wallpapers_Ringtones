package com.livewallpaper.ringtones.callertune.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageRetrofit {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit == null){
            String BASEURL = "https://www.api.idecloudstoragepanel.com/api/cloud/";
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
