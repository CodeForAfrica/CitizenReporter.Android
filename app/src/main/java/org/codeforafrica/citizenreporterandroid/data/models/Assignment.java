package org.codeforafrica.citizenreporterandroid.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Assignment {

  @SerializedName("id") @Expose private String id;
  @SerializedName("title") @Expose private String title;
  @SerializedName("description") @Expose private String description;
  @SerializedName("required_media") @Expose private String requiredMedia;
  @SerializedName("featured_image") @Expose private String featuredImage;
  @SerializedName("number_of_responses") @Expose private int numberOfResponses;
  @SerializedName("deadline") @Expose private Date deadline;
  @SerializedName("author") @Expose private String author;
  @SerializedName("assignment_location") @Expose private String assignmentLocation;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRequiredMedia() {
    return requiredMedia;
  }

  public void setRequiredMedia(String requiredMedia) {
    this.requiredMedia = requiredMedia;
  }

  public String getFeaturedImage() {
    return featuredImage;
  }

  public void setFeaturedImage(String featuredImage) {
    this.featuredImage = featuredImage;
  }

  public int getNumberOfResponses() {
    return numberOfResponses;
  }

  public void setNumberOfResponses(int numberOfResponses) {
    this.numberOfResponses = numberOfResponses;
  }

  public Date getDeadline() {
    return deadline;
  }

  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getAssignmentLocation() {
    return assignmentLocation;
  }

  public void setAssignmentLocation(String assignmentLocation) {
    this.assignmentLocation = assignmentLocation;
  }
}
