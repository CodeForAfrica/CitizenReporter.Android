package org.codeforafrica.citizenreporterandroid.data.models;

import android.graphics.drawable.Drawable;

/**
 * Created by Ahereza on 8/15/17.
 */

public class SceneItem {
  public String getHeading() {
    return heading;
  }

  public void setHeading(String heading) {
    this.heading = heading;
  }

  public String heading;

  public String getSubheading() {
    return subheading;
  }

  public void setSubheading(String subheading) {
    this.subheading = subheading;
  }

  public String subheading;

  public Drawable getSceneImage() {
    return sceneImage;
  }

  public void setSceneImage(Drawable sceneImage) {
    this.sceneImage = sceneImage;
  }

  public Drawable sceneImage;
}
