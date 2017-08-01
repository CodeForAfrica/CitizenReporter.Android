package org.codeforafrica.citizenreporterandroid.data.models;

/**
 * Created by Ahereza on 8/1/17.
 */

public class User {
    private String name;
    private String fb_id;
    private String fcm_token;
    private String profile_pic_url;

    public User(String name, String fb_id, String profile_pic_url) {
        this.name = name;
        this.fb_id = fb_id;
        this.profile_pic_url = profile_pic_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }
}
