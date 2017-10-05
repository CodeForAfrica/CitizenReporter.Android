package org.codeforafrica.citizenreporterandroid.camera;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.MediaStoreSignature;
import java.io.File;
import java.io.FileOutputStream;
import org.codeforafrica.citizenreporterandroid.R;

public class ViewImageActivity extends AppCompatActivity {
	private static final String IMAGE_FILE_LOCATION = "image_file_location";
	private static final String IMAGE_SAVED_PATH = "imagePath";
	private static final String TAG = ViewImageActivity.class.getSimpleName();
	private float rotationAngle;
	private File imageFile;

	@BindView(R.id.img_view) ImageView imageView;
	@BindView(R.id.img_save_btn) ImageView saveBtn;
	@BindView(R.id.img_close_btn) ImageView closeBtn;
	@BindView(R.id.btn_rotate_clockwise) ImageView rotateClockwise;
	@BindView(R.id.btn_rotate_anti_clockwise) ImageView rotateCounterClockwise;

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		View decorView = getWindow().getDecorView();
		if (hasFocus) {
			decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_FULLSCREEN);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_image);
		ButterKnife.bind(this);

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		imageFile = new File(
				getIntent().getStringExtra(IMAGE_FILE_LOCATION)
		);

		GlideApp.with(this)
				.load(imageFile)
				.override(width, height)
				.skipMemoryCache(true)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.into(imageView);

		closeBtn.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				boolean deleted = imageFile.delete();
				if (deleted) {
					MediaScannerConnection.scanFile(getApplicationContext(),
							new String[] { getIntent().getStringExtra(IMAGE_FILE_LOCATION) }, null, null);
					setResult(Activity.RESULT_CANCELED);
					ViewImageActivity.super.finish();
				} else {
					Toast.makeText(getApplicationContext(), "Problem deleting picture", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		saveBtn.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra(IMAGE_SAVED_PATH, imageFile.getAbsolutePath());
				setResult(Activity.RESULT_OK, resultIntent);
				ViewImageActivity.super.finish();
			}
		});

		rotateClockwise.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				loadRotation(90);
			}
		});

		rotateCounterClockwise.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				loadRotation(-90);
			}
		});
	}

	private void loadRotation(float angle) {
		rotationAngle = angle;
		new RotateBitmapTask().execute(angle);
	}

	public static Bitmap rotate(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, false);
	}

	private class RotateBitmapTask extends AsyncTask<Float, Void, Void> {
		ProgressDialog dialog;

		@Override protected Void doInBackground(Float... rotation) {
			Bitmap bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			try {
				Bitmap finalBitmap = rotate(bmp, rotationAngle);
				FileOutputStream out = new FileOutputStream(imageFile);
				finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(ViewImageActivity.this, "Please wait ...", "rotating");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			long longDate = System.currentTimeMillis();
			GlideApp.with(getApplicationContext())
					.load(imageFile)
					.signature(new MediaStoreSignature("image/jpeg", longDate, (int) rotationAngle))
					.into(imageView);
			dialog.dismiss();
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}
