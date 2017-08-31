package org.codeforafrica.citizenreporterandroid.storyboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Ahereza on 8/22/17.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.attached_image) ImageView attachedImage;

  @BindView(R.id.image_filename_tv) TextView image_filenameTV;

  @BindView(R.id.image_filesize_tv) TextView image_filesizeTV;

  public ImageViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
