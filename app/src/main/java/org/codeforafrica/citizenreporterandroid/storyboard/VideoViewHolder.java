package org.codeforafrica.citizenreporterandroid.storyboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.codeforafrica.citizenreporterandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahereza on 8/22/17.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.video_filename_tv)
    TextView videoFilenameTV;

    @BindView(R.id.video_filesize_tv)
    TextView video_filesizeTV;

    public VideoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
