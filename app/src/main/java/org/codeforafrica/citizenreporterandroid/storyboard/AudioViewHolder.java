package org.codeforafrica.citizenreporterandroid.storyboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Ahereza on 8/22/17.
 */

public class AudioViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.audio_filename_tv) TextView audio_filenameTV;

  @BindView(R.id.audio_filesize_tv) TextView audio_filesizeTV;

  public AudioViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
