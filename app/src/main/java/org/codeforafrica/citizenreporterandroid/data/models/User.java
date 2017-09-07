package org.codeforafrica.citizenreporterandroid.data.models;

/**
 * Created by Ahereza on 8/1/17.
 */

public class User {
  private String name;
  private String uid;
  private String fcm_token;
  private String profile_pic;

  public User(String name, String uid, String profile_pic_url) {
    this.name = name;
    this.uid = uid;
    this.profile_pic = profile_pic_url;
    this.fcm_token = "";
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getFcm_token() {
    return fcm_token;
  }

  public void setFcm_token(String fcm_token) {
    this.fcm_token = fcm_token;
  }

  public String getProfile_pic_url() {
    return profile_pic;
  }

  public void setProfile_pic_url(String profile_pic_url) {
    this.profile_pic = profile_pic_url;
  }
}
