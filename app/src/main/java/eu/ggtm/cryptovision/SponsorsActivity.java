package eu.ggtm.cryptovision;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Vibrator;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class SponsorsActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private String versionInfo = "";
	private String buildInfo = "";
	
	private LinearLayout sponsorsBase;
	private LinearLayout headEdge;
	private HorizontalScrollView topSpacer;
	private TextView sponsorsTitle;
	private TextView settingsInfo;
	private ImageView adBanner1;
	private ImageView adBanner2;
	private ImageView adBanner3;
	private ImageView adBanner4;
	private AdView adBanner5;
	private ScrollView upPusher;
	private LinearLayout footHeader;
	private TextView backButton;
	private ImageView homeButton;
	private TextView versionSpam;
	private ImageView cVnLogo;
	
	private SharedPreferences memory;
	private Intent hopActivity = new Intent();
	private Vibrator touchBzz;
	private TimerTask tapFlash;
	private Intent openAd = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.sponsors);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		sponsorsBase = findViewById(R.id.sponsorsBase);
		headEdge = findViewById(R.id.headEdge);
		topSpacer = findViewById(R.id.topSpacer);
		sponsorsTitle = findViewById(R.id.sponsorsTitle);
		settingsInfo = findViewById(R.id.settingsInfo);
		adBanner1 = findViewById(R.id.adBanner1);
		adBanner2 = findViewById(R.id.adBanner2);
		adBanner3 = findViewById(R.id.adBanner3);
		adBanner4 = findViewById(R.id.adBanner4);
		adBanner5 = findViewById(R.id.adBanner5);
		upPusher = findViewById(R.id.upPusher);
		footHeader = findViewById(R.id.footHeader);
		backButton = findViewById(R.id.backButton);
		homeButton = findViewById(R.id.homeButton);
		versionSpam = findViewById(R.id.versionSpam);
		cVnLogo = findViewById(R.id.cVnLogo);
		memory = getSharedPreferences("memory", Activity.MODE_PRIVATE);
		touchBzz = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		adBanner1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				openAd.setAction(Intent.ACTION_VIEW);
				openAd.setData(Uri.parse("https://cryptotabbrowser.com/40900491"));
				startActivity(openAd);
			}
		});
		
		adBanner2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				openAd.setAction(Intent.ACTION_VIEW);
				openAd.setData(Uri.parse("https://cryptotabfarm.com/join-farm-now/3EHR8K13"));
				startActivity(openAd);
			}
		});
		
		adBanner3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				openAd.setAction(Intent.ACTION_VIEW);
				openAd.setData(Uri.parse("https://pool.cryptobrowser.site/landing/?aid=40900491"));
				startActivity(openAd);
			}
		});
		
		adBanner4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				openAd.setAction(Intent.ACTION_VIEW);
				openAd.setData(Uri.parse("https://freebitco.in/?r=669276"));
				startActivity(openAd);
			}
		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				backButton.setTextColor(0xFF350000);
				touchBzz.vibrate((long)(100));
				tapFlash = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								backButton.setTextColor(0xFFFDD835);
								hopActivity.setAction(Intent.ACTION_VIEW);
								hopActivity.setClass(getApplicationContext(), SettingsActivity.class);
								startActivity(hopActivity);
								finish();
							}
						});
					}
				};
				_timer.schedule(tapFlash, (int)(50));
			}
		});
		
		homeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				homeButton.setColorFilter(0xFF350000, PorterDuff.Mode.MULTIPLY);
				touchBzz.vibrate((long)(100));
				tapFlash = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								hopActivity.setAction(Intent.ACTION_VIEW);
								hopActivity.setClass(getApplicationContext(), MainActivity.class);
								startActivity(hopActivity);
								finish();
							}
						});
					}
				};
				_timer.schedule(tapFlash, (int)(50));
			}
		});
	}
	
	private void initializeLogic() {
		versionInfo = memory.getString("Version", "");
		buildInfo = memory.getString("Build", "");
		versionSpam.setText("v".concat(versionInfo.concat(" [b".concat(buildInfo.concat("] ~ PhoeniX")))));
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		{
			android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
			int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
			int clrs [] = {0xFF333333,0xFF757575};
			SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP, clrs);
			SketchUi.setCornerRadii(new float[]{
				d*20,d*20,d*20 ,d*20,d*0,d*0 ,d*0,d*0});
			headEdge.setElevation(d*5);
			headEdge.setBackground(SketchUi);
		}
		{
			android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
			int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
			int clrs [] = {0xFF757575,0xFF333333};
			SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP, clrs);
			SketchUi.setCornerRadii(new float[]{
				d*0,d*0,d*0 ,d*0,d*20,d*20 ,d*20,d*20});
			footHeader.setElevation(d*5);
			android.graphics.drawable.RippleDrawable SketchUi_RD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFB71C1C}), SketchUi, null);
			footHeader.setBackground(SketchUi_RD);
			footHeader.setClickable(true);
		}
		
		{
			AdRequest adRequest = new AdRequest.Builder().build();
			adBanner5.loadAd(adRequest);
		}
	}
	
	@Override
	public void onBackPressed() {
		hopActivity.setAction(Intent.ACTION_VIEW);
		hopActivity.setClass(getApplicationContext(), SettingsActivity.class);
		startActivity(hopActivity);
		finish();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adBanner5 != null) {
			adBanner5.destroy();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (adBanner5 != null) {
			adBanner5.pause();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (adBanner5 != null) {
			adBanner5.resume();
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}