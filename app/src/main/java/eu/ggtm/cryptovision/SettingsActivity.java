package eu.ggtm.cryptovision;

import android.animation.*;
import android.animation.ObjectAnimator;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.widget.*;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
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

public class SettingsActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private String versionInfo = "";
	private String buildInfo = "";
	private double tapCounter = 0;
	private boolean silencedEgg = false;
	private boolean justSpawned = false;
	private double turnedSponsor = 0;
	private double adsOnTaps = 0;
	private double adsOffTaps = 0;
	private double shelfOnTaps = 0;
	private double shelfOffTaps = 0;
	private double bordersOnTaps = 0;
	private double bordersOffTaps = 0;
	private double exitOnTaps = 0;
	private double exitOffTaps = 0;
	private double fullscreenOnTaps = 0;
	private double fullscreenOffTaps = 0;
	private double eggOnTimes = 0;
	private double eggOffTaps = 0;
	private double clockOnTaps = 0;
	private double clockOffTaps = 0;
	private double backTaps = 0;
	
	private LinearLayout settingsBase;
	private LinearLayout headEdge;
	private HorizontalScrollView topSpacer;
	private TextView settingsTitle;
	private TextView settingsInfo;
	private Switch settingAds;
	private Switch settingShelf;
	private Switch settingBorders;
	private Switch settingExitbutton;
	private Switch settingFullscreen;
	private Switch settingClock;
	private LinearLayout optionDigiAna;
	private Switch settingEgg;
	private ScrollView upPusher;
	private AdView settingsAd;
	private LinearLayout footHeader;
	private RadioButton settingDigital;
	private RadioButton settingAnalog;
	private TextView backButton;
	private TextView sponsorButton;
	private TextView versionSpam;
	private ImageView cVnLogo;
	
	private SharedPreferences memory;
	private Intent hopActivity = new Intent();
	private TimerTask tapFlash;
	private TimerTask unsilenceEgg;
	private Vibrator touchBzz;
	private TimerTask turnSponsor;
	private ObjectAnimator bannerAdShow = new ObjectAnimator();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.settings);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		settingsBase = findViewById(R.id.settingsBase);
		headEdge = findViewById(R.id.headEdge);
		topSpacer = findViewById(R.id.topSpacer);
		settingsTitle = findViewById(R.id.settingsTitle);
		settingsInfo = findViewById(R.id.settingsInfo);
		settingAds = findViewById(R.id.settingAds);
		settingShelf = findViewById(R.id.settingShelf);
		settingBorders = findViewById(R.id.settingBorders);
		settingExitbutton = findViewById(R.id.settingExitbutton);
		settingFullscreen = findViewById(R.id.settingFullscreen);
		settingClock = findViewById(R.id.settingClock);
		optionDigiAna = findViewById(R.id.optionDigiAna);
		settingEgg = findViewById(R.id.settingEgg);
		upPusher = findViewById(R.id.upPusher);
		settingsAd = findViewById(R.id.settingsAd);
		footHeader = findViewById(R.id.footHeader);
		settingDigital = findViewById(R.id.settingDigital);
		settingAnalog = findViewById(R.id.settingAnalog);
		backButton = findViewById(R.id.backButton);
		sponsorButton = findViewById(R.id.sponsorButton);
		versionSpam = findViewById(R.id.versionSpam);
		cVnLogo = findViewById(R.id.cVnLogo);
		memory = getSharedPreferences("memory", Activity.MODE_PRIVATE);
		touchBzz = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		settingAds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (!justSpawned) {
					touchBzz.vibrate((long)(100));
				}
				if (_isChecked) {
					memory.edit().putString("Ads", "Allowed").commit();
					adsOnTaps++;
					memory.edit().putString("AdsOnCount", String.valueOf((long)(adsOnTaps))).commit();
				}
				else {
					memory.edit().putString("Ads", "Disabled").commit();
					adsOffTaps++;
					memory.edit().putString("AdsOffCount", String.valueOf((long)(adsOffTaps))).commit();
				}
			}
		});
		
		settingShelf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (!justSpawned) {
					touchBzz.vibrate((long)(100));
				}
				if (_isChecked) {
					memory.edit().putString("Shelf", "On").commit();
					shelfOnTaps++;
					memory.edit().putString("ShelfOnCount", String.valueOf((long)(shelfOnTaps))).commit();
					settingAds.setEnabled(true);
				}
				else {
					memory.edit().putString("Shelf", "Off").commit();
					shelfOffTaps++;
					memory.edit().putString("ShelfOffCount", String.valueOf((long)(shelfOffTaps))).commit();
					settingAds.setEnabled(false);
				}
			}
		});
		
		settingBorders.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (!justSpawned) {
					touchBzz.vibrate((long)(100));
				}
				if (_isChecked) {
					memory.edit().putString("Borders", "On").commit();
					bordersOnTaps++;
					memory.edit().putString("BordersOnCount", String.valueOf((long)(bordersOnTaps))).commit();
					{
						settingsBase.setPadding(8,0,8,0);
					}
				}
				else {
					memory.edit().putString("Borders", "Off").commit();
					bordersOffTaps++;
					memory.edit().putString("BordersOffCount", String.valueOf((long)(bordersOffTaps))).commit();
					{
						settingsBase.setPadding(0,0,0,0);
					}
				}
			}
		});
		
		settingExitbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (!justSpawned) {
					touchBzz.vibrate((long)(100));
				}
				if (_isChecked) {
					memory.edit().putString("Exitbutton", "On").commit();
					exitOnTaps++;
					memory.edit().putString("ExitbtnOnCount", String.valueOf((long)(exitOnTaps))).commit();
				}
				else {
					memory.edit().putString("Exitbutton", "Off").commit();
					exitOffTaps++;
					memory.edit().putString("ExitbtnOffCount", String.valueOf((long)(exitOffTaps))).commit();
				}
			}
		});
		
		settingFullscreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (!justSpawned) {
					touchBzz.vibrate((long)(100));
				}
				if (_isChecked) {
					memory.edit().putString("Fullscreen", "Enabled").commit();
					fullscreenOnTaps++;
					memory.edit().putString("FullscrOnCount", String.valueOf((long)(fullscreenOnTaps))).commit();
					_hideNotibar();
				}
				else {
					memory.edit().putString("Fullscreen", "Disabled").commit();
					fullscreenOffTaps++;
					memory.edit().putString("FullscrOffCount", String.valueOf((long)(fullscreenOffTaps))).commit();
					_showNotibar();
				}
			}
		});
		
		settingClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					memory.edit().putString("Time", "Digital").commit();
					clockOnTaps++;
					memory.edit().putString("ClockOnCount", String.valueOf((long)(clockOnTaps))).commit();
					settingDigital.setChecked(true);
					settingAnalog.setChecked(false);
					optionDigiAna.setVisibility(View.VISIBLE);
				}
				else {
					memory.edit().putString("Time", "Off").commit();
					clockOffTaps++;
					memory.edit().putString("ClockOffCount", String.valueOf((long)(clockOffTaps))).commit();
					optionDigiAna.setVisibility(View.GONE);
					settingDigital.setChecked(false);
					settingAnalog.setChecked(false);
				}
			}
		});
		
		settingEgg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (!justSpawned) {
					touchBzz.vibrate((long)(100));
				}
				if (_isChecked) {
					memory.edit().putString("Egg", "Activated").commit();
					eggOnTimes++;
					memory.edit().putString("EggOnCount", String.valueOf((long)(eggOnTimes))).commit();
					settingEgg.setVisibility(View.VISIBLE);
					tapCounter = 15;
				}
				else {
					memory.edit().putString("Egg", "Disabled").commit();
					eggOffTaps++;
					memory.edit().putString("EggOffCount", String.valueOf((long)(eggOffTaps))).commit();
					tapCounter = 0;
					settingEgg.setVisibility(View.GONE);
				}
			}
		});
		
		settingDigital.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (!justSpawned) {
					touchBzz.vibrate((long)(100));
				}
				if (_isChecked) {
					memory.edit().putString("Time", "Digital").commit();
					settingAnalog.setChecked(false);
				}
			}
		});
		
		settingAnalog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (!justSpawned) {
					touchBzz.vibrate((long)(100));
				}
				if (_isChecked) {
					memory.edit().putString("Time", "Analog").commit();
					settingDigital.setChecked(false);
				}
			}
		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				touchBzz.vibrate((long)(100));
				backTaps++;
				memory.edit().putString("SettingsBackCount", String.valueOf((long)(backTaps))).commit();
				hopActivity.setAction(Intent.ACTION_VIEW);
				hopActivity.setClass(getApplicationContext(), MainActivity.class);
				startActivity(hopActivity);
				finish();
			}
		});
		
		sponsorButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				touchBzz.vibrate((long)(100));
				hopActivity.setAction(Intent.ACTION_VIEW);
				hopActivity.setClass(getApplicationContext(), SponsorsActivity.class);
				startActivity(hopActivity);
				finish();
			}
		});
		
		cVnLogo.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				if (!silencedEgg) {
					touchBzz.vibrate((long)(111));
					silencedEgg = true;
					unsilenceEgg = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									silencedEgg = false;
								}
							});
						}
					};
					_timer.schedule(unsilenceEgg, (int)(1500));
					tapCounter++;
					if (tapCounter == 4) {
						SketchwareUtil.showMessage(getApplicationContext(), "Can you stop doing that?");
					}
					if (tapCounter == 6) {
						SketchwareUtil.showMessage(getApplicationContext(), "Seriously, you might break something!");
					}
					if (tapCounter == 9) {
						SketchwareUtil.showMessage(getApplicationContext(), "Why must you annoy me?!");
					}
					if (tapCounter == 11) {
						SketchwareUtil.showMessage(getApplicationContext(), "Don't tap 3 more times!");
					}
					if (tapCounter == 12) {
						SketchwareUtil.showMessage(getApplicationContext(), "Don't tap 2 more times");
					}
					if (tapCounter == 14) {
						SketchwareUtil.showMessage(getApplicationContext(), "Now you've done it!");
						eggOnTimes++;
						memory.edit().putString("EggOnCount", String.valueOf((long)(eggOnTimes))).commit();
						memory.edit().putString("Egg", "Activated").commit();
						settingEgg.setVisibility(View.VISIBLE);
					}
					if (tapCounter > 14) {
						SketchwareUtil.showMessage(getApplicationContext(), "Changes were made...");
					}
				}
				return true;
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("cVn App-Settings");
		upPusher.setVisibility(View.VISIBLE);
		sponsorButton.setVisibility(View.GONE);
		settingEgg.setVisibility(View.GONE);
		versionInfo = memory.getString("Version", "");
		buildInfo = memory.getString("Build", "");
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		{
			android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
			int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
			int clrs [] = {0xFF000000,0xFF757575};
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
			android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
			int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
			int clrs [] = {0xFF000000,0xFF333333};
			SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, clrs);
			SketchUi.setCornerRadii(new float[]{
				d*0,d*0,d*0 ,d*0,d*21,d*21 ,d*21,d*21});
			settingsBase.setElevation(d*5);
			settingsBase.setBackground(SketchUi);
		}
		versionSpam.setText("v".concat(versionInfo.concat(" [b".concat(buildInfo.concat("] ~ PhoeniX")))));
		justSpawned = true;
		silencedEgg = false;
		if (memory.getString("Ads", "").equals("Disabled")) {
			settingAds.setChecked(false);
		}
		else {
			settingAds.setChecked(true);
		}
		if (memory.contains("AdsOnCount")) {
			adsOnTaps = Double.parseDouble(memory.getString("AdsOnCount", ""));
		}
		else {
			adsOnTaps = 0;
		}
		if (memory.contains("AdsOffCount")) {
			adsOffTaps = Double.parseDouble(memory.getString("AdsOffCount", ""));
		}
		else {
			adsOffTaps = 0;
		}
		if (memory.getString("Shelf", "").equals("Off")) {
			settingShelf.setChecked(false);
			settingAds.setEnabled(false);
		}
		else {
			settingShelf.setChecked(true);
			settingAds.setEnabled(true);
		}
		if (memory.contains("ShelfOnCount")) {
			shelfOnTaps = Double.parseDouble(memory.getString("ShelfOnCount", ""));
		}
		else {
			shelfOnTaps = 0;
		}
		if (memory.contains("ShelfOffCount")) {
			shelfOffTaps = Double.parseDouble(memory.getString("ShelfOffCount", ""));
		}
		else {
			shelfOffTaps = 0;
		}
		if (memory.getString("Borders", "").equals("Off")) {
			{
				settingsBase.setPadding(0,0,0,0);
			}
			settingBorders.setChecked(false);
		}
		else {
			{
				settingsBase.setPadding(8,0,8,0);
			}
			settingBorders.setChecked(true);
		}
		if (memory.contains("BordersOnCount")) {
			bordersOnTaps = Double.parseDouble(memory.getString("BordersOnCount", ""));
		}
		else {
			bordersOnTaps = 0;
		}
		if (memory.contains("BordersOffCount")) {
			bordersOffTaps = Double.parseDouble(memory.getString("BordersOffCount", ""));
		}
		else {
			bordersOffTaps = 0;
		}
		if (memory.getString("Exitbutton", "").equals("On")) {
			settingExitbutton.setChecked(true);
		}
		else {
			settingExitbutton.setChecked(false);
		}
		if (memory.contains("ExitbtnOnCount")) {
			exitOnTaps = Double.parseDouble(memory.getString("ExitbtnOnCount", ""));
		}
		else {
			exitOnTaps = 0;
		}
		if (memory.contains("ExitbtnOffCount")) {
			exitOffTaps = Double.parseDouble(memory.getString("ExitbtnOffCount", ""));
		}
		else {
			exitOffTaps = 0;
		}
		if (memory.getString("Fullscreen", "").equals("Enabled")) {
			_hideNotibar();
			settingFullscreen.setChecked(true);
		}
		else {
			_showNotibar();
			settingFullscreen.setChecked(false);
		}
		if (memory.contains("FullscrOnCount")) {
			fullscreenOnTaps = Double.parseDouble(memory.getString("FullscrOnCount", ""));
		}
		else {
			fullscreenOnTaps = 0;
		}
		if (memory.contains("FullscrOffCount")) {
			fullscreenOffTaps = Double.parseDouble(memory.getString("FullscrOffCount", ""));
		}
		else {
			fullscreenOffTaps = 0;
		}
		if (memory.getString("Egg", "").equals("Activated")) {
			settingEgg.setVisibility(View.VISIBLE);
			tapCounter = 15;
			settingEgg.setChecked(true);
		}
		else {
			settingEgg.setVisibility(View.GONE);
			tapCounter = 0;
			settingEgg.setChecked(false);
		}
		if (memory.contains("EggOnCount")) {
			eggOnTimes = Double.parseDouble(memory.getString("EggOnCount", ""));
		}
		else {
			eggOnTimes = 0;
		}
		if (memory.contains("EggOffCount")) {
			eggOffTaps = Double.parseDouble(memory.getString("EggOffCount", ""));
		}
		else {
			eggOffTaps = 0;
		}
		if (memory.getString("Time", "").equals("Digital")) {
			settingClock.setChecked(true);
			settingDigital.setChecked(true);
			settingAnalog.setChecked(false);
			optionDigiAna.setVisibility(View.VISIBLE);
		}
		else {
			if (memory.getString("Time", "").equals("Analog")) {
				settingClock.setChecked(true);
				settingDigital.setChecked(false);
				settingAnalog.setChecked(true);
				optionDigiAna.setVisibility(View.VISIBLE);
			}
			else {
				settingClock.setChecked(false);
				optionDigiAna.setVisibility(View.GONE);
				settingDigital.setChecked(false);
				settingAnalog.setChecked(false);
			}
		}
		if (memory.contains("ClockOnCount")) {
			clockOnTaps = Double.parseDouble(memory.getString("ClockOnCount", ""));
		}
		else {
			clockOnTaps = 0;
		}
		if (memory.contains("ClockOffCount")) {
			clockOffTaps = Double.parseDouble(memory.getString("ClockOffCount", ""));
		}
		else {
			clockOffTaps = 0;
		}
		if (memory.contains("SettingsBackCount")) {
			backTaps = Double.parseDouble(memory.getString("SettingsBackCount", ""));
		}
		else {
			backTaps = 0;
		}
		turnedSponsor = 0;
		turnSponsor = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (turnedSponsor < 359) {
							turnedSponsor++;
						}
						else {
							turnedSponsor = 0;
						}
						sponsorButton.setRotation((float)(turnedSponsor));
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(turnSponsor, (int)(25), (int)(25));
		
		{
			AdRequest adRequest = new AdRequest.Builder().build();
			settingsAd.loadAd(adRequest);
		}
		bannerAdShow.setTarget(settingsAd);
		bannerAdShow.setPropertyName("alpha");
		bannerAdShow.setFloatValues((float)(0.0d), (float)(1.0d));
		bannerAdShow.setDuration((int)(1000));
		bannerAdShow.start();
		justSpawned = false;
	}
	
	@Override
	public void onBackPressed() {
		hopActivity.setAction(Intent.ACTION_VIEW);
		hopActivity.setClass(getApplicationContext(), MainActivity.class);
		startActivity(hopActivity);
		finish();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (settingsAd != null) {
			settingsAd.destroy();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (settingsAd != null) {
			settingsAd.pause();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (settingsAd != null) {
			settingsAd.resume();
		}
	}
	public void _hideNotibar() {
		{
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}
	
	
	public void _showNotibar() {
		{
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}
	
	
	public void _stretchInterface() {
		LinearLayout settingsBase = (LinearLayout) findViewById(R.id.settingsBase);
		
		android.widget.LinearLayout.LayoutParams params = (android.widget. LinearLayout.LayoutParams)settingsBase.getLayoutParams();
		
		params.width = android.widget. LinearLayout.LayoutParams.MATCH_PARENT;
		
		params.height = 100;
		
		settingsBase.setLayoutParams(params);
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