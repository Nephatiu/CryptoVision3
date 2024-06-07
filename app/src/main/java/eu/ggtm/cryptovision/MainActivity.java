package eu.ggtm.cryptovision;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.DialogInterface;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private String versionInfo = "";
	private String buildInfo = "";
	private boolean exitRepeat = false;
	private double settingsRotation = 0;
	private String currentURL = "";
	
	private LinearLayout baseFrame;
	private LinearLayout headEdge;
	private WebView accessPortal;
	private ImageView chatShelf;
	private HorizontalScrollView bottomSpacer;
	private ProgressBar loadBar;
	private LinearLayout footHeader;
	private ImageView cVnLogo;
	private TextView versionSpam;
	private TextView settingsButton;
	private TextView exitButton;
	
	private SharedPreferences memory;
	private TimerTask exitDouble;
	private TimerTask settingsRotate;
	private TimerTask loadValue;
	private TimerTask unloadDelay;
	private Intent hopActivity = new Intent();
	private TimerTask tapFlash;
	private AlertDialog.Builder shelfToggle;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		baseFrame = findViewById(R.id.baseFrame);
		headEdge = findViewById(R.id.headEdge);
		accessPortal = findViewById(R.id.accessPortal);
		accessPortal.getSettings().setJavaScriptEnabled(true);
		accessPortal.getSettings().setSupportZoom(true);
		chatShelf = findViewById(R.id.chatShelf);
		bottomSpacer = findViewById(R.id.bottomSpacer);
		loadBar = findViewById(R.id.loadBar);
		footHeader = findViewById(R.id.footHeader);
		cVnLogo = findViewById(R.id.cVnLogo);
		versionSpam = findViewById(R.id.versionSpam);
		settingsButton = findViewById(R.id.settingsButton);
		exitButton = findViewById(R.id.exitButton);
		memory = getSharedPreferences("memory", Activity.MODE_PRIVATE);
		shelfToggle = new AlertDialog.Builder(this);
		
		accessPortal.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				loadBar.setVisibility(View.VISIBLE);
				loadValue = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loadBar.setProgress((int)accessPortal.getProgress());
							}
						});
					}
				};
				_timer.scheduleAtFixedRate(loadValue, (int)(0), (int)(10));
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				loadValue.cancel();
				unloadDelay = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loadBar.setVisibility(View.INVISIBLE);
							}
						});
					}
				};
				_timer.schedule(unloadDelay, (int)(250));
				super.onPageFinished(_param1, _param2);
			}
		});
		
		chatShelf.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				shelfToggle.setTitle("Do you want to disable the shelf?");
				shelfToggle.setMessage("You have been pushing on the shelf for an extended time, do you want to disable it and gain some screenspace? (You can turn it back on in settings.)");
				shelfToggle.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						memory.edit().putString("Shelf", "Off").commit();
						chatShelf.setVisibility(View.GONE);
					}
				});
				shelfToggle.setNegativeButton("No!", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				shelfToggle.create().show();
				return true;
			}
		});
		
		footHeader.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				currentURL = accessPortal.getUrl();
				accessPortal.stopLoading();
				accessPortal.clearCache(true);
				accessPortal.loadUrl("https://www.ggtm.eu");
				accessPortal.stopLoading();
				accessPortal.clearCache(true);
				accessPortal.loadUrl(currentURL);
				accessPortal.clearHistory();
			}
		});
		
		settingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				settingsRotate.cancel();
				settingsButton.setTextColor(0xFF350000);
				tapFlash = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								settingsButton.setTextColor(0xFFFDD835);
								settingsRotate = new TimerTask() {
									@Override
									public void run() {
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												if (settingsRotation > 0) {
													settingsRotation--;
													settingsButton.setRotation((float)(settingsRotation));
												}
												else {
													settingsRotate.cancel();
													settingsRotation = 0;
													settingsButton.setRotation((float)(settingsRotation));
													hopActivity.setAction(Intent.ACTION_VIEW);
													hopActivity.setClass(getApplicationContext(), SettingsActivity.class);
													startActivity(hopActivity);
													finish();
												}
											}
										});
									}
								};
								_timer.scheduleAtFixedRate(settingsRotate, (int)(3), (int)(3));
							}
						});
					}
				};
				_timer.schedule(tapFlash, (int)(50));
			}
		});
		
		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				exitButton.setText("❌");
				tapFlash = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								exitButton.setText("❎");
								SketchwareUtil.showMessage(getApplicationContext(), "👋");
								finishAffinity();
							}
						});
					}
				};
				_timer.schedule(tapFlash, (int)(50));
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("cVn");
		_enableJavascript();
		_actionFullscreenAddon();
		versionInfo = "3.00";
		buildInfo = "35";
		memory.edit().putString("Version", versionInfo).commit();
		memory.edit().putString("Build", buildInfo).commit();
		chatShelf.setColorFilter(0xFF350000, PorterDuff.Mode.MULTIPLY);
		loadBar.setVisibility(View.INVISIBLE);
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
			int clrs [] = {0xFF000000,0xFF757575};
			SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, clrs);
			SketchUi.setCornerRadii(new float[]{
				d*0,d*0,d*0 ,d*0,d*20,d*20 ,d*20,d*20});
			footHeader.setElevation(d*5);
			android.graphics.drawable.RippleDrawable SketchUi_RD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFF550000}), SketchUi, null);
			footHeader.setBackground(SketchUi_RD);
			footHeader.setClickable(true);
		}
		versionSpam.setText("v".concat(versionInfo.concat(" [b".concat(buildInfo.concat("] ~ PhoeniX")))));
		if (memory.getString("Shelf", "").equals("Off")) {
			chatShelf.setVisibility(View.GONE);
		}
		else {
			chatShelf.setVisibility(View.VISIBLE);
		}
		if (memory.getString("Borders", "").equals("Off")) {
			{
				baseFrame.setPadding(0,2,0,0);
			}
		}
		else {
			{
				baseFrame.setPadding(8,2,8,0);
			}
		}
		if (memory.getString("Exitbutton", "").equals("On")) {
			exitButton.setVisibility(View.VISIBLE);
		}
		else {
			exitButton.setVisibility(View.GONE);
		}
		exitRepeat = false;
		settingsRotation = 0;
		accessPortal.loadUrl("https://cryptovision.live");
		settingsRotate = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						settingsButton.setRotation((float)(settingsRotation));
						if (settingsRotation < 359) {
							settingsRotation++;
						}
						else {
							settingsRotation = 0;
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(settingsRotate, (int)(10), (int)(10));
	}
	
	@Override
	public void onBackPressed() {
		if (accessPortal.canGoBack()) {
			accessPortal.goBack();
		}
		else {
			_doubleExit();
		}
	}
	public void _enableJavascript() {
		accessPortal.getSettings().setJavaScriptEnabled(true);
		accessPortal.getSettings().setDomStorageEnabled(true);
	}
	
	
	public void _doubleExit() {
		if (exitRepeat) {
			SketchwareUtil.showMessage(getApplicationContext(), "\n👋");
			finishAffinity();
		}
		else {
			exitRepeat = true;
			SketchwareUtil.showMessage(getApplicationContext(), "Press \"Back\" again quickly to exit.");
			exitDouble = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							exitRepeat = false;
						}
					});
				}
			};
			_timer.schedule(exitDouble, (int)(2000));
		}
	}
	
	
	public void _silentFullscreenAddon() {
	}
	
	public class CustomWebClient extends WebChromeClient {
		private View mCustomView;
		private WebChromeClient.CustomViewCallback mCustomViewCallback;
		protected FrameLayout frame;
		
		// Initially mOriginalOrientation is set to Landscape
		private int mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		private int mOriginalSystemUiVisibility;
		
		// Constructor for CustomWebClient
		public CustomWebClient() {}
		
		public Bitmap getDefaultVideoPoster() {
			if (MainActivity.this == null) {
				return null; }
			return BitmapFactory.decodeResource(MainActivity.this.getApplicationContext().getResources(), 2130837573); }
		
		public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback viewCallback) {
			if (this.mCustomView != null) {
				onHideCustomView();
				return; }
			this.mCustomView = paramView;
			this.mOriginalSystemUiVisibility = MainActivity.this.getWindow().getDecorView().getSystemUiVisibility();
			// When CustomView is shown screen orientation changes to mOriginalOrientation (Landscape).
			MainActivity.this.setRequestedOrientation(this.mOriginalOrientation);
			// After that mOriginalOrientation is set to portrait.
			this.mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
			this.mCustomViewCallback = viewCallback; ((FrameLayout)MainActivity.this.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1)); MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(3846);
		}
		
		public void onHideCustomView() {
			((FrameLayout)MainActivity.this.getWindow().getDecorView()).removeView(this.mCustomView);
			this.mCustomView = null;
			MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
			// When CustomView is hidden, screen orientation is set to mOriginalOrientation (portrait).
			MainActivity.this.setRequestedOrientation(this.mOriginalOrientation);
			// After that mOriginalOrientation is set to landscape.
			this.mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; this.mCustomViewCallback.onCustomViewHidden();
			this.mCustomViewCallback = null;
		}
	}
	
	{
	}
	
	
	public void _actionFullscreenAddon() {
		accessPortal.setWebChromeClient(new CustomWebClient());
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