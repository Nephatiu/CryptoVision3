package eu.ggtm.cryptovision;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
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
	
	private LinearLayout settingsBase;
	private HorizontalScrollView topSpacer;
	private TextView settingsTitle;
	private TextView settingsInfo;
	private Switch settingShelf;
	private Switch settingBorders;
	private Switch settingExitbutton;
	private ScrollView upPusher;
	private LinearLayout footHeader;
	private TextView backButton;
	private TextView versionSpam;
	private ImageView cVnLogo;
	
	private SharedPreferences memory;
	private Intent hopActivity = new Intent();
	private TimerTask tapFlash;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.settings);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		settingsBase = findViewById(R.id.settingsBase);
		topSpacer = findViewById(R.id.topSpacer);
		settingsTitle = findViewById(R.id.settingsTitle);
		settingsInfo = findViewById(R.id.settingsInfo);
		settingShelf = findViewById(R.id.settingShelf);
		settingBorders = findViewById(R.id.settingBorders);
		settingExitbutton = findViewById(R.id.settingExitbutton);
		upPusher = findViewById(R.id.upPusher);
		footHeader = findViewById(R.id.footHeader);
		backButton = findViewById(R.id.backButton);
		versionSpam = findViewById(R.id.versionSpam);
		cVnLogo = findViewById(R.id.cVnLogo);
		memory = getSharedPreferences("memory", Activity.MODE_PRIVATE);
		
		settingShelf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					memory.edit().putString("Shelf", "On").commit();
				}
				else {
					memory.edit().putString("Shelf", "Off").commit();
				}
			}
		});
		
		settingBorders.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					memory.edit().putString("Borders", "On").commit();
					{
						settingsBase.setPadding(8,0,8,0);
					}
				}
				else {
					memory.edit().putString("Borders", "Off").commit();
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
				if (_isChecked) {
					memory.edit().putString("Exitbutton", "On").commit();
				}
				else {
					memory.edit().putString("Exitbutton", "Off").commit();
				}
			}
		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				backButton.setTextColor(0xFF350000);
				tapFlash = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								backButton.setTextColor(0xFFFDD835);
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
		
		cVnLogo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!memory.getString("Egg", "").equals("Activated")) {
					tapCounter++;
					if (tapCounter == 7) {
						SketchwareUtil.showMessage(getApplicationContext(), "Can you stop that?");
					}
					if (tapCounter == 10) {
						SketchwareUtil.showMessage(getApplicationContext(), "Seriously, something might break!");
					}
					if (tapCounter == 13) {
						SketchwareUtil.showMessage(getApplicationContext(), "Don't tap 2 more times...");
					}
					if (tapCounter == 14) {
						SketchwareUtil.showMessage(getApplicationContext(), "Don't tap 1 more time!");
					}
					if (tapCounter == 15) {
						memory.edit().putString("Egg", "Activated").commit();
						SketchwareUtil.showMessage(getApplicationContext(), "Now you've done it!");
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Sorry, that trick only works once...");
				}
			}
		});
	}
	
	private void initializeLogic() {
		settingShelf.setVisibility(View.VISIBLE);
		versionInfo = memory.getString("Version", "");
		buildInfo = memory.getString("Build", "");
		{
			android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
			int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
			int clrs [] = {0xFF000000,0xFF757575};
			SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, clrs);
			SketchUi.setCornerRadii(new float[]{
				d*35,d*35,d*35 ,d*35,d*0,d*0 ,d*0,d*0});
			footHeader.setElevation(d*5);
			android.graphics.drawable.RippleDrawable SketchUi_RD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFB71C1C}), SketchUi, null);
			footHeader.setBackground(SketchUi_RD);
			footHeader.setClickable(true);
		}
		versionSpam.setText("v".concat(versionInfo.concat(" [b".concat(buildInfo.concat("] ~ PhoeniX")))));
		tapCounter = 0;
		if (memory.getString("Shelf", "").equals("Off")) {
			settingShelf.setChecked(false);
		}
		else {
			settingShelf.setChecked(true);
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
		if (memory.getString("Exitbutton", "").equals("On")) {
			settingExitbutton.setChecked(true);
		}
		else {
			settingExitbutton.setChecked(false);
		}
	}
	
	@Override
	public void onBackPressed() {
		hopActivity.setAction(Intent.ACTION_VIEW);
		hopActivity.setClass(getApplicationContext(), MainActivity.class);
		startActivity(hopActivity);
		finish();
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