package org.codeforafrica.citizenreporterandroid.storyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Ahereza on 8/15/17.
 */

public class SceneAdapter extends ArrayAdapter<String> {
  private String[] sceneTitles;
  private String[] sceneDescriptions;
  private TypedArray sceneImages;
  private Context context;

  public SceneAdapter(Context _context, String[] _sceneTitles, String[] _sceneDescriptions,
      TypedArray _sceneImages, int row_pick_scene) {
    super(_context, row_pick_scene);
    this.sceneTitles = _sceneTitles;
    this.sceneDescriptions = _sceneDescriptions;
    this.sceneImages = _sceneImages;
    this.context = _context;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    View v = convertView;

    if (v == null) {
      LayoutInflater vi;
      vi = LayoutInflater.from(getContext());
      v = vi.inflate(R.layout.row_scene, null);
    }

    TextView tt1 = (TextView) v.findViewById(R.id.scene_head);
    TextView tt2 = (TextView) v.findViewById(R.id.scene_sub);
    ImageView tt3 = (ImageView) v.findViewById(R.id.scene_image);

    if (tt1 != null) {
      tt1.setText(sceneTitles[position]);
    }

    if (tt2 != null) {
      tt2.setText(sceneDescriptions[position]);
    }

    if (tt3 != null) {
      tt3.setImageResource(sceneImages.getResourceId(position, -1));
    }

    return v;
  }

  @Override public int getCount() {
    // TODO Auto-generated method stub
    return sceneTitles.length;
  }
}
