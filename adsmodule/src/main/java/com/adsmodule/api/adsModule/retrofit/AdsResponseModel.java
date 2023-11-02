package com.adsmodule.api.adsModule.retrofit;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdsResponseModel {

    private String url;
    private AppOpenAdsDTO app_open_ads = new AppOpenAdsDTO();
    private BannerAdsDTO banner_ads = new BannerAdsDTO();
    private NativeAdsDTO native_ads = new NativeAdsDTO();
    private InterstitialAdsDTO interstitial_ads = new InterstitialAdsDTO();
    private RewardedAdsDTO rewarded_ads = new RewardedAdsDTO();
    private MobileStickyAdsDTO mobile_sticky_ads = new MobileStickyAdsDTO();
    private CollapsibleAdsDTO collapsible_ads = new CollapsibleAdsDTO();
    private CustomAdsJsonDTO custom_ads_json = new CustomAdsJsonDTO();
    private ExtraDataFieldDTO extra_data_field = new ExtraDataFieldDTO();
    private String app_name = "";
    private String package_name;
    private boolean show_ads;
    private String ads_open_type;
    private int ads_count;
    private String version_name;
    private String button_bg;
    private String button_text_color;
    private String common_text_color;
    private String ads_bg;
    private String backpress_ads_type;
    private int backPress_count;

    public String getAds_sequence_type() {
        return ads_sequence_type;
    }

    public void setAds_sequence_type(String ads_sequence_type) {
        this.ads_sequence_type = ads_sequence_type;
    }

    private String ads_sequence_type;

    public String getBackPress_ads_type() {
        return backpress_ads_type;
    }

    public void setBackPress_ads_type(String backPress_ads_type) {
        this.backpress_ads_type = backPress_ads_type;
    }

    public int getBackPress_count() {
        return backPress_count;
    }

    public void setBackPress_count(int backPress_count) {
        this.backPress_count = backPress_count;
    }

    public String getButton_bg() {
        return button_bg;
    }

    public void setButton_bg(String button_bg) {
        this.button_bg = button_bg;
    }

    public String getCommon_text_color() {
        return common_text_color;
    }

    public void setCommon_text_color(String common_text_color) {
        this.common_text_color = common_text_color;
    }

    public String getButton_text_color() {
        return button_text_color;
    }

    public void setButton_text_color(String button_text_color) {
        this.button_text_color = button_text_color;
    }

    public String getAd_bg() {
        return ads_bg;
    }

    public void setAd_bg(String ad_bg) {
        this.ads_bg = ad_bg;
    }


    public String getMonetize_platform() {
        return monetize_platform;
    }

    public void setMonetize_platform(String monetize_platform) {
        this.monetize_platform = monetize_platform;
    }
    @SerializedName(value = "monetize_platform", alternate = {"testads_platform"})
    String monetize_platform;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AppOpenAdsDTO getApp_open_ads() {
        return app_open_ads;
    }

    public void setApp_open_ads(AppOpenAdsDTO app_open_ads) {
        this.app_open_ads = app_open_ads;
    }

    public BannerAdsDTO getBanner_ads() {
        return banner_ads;
    }

    public void setBanner_ads(BannerAdsDTO banner_ads) {
        this.banner_ads = banner_ads;
    }

    public NativeAdsDTO getNative_ads() {
        return native_ads;
    }

    public void setNative_ads(NativeAdsDTO native_ads) {
        this.native_ads = native_ads;
    }

    public InterstitialAdsDTO getInterstitial_ads() {
        return interstitial_ads;
    }

    public void setInterstitial_ads(InterstitialAdsDTO interstitial_ads) {
        this.interstitial_ads = interstitial_ads;
    }

    public RewardedAdsDTO getRewarded_ads() {
        return rewarded_ads;
    }

    public void setRewarded_ads(RewardedAdsDTO rewarded_ads) {
        this.rewarded_ads = rewarded_ads;
    }

    public MobileStickyAdsDTO getMobile_sticky_ads() {
        return mobile_sticky_ads;
    }

    public void setMobile_sticky_ads(MobileStickyAdsDTO mobile_sticky_ads) {
        this.mobile_sticky_ads = mobile_sticky_ads;
    }

    public CollapsibleAdsDTO getCollapsible_ads() {
        return collapsible_ads;
    }

    public void setCollapsible_ads(CollapsibleAdsDTO collapsible_ads) {
        this.collapsible_ads = collapsible_ads;
    }

    public CustomAdsJsonDTO getCustom_ads_json() {
        return custom_ads_json;
    }

    public void setCustom_ads_json(CustomAdsJsonDTO custom_ads_json) {
        this.custom_ads_json = custom_ads_json;
    }

    public ExtraDataFieldDTO getExtra_data_field() {
        return extra_data_field;
    }

    public void setExtra_data_field(ExtraDataFieldDTO extra_data_field) {
        this.extra_data_field = extra_data_field;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public boolean isShow_ads() {
        return show_ads;
    }

    public void setShow_ads(boolean show_ads) {
        this.show_ads = show_ads;
    }

    public String getAds_open_type() {
        return ads_open_type;
    }

    public void setAds_open_type(String ads_open_type) {
        this.ads_open_type = ads_open_type;
    }

    public int getAds_count() {
        return ads_count;
    }

    public void setAds_count(int ads_count) {
        this.ads_count = ads_count;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public static class AppOpenAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class BannerAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class NativeAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class InterstitialAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class RewardedAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class CollapsibleAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class CustomAdsJsonDTO {
    }

    public static class ExtraDataFieldDTO {

        private String wallpaper_base_url;
        private String keyboard_base_url;
        private String ringtone_base_url;
        private int popularWallpaperIndex;
        private int popularkeyboardIndex;
        private int popularringtoneIndex;
        private List<WallpaperColorsDTO> wallpaper_colors;
        private List<WallpaperCategoriesDTO> wallpaper_categories;
        private JsonObject wallpaper_data;
        private List<KeyboardColorsDTO> keyboard_colors;
        private List<KeyboardCategoriesDTO> keyboard_categories;
        private JsonObject keyboard_data;
        private JsonObject ringtone_data;
        private List<RingtoneMoodsDTO> ringtone_moods;
        private List<RingtoneCategoriesDTO> ringtone_categories;

        public int getPopularringtoneIndex() {
            return popularringtoneIndex;
        }

        public void setPopularringtoneIndex(int popularringtoneIndex) {
            this.popularringtoneIndex = popularringtoneIndex;
        }

        public String getRingtone_base_url() {
            return ringtone_base_url;
        }

        public void setRingtone_base_url(String ringtone_base_url) {
            this.ringtone_base_url = ringtone_base_url;
        }

        public JsonObject getRingtone_data() {
            return ringtone_data;
        }

        public void setRingtone_data(JsonObject ringtone_data) {
            this.ringtone_data = ringtone_data;
        }

        public int getPopularWallpaperIndex() {
            return popularWallpaperIndex;
        }

        public void setPopularWallpaperIndex(int popularWallpaperIndex) {
            this.popularWallpaperIndex = popularWallpaperIndex;
        }

        public int getPopularkeyboardIndex() {
            return popularkeyboardIndex;
        }

        public void setPopularkeyboardIndex(int popularkeyboardIndex) {
            this.popularkeyboardIndex = popularkeyboardIndex;
        }

        public String getWallpaper_base_url() {
            return wallpaper_base_url;
        }

        public void setWallpaper_base_url(String wallpaper_base_url) {
            this.wallpaper_base_url = wallpaper_base_url;
        }

        public String getKeyboard_base_url() {
            return keyboard_base_url;
        }

        public void setKeyboard_base_url(String keyboard_base_url) {
            this.keyboard_base_url = keyboard_base_url;
        }

        public List<WallpaperColorsDTO> getWallpaper_colors() {
            return wallpaper_colors;
        }

        public void setWallpaper_colors(List<WallpaperColorsDTO> wallpaper_colors) {
            this.wallpaper_colors = wallpaper_colors;
        }

        public List<WallpaperCategoriesDTO> getWallpaper_categories() {
            return wallpaper_categories;
        }

        public void setWallpaper_categories(List<WallpaperCategoriesDTO> wallpaper_categories) {
            this.wallpaper_categories = wallpaper_categories;
        }

        public JsonObject getWallpaper_data() {
            return wallpaper_data;
        }

        public void setWallpaper_data(JsonObject wallpaper_data) {
            this.wallpaper_data = wallpaper_data;
        }

        public List<KeyboardColorsDTO> getKeyboard_colors() {
            return keyboard_colors;
        }

        public void setKeyboard_colors(List<KeyboardColorsDTO> keyboard_colors) {
            this.keyboard_colors = keyboard_colors;
        }

        public List<KeyboardCategoriesDTO> getKeyboard_categories() {
            return keyboard_categories;
        }

        public void setKeyboard_categories(List<KeyboardCategoriesDTO> keyboard_categories) {
            this.keyboard_categories = keyboard_categories;
        }

        public JsonObject getKeyboard_data() {
            return keyboard_data;
        }

        public void setKeyboard_data(JsonObject keyboard_data) {
            this.keyboard_data = keyboard_data;
        }

        public List<RingtoneMoodsDTO> getRingtone_moods() {
            return ringtone_moods;
        }

        public void setRingtone_moods(List<RingtoneMoodsDTO> ringtone_moods) {
            this.ringtone_moods = ringtone_moods;
        }

        public List<RingtoneCategoriesDTO> getRingtone_categories() {
            return ringtone_categories;
        }

        public void setRingtone_categories(List<RingtoneCategoriesDTO> ringtone_categories) {
            this.ringtone_categories = ringtone_categories;
        }

        public static class WallpaperColorsDTO {
            private String color_name;
            private List<String> ids;

            public String getColor_name() {
                return color_name;
            }

            public void setColor_name(String color_name) {
                this.color_name = color_name;
            }

            public List<String> getIds() {
                return ids;
            }

            public void setIds(List<String> ids) {
                this.ids = ids;
            }
        }

        public static class WallpaperCategoriesDTO {
            private String category_name;
            private String category_desc;
            private List<String> ids;

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public String getCategory_desc() {
                return category_desc;
            }

            public void setCategory_desc(String category_desc) {
                this.category_desc = category_desc;
            }

            public List<String> getIds() {
                return ids;
            }

            public void setIds(List<String> ids) {
                this.ids = ids;
            }
        }

        public static class KeyboardColorsDTO {
            private String color_name;
            private List<String> ids;

            public String getColor_name() {
                return color_name;
            }

            public void setColor_name(String color_name) {
                this.color_name = color_name;
            }

            public List<String> getIds() {
                return ids;
            }

            public void setIds(List<String> ids) {
                this.ids = ids;
            }
        }

        public static class KeyboardCategoriesDTO {
            private String category_name;
            private String category_desc;
            private List<String> ids;

            public String getCategory_desc() {
                return category_desc;
            }

            public void setCategory_desc(String category_desc) {
                this.category_desc = category_desc;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public List<String> getIds() {
                return ids;
            }

            public void setIds(List<String> ids) {
                this.ids = ids;
            }
        }

        public static class RingtoneMoodsDTO {
            private String ringtone_name;
            private String moode_img;
            private List<String> ids;

            public String getRingtone_name() {
                return ringtone_name;
            }

            public void setRingtone_name(String ringtone_name) {
                this.ringtone_name = ringtone_name;
            }

            public String getMoode_img() {
                return moode_img;
            }

            public void setMoode_img(String moode_img) {
                this.moode_img = moode_img;
            }

            public List<String> getIds() {
                return ids;
            }

            public void setIds(List<String> ids) {
                this.ids = ids;
            }
        }

        public static class RingtoneCategoriesDTO {
            private String category_name;
            private String category_desc;
            private String category_img;
            private List<String> ids;

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public String getCategory_desc() {
                return category_desc;
            }

            public void setCategory_desc(String category_desc) {
                this.category_desc = category_desc;
            }

            public String getCategory_img() {
                return category_img;
            }

            public void setCategory_img(String category_img) {
                this.category_img = category_img;
            }

            public List<String> getIds() {
                return ids;
            }

            public void setIds(List<String> ids) {
                this.ids = ids;
            }
        }
    }

    private class MobileStickyAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }
}
