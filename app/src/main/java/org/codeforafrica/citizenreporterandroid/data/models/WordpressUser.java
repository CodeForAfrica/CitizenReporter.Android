package org.codeforafrica.citizenreporterandroid.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahereza on 9/21/17.
 */

public class WordpressUser {
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("user_id")
  @Expose
  private Integer userId;
  @SerializedName("username")
  @Expose
  private String username;
  @SerializedName("password")
  @Expose
  private String password;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("first_name")
  @Expose
  private String firstName;
  @SerializedName("last_name")
  @Expose
  private String lastName;
  @SerializedName("phone_number")
  @Expose
  private String phoneNumber;
  @SerializedName("location")
  @Expose
  private String location;
  @SerializedName("address")
  @Expose
  private String address;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
