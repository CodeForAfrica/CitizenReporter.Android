package org.codeforafrica.citizenreporterandroid.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Mugiwara_Munyi on 16/08/2017.
 */

public class HelperOverlay extends Activity {
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.photo_overlay);
        showOverlay();
    }

    private void showOverlay(){

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        dialog.setContentView(R.layout.photo_overlay);
        LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.overlayLayout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }
}
