package org.codeforafrica.citizenreporterandroid.camera;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import org.codeforafrica.citizenreporterandroid.GlideApp;
import org.codeforafrica.citizenreporterandroid.R;

public class SceneSelectorAdapter extends RecyclerView.Adapter<SceneSelectorAdapter.ViewHolder> {
	private static int rotationAngle;
	private String sceneKey;
	private ArrayList<Integer> scenesList;
	private OnClickThumbListener onClickThumbListener;

	public interface OnClickThumbListener {
		void OnClickScene(String sceneKey, Integer position);
	}

	public SceneSelectorAdapter(Activity activity, String sceneKey, ArrayList<Integer> sceneList) {
		this.sceneKey = sceneKey;
		this.scenesList = sceneList;
		this.onClickThumbListener = (OnClickThumbListener) activity;
		if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			rotationAngle = -90;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.fragment_selector_view, parent, false);
		return new ViewHolder(view);
	}

	@Override public void onBindViewHolder(ViewHolder holder, int position) {
		holder.position = position;
		holder.bindScene(scenesList.get(position));
	}

	@Override public int getItemCount() {
		return this.scenesList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.scene_image_view) ImageView imageView;
		private Integer position;

		private ViewHolder(final View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			imageView.setOnClickListener(this);
		}

		private void bindScene(Integer sceneID) {
			if(rotationAngle == -90) itemView.setRotation(rotationAngle);
			GlideApp.with(itemView)
					.load(null)
					.placeholder(sceneID)
					.into(imageView);
		}

		@Override public void onClick(View view) {
			onClickThumbListener.OnClickScene(sceneKey, position);
		}
	}
}
