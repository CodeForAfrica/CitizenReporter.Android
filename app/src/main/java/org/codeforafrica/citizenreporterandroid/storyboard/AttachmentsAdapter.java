package org.codeforafrica.citizenreporterandroid.storyboard;

/**
 * Created by Ahereza on 8/22/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import com.squareup.picasso.Picasso;

import org.codeforafrica.citizenreporterandroid.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;


/**
 * Created by Ahereza on 8/21/17.
 */

public class AttachmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIDEO = 101, IMAGE = 103, AUDIO = 104;


    private List<String> items;
    private Context context;

    public AttachmentsAdapter(List<String> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ADAPTER", "on created");
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIDEO:
                View videoView = inflater.inflate(R.layout.item_video, parent, false);
                viewHolder = new VideoViewHolder(videoView);
                break;
            case IMAGE:
                View imageView = inflater.inflate(R.layout.item_image, parent, false);
                viewHolder = new ImageViewHolder(imageView);
                break;
            case AUDIO:
                View audioView = inflater.inflate(R.layout.item_audio, parent, false);
                viewHolder = new AudioViewHolder(audioView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String path = items.get(position);
        switch (holder.getItemViewType()) {
            case IMAGE:
                ImageViewHolder vh1 = (ImageViewHolder) holder;
                Picasso.with(context).load(path).into(vh1.attachedImage);
                vh1.image_filenameTV.setText(getFileName(path));
                vh1.image_filesizeTV.setText(getFileSize(path));
                break;
            case AUDIO:
                AudioViewHolder vh2 = (AudioViewHolder) holder;
                vh2.audio_filenameTV.setText(getFileName(path));
                vh2.audio_filesizeTV.setText(getFileSize(path));
                break;
            case VIDEO:
                VideoViewHolder vh3 = (VideoViewHolder) holder;
                vh3.videoFilenameTV.setText(getFileName(path));
                vh3.video_filesizeTV.setText(getFileSize(path));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        String mimeType = getMimeType(items.get(position));
        if (mimeType.startsWith("image")) {
            return IMAGE;
        } else if (mimeType.startsWith("audio")) {
            return AUDIO;
        } else if (mimeType.startsWith("video")) {
            return VIDEO;
        } else {
            return -1;
        }


    }

    public void setAttachmentItems(List<String> items) {
        this.items = items;
    }

    public void updateList(List<String> items) {
        if (items != null && items.size() > 0) {
            items.clear();
            items.addAll(items);
            notifyDataSetChanged();
        }
    }



    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public String getFileSize(String uri) {
        File file = new File(uri);
        long length = file.length();
        length = length/(1024*1024);
        DecimalFormat df = new DecimalFormat("#.#");
        df.format(length);
        return "" + length + "MB";

    }

    public String getFileName(String uri) {
        File file = new File(uri);
        return file.getName();
    }




}

