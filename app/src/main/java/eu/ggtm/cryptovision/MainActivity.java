package eu.ggtm.cryptovision;

import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.Context;
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
	private double timeShout1 = 0;
	private double timeShout2 = 0;
	private double timeShout3 = 0;
	private double timeShout4 = 0;
	private double timeShout5 = 0;
	private String textShout1 = "";
	private String textShout2 = "";
	private String textShout3 = "";
	private String textShout4 = "";
	private String textShout5 = "";
	private double randomShout1 = 0;
	private double randomShout2 = 0;
	private double randomShout3 = 0;
	private double randomShout4 = 0;
	private double randomShout5 = 0;
	private double colorShout1 = 0;
	private double colorShout2 = 0;
	private double colorShout3 = 0;
	private double colorShout4 = 0;
	private double colorShout5 = 0;
	private String coloredShout1 = "";
	private String coloredShout2 = "";
	private String coloredShout3 = "";
	private String coloredShout4 = "";
	private String coloredShout5 = "";
	private double startShout1 = 0;
	private double startShout2 = 0;
	private double startShout3 = 0;
	private double startShout4 = 0;
	private double startShout5 = 0;
	private boolean settingRewinding = false;
	
	private LinearLayout baseFrame;
	private LinearLayout headEdge;
	private WebView accessPortal;
	private ImageView chatShelf;
	private HorizontalScrollView bottomSpacer;
	private ProgressBar loadBar;
	private LinearLayout footHeader;
	private TextView portalTitle;
	private TextView topShout1;
	private TextView topShout2;
	private TextView topShout3;
	private TextView topShout4;
	private TextView topShout5;
	private ImageView cVnLogo;
	private TextView versionSpam;
	private TextView settingsPointer;
	private DigitalClock timeDigital;
	private AnalogClock timeAnalog;
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
	private TimerTask eggShout1;
	private TimerTask eggShout2;
	private TimerTask eggShout3;
	private TimerTask eggShout4;
	private TimerTask eggShout5;
	private TimerTask queueShout1;
	private TimerTask queueShout2;
	private TimerTask queueShout3;
	private TimerTask queueShout4;
	private TimerTask queueShout5;
	private SharedPreferences shouts;
	private SharedPreferences colors;
	private Vibrator touchBzz;
	private TimerTask showSettings;
	private TimerTask externalURL;
	private Intent externalWeb = new Intent();
	private TimerTask webTitleCheck;
	private TimerTask eggDelay;
	private ObjectAnimator titleVanish = new ObjectAnimator();
	private TimerTask vanishDelay;
	
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
		portalTitle = findViewById(R.id.portalTitle);
		topShout1 = findViewById(R.id.topShout1);
		topShout2 = findViewById(R.id.topShout2);
		topShout3 = findViewById(R.id.topShout3);
		topShout4 = findViewById(R.id.topShout4);
		topShout5 = findViewById(R.id.topShout5);
		cVnLogo = findViewById(R.id.cVnLogo);
		versionSpam = findViewById(R.id.versionSpam);
		settingsPointer = findViewById(R.id.settingsPointer);
		timeDigital = findViewById(R.id.timeDigital);
		timeAnalog = findViewById(R.id.timeAnalog);
		settingsButton = findViewById(R.id.settingsButton);
		exitButton = findViewById(R.id.exitButton);
		memory = getSharedPreferences("memory", Activity.MODE_PRIVATE);
		shelfToggle = new AlertDialog.Builder(this);
		shouts = getSharedPreferences("shouts", Activity.MODE_PRIVATE);
		colors = getSharedPreferences("colors", Activity.MODE_PRIVATE);
		touchBzz = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		accessPortal.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				if (!_url.contains("//cryptovision.live")) {
					accessPortal.stopLoading();
					externalWeb.setAction(Intent.ACTION_VIEW);
					externalWeb.setData(Uri.parse(accessPortal.getUrl()));
					accessPortal.goBack();
					startActivity(externalWeb);
				}
				else {
					loadBar.setVisibility(View.VISIBLE);
					webTitleCheck.cancel();
					portalTitle.setText("〰 Loading 〰");
					settingsRotate.cancel();
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
					_timer.scheduleAtFixedRate(settingsRotate, (int)(5), (int)(5));
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
				}
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
								webTitleCheck = new TimerTask() {
									@Override
									public void run() {
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												portalTitle.setText(accessPortal.getTitle());
											}
										});
									}
								};
								_timer.scheduleAtFixedRate(webTitleCheck, (int)(100), (int)(100));
							}
						});
					}
				};
				_timer.schedule(unloadDelay, (int)(250));
				settingsRotate.cancel();
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
				_timer.scheduleAtFixedRate(settingsRotate, (int)(25), (int)(25));
				super.onPageFinished(_param1, _param2);
			}
		});
		
		chatShelf.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				touchBzz.vibrate((long)(111));
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
				if (accessPortal.getUrl().contains("node.cryptovision.live")) {
					touchBzz.vibrate((long)(100));
					accessPortal.stopLoading();
					accessPortal.clearCache(true);
					accessPortal.loadUrl("https://cryptovision.live");
					accessPortal.clearHistory();
				}
				else {
					touchBzz.vibrate((long)(100));
					currentURL = accessPortal.getUrl();
					accessPortal.stopLoading();
					accessPortal.clearCache(true);
					accessPortal.loadUrl("http://0.0.0.0");
					accessPortal.stopLoading();
					accessPortal.clearCache(true);
					accessPortal.loadUrl(currentURL);
					accessPortal.clearHistory();
				}
			}
		});
		
		settingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!settingRewinding) {
					settingRewinding = true;
					settingsRotate.cancel();
					touchBzz.vibrate((long)(100));
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
														settingRewinding = false;
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
			}
		});
		
		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				exitButton.setText("❌");
				touchBzz.vibrate((long)(125));
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
		buildInfo = "95";
		memory.edit().putString("Version", versionInfo).commit();
		memory.edit().putString("Build", buildInfo).commit();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		chatShelf.setColorFilter(0xFF350000, PorterDuff.Mode.MULTIPLY);
		timeDigital.setTextColor(Color.parseColor("#FFFFFF"));
		timeDigital.setTextSize((float)11);
		loadBar.setVisibility(View.INVISIBLE);
		settingsPointer.setVisibility(View.GONE);
		topShout1.setVisibility(View.GONE);
		topShout2.setVisibility(View.GONE);
		topShout3.setVisibility(View.GONE);
		topShout4.setVisibility(View.GONE);
		topShout5.setVisibility(View.GONE);
		if (memory.getString("Fullscreen", "").equals("Enabled")) {
			_hideNotibar();
		}
		else {
			_showNotibar();
		}
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
			int clrs [] = {0xFF757575,0xFF000000};
			SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP, clrs);
			SketchUi.setCornerRadii(new float[]{
				d*0,d*0,d*0 ,d*0,d*20,d*20 ,d*20,d*20});
			footHeader.setElevation(d*5);
			android.graphics.drawable.RippleDrawable SketchUi_RD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFB71C1C}), SketchUi, null);
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
		webTitleCheck = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						portalTitle.setText("〰 Booting 〰");
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(webTitleCheck, (int)(100), (int)(100));
		settingRewinding = false;
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
		_timer.scheduleAtFixedRate(settingsRotate, (int)(25), (int)(25));
		if (memory.getString("Time", "").equals("Digital")) {
			timeDigital.setVisibility(View.VISIBLE);
		}
		else {
			timeDigital.setVisibility(View.GONE);
		}
		if (memory.getString("Time", "").equals("Analog")) {
			timeAnalog.setVisibility(View.VISIBLE);
		}
		else {
			timeAnalog.setVisibility(View.GONE);
		}
		if (memory.getString("Egg", "").equals("Activated")) {
			_setShouts();
			_eggShouts();
		}
		if (!memory.contains("oneTimeSettingsArrow")) {
			showSettings = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							settingsPointer.setVisibility(View.VISIBLE);
							showSettings = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											settingsPointer.setVisibility(View.INVISIBLE);
											showSettings = new TimerTask() {
												@Override
												public void run() {
													runOnUiThread(new Runnable() {
														@Override
														public void run() {
															settingsPointer.setVisibility(View.VISIBLE);
															showSettings = new TimerTask() {
																@Override
																public void run() {
																	runOnUiThread(new Runnable() {
																		@Override
																		public void run() {
																			settingsPointer.setVisibility(View.INVISIBLE);
																			showSettings = new TimerTask() {
																				@Override
																				public void run() {
																					runOnUiThread(new Runnable() {
																						@Override
																						public void run() {
																							settingsPointer.setVisibility(View.VISIBLE);
																							showSettings = new TimerTask() {
																								@Override
																								public void run() {
																									runOnUiThread(new Runnable() {
																										@Override
																										public void run() {
																											settingsPointer.setVisibility(View.INVISIBLE);
																											showSettings = new TimerTask() {
																												@Override
																												public void run() {
																													runOnUiThread(new Runnable() {
																														@Override
																														public void run() {
																															settingsPointer.setVisibility(View.VISIBLE);
																															showSettings = new TimerTask() {
																																@Override
																																public void run() {
																																	runOnUiThread(new Runnable() {
																																		@Override
																																		public void run() {
																																			settingsPointer.setVisibility(View.INVISIBLE);
																																			showSettings = new TimerTask() {
																																				@Override
																																				public void run() {
																																					runOnUiThread(new Runnable() {
																																						@Override
																																						public void run() {
																																							settingsPointer.setVisibility(View.VISIBLE);
																																							showSettings = new TimerTask() {
																																								@Override
																																								public void run() {
																																									runOnUiThread(new Runnable() {
																																										@Override
																																										public void run() {
																																											settingsPointer.setVisibility(View.GONE);
																																											memory.edit().putString("oneTimeSettingsArrow", "Shown").commit();
																																										}
																																									});
																																								}
																																							};
																																							_timer.schedule(showSettings, (int)(3500));
																																						}
																																					});
																																				}
																																			};
																																			_timer.schedule(showSettings, (int)(500));
																																		}
																																	});
																																}
																															};
																															_timer.schedule(showSettings, (int)(500));
																														}
																													});
																												}
																											};
																											_timer.schedule(showSettings, (int)(500));
																										}
																									});
																								}
																							};
																							_timer.schedule(showSettings, (int)(500));
																						}
																					});
																				}
																			};
																			_timer.schedule(showSettings, (int)(500));
																		}
																	});
																}
															};
															_timer.schedule(showSettings, (int)(1000));
														}
													});
												}
											};
											_timer.schedule(showSettings, (int)(500));
										}
									});
								}
							};
							_timer.schedule(showSettings, (int)(2000));
						}
					});
				}
			};
			_timer.schedule(showSettings, (int)(1500));
		}
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
			touchBzz.vibrate((long)(125));
			SketchwareUtil.showMessage(getApplicationContext(), "\n👋");
			finishAffinity();
		}
		else {
			touchBzz.vibrate((long)(100));
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
	
	
	public void _eggShouts() {
		vanishDelay = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						titleVanish.setTarget(portalTitle);
						titleVanish.setPropertyName("alpha");
						titleVanish.setFloatValues((float)(1.0d), (float)(0.0d));
						titleVanish.setDuration((int)(8000));
						titleVanish.start();
					}
				});
			}
		};
		_timer.schedule(vanishDelay, (int)(20000));
		eggDelay = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						portalTitle.setVisibility(View.GONE);
						topShout1.setVisibility(View.INVISIBLE);
						topShout2.setVisibility(View.INVISIBLE);
						topShout3.setVisibility(View.INVISIBLE);
						topShout4.setVisibility(View.INVISIBLE);
						topShout5.setVisibility(View.INVISIBLE);
						startShout1 = SketchwareUtil.getRandom((int)(1000), (int)(10000));
						eggShout1 = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										timeShout1 = SketchwareUtil.getRandom((int)(6000), (int)(57500));
										randomShout1 = SketchwareUtil.getRandom((int)(1), (int)(20));
										colorShout1 = SketchwareUtil.getRandom((int)(1), (int)(6));
										textShout1 = shouts.getString(String.valueOf((long)(randomShout1)), "");
										coloredShout1 = colors.getString(String.valueOf((long)(colorShout1)), "");
										queueShout1 = new TimerTask() {
											@Override
											public void run() {
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														topShout1.setText(textShout1);
														topShout1.setVisibility(View.VISIBLE);
														queueShout1 = new TimerTask() {
															@Override
															public void run() {
																runOnUiThread(new Runnable() {
																	@Override
																	public void run() {
																		topShout1.setVisibility(View.INVISIBLE);
																	}
																});
															}
														};
														_timer.schedule(queueShout1, (int)(4567));
													}
												});
											}
										};
										_timer.schedule(queueShout1, (int)(timeShout1));
									}
								});
							}
						};
						_timer.scheduleAtFixedRate(eggShout1, (int)(startShout1), (int)(60000));
						startShout2 = SketchwareUtil.getRandom((int)(1000), (int)(10000));
						eggShout2 = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										timeShout2 = SketchwareUtil.getRandom((int)(6000), (int)(57500));
										randomShout2 = SketchwareUtil.getRandom((int)(1), (int)(20));
										colorShout2 = SketchwareUtil.getRandom((int)(1), (int)(6));
										textShout2 = shouts.getString(String.valueOf((long)(randomShout2)), "");
										coloredShout2 = colors.getString(String.valueOf((long)(colorShout2)), "");
										queueShout2 = new TimerTask() {
											@Override
											public void run() {
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														topShout2.setText(textShout2);
														topShout2.setVisibility(View.VISIBLE);
														queueShout2 = new TimerTask() {
															@Override
															public void run() {
																runOnUiThread(new Runnable() {
																	@Override
																	public void run() {
																		topShout2.setVisibility(View.INVISIBLE);
																	}
																});
															}
														};
														_timer.schedule(queueShout2, (int)(4567));
													}
												});
											}
										};
										_timer.schedule(queueShout2, (int)(timeShout2));
									}
								});
							}
						};
						_timer.scheduleAtFixedRate(eggShout2, (int)(startShout2), (int)(60000));
						startShout3 = SketchwareUtil.getRandom((int)(1000), (int)(10000));
						eggShout3 = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										timeShout3 = SketchwareUtil.getRandom((int)(6000), (int)(57500));
										randomShout3 = SketchwareUtil.getRandom((int)(1), (int)(20));
										colorShout3 = SketchwareUtil.getRandom((int)(1), (int)(6));
										textShout3 = shouts.getString(String.valueOf((long)(randomShout3)), "");
										coloredShout3 = colors.getString(String.valueOf((long)(colorShout3)), "");
										queueShout3 = new TimerTask() {
											@Override
											public void run() {
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														topShout3.setText(textShout3);
														topShout3.setVisibility(View.VISIBLE);
														queueShout3 = new TimerTask() {
															@Override
															public void run() {
																runOnUiThread(new Runnable() {
																	@Override
																	public void run() {
																		topShout3.setVisibility(View.INVISIBLE);
																	}
																});
															}
														};
														_timer.schedule(queueShout3, (int)(4567));
													}
												});
											}
										};
										_timer.schedule(queueShout3, (int)(timeShout3));
									}
								});
							}
						};
						_timer.scheduleAtFixedRate(eggShout3, (int)(startShout3), (int)(60000));
						startShout4 = SketchwareUtil.getRandom((int)(1000), (int)(10000));
						eggShout4 = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										timeShout4 = SketchwareUtil.getRandom((int)(6000), (int)(57500));
										randomShout4 = SketchwareUtil.getRandom((int)(1), (int)(20));
										colorShout4 = SketchwareUtil.getRandom((int)(1), (int)(6));
										textShout4 = shouts.getString(String.valueOf((long)(randomShout4)), "");
										coloredShout4 = colors.getString(String.valueOf((long)(colorShout4)), "");
										queueShout4 = new TimerTask() {
											@Override
											public void run() {
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														topShout4.setText(textShout4);
														topShout4.setVisibility(View.VISIBLE);
														queueShout4 = new TimerTask() {
															@Override
															public void run() {
																runOnUiThread(new Runnable() {
																	@Override
																	public void run() {
																		topShout4.setVisibility(View.INVISIBLE);
																	}
																});
															}
														};
														_timer.schedule(queueShout4, (int)(4567));
													}
												});
											}
										};
										_timer.schedule(queueShout4, (int)(timeShout4));
									}
								});
							}
						};
						_timer.scheduleAtFixedRate(eggShout4, (int)(startShout4), (int)(60000));
						startShout5 = SketchwareUtil.getRandom((int)(1000), (int)(10000));
						eggShout5 = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										timeShout5 = SketchwareUtil.getRandom((int)(6000), (int)(57500));
										randomShout5 = SketchwareUtil.getRandom((int)(1), (int)(20));
										colorShout5 = SketchwareUtil.getRandom((int)(1), (int)(6));
										textShout5 = shouts.getString(String.valueOf((long)(randomShout5)), "");
										coloredShout5 = colors.getString(String.valueOf((long)(colorShout5)), "");
										queueShout5 = new TimerTask() {
											@Override
											public void run() {
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														topShout5.setText(textShout5);
														topShout5.setVisibility(View.VISIBLE);
														queueShout5 = new TimerTask() {
															@Override
															public void run() {
																runOnUiThread(new Runnable() {
																	@Override
																	public void run() {
																		topShout5.setVisibility(View.INVISIBLE);
																	}
																});
															}
														};
														_timer.schedule(queueShout5, (int)(4567));
													}
												});
											}
										};
										_timer.schedule(queueShout5, (int)(timeShout5));
									}
								});
							}
						};
						_timer.scheduleAtFixedRate(eggShout5, (int)(startShout5), (int)(60000));
					}
				});
			}
		};
		_timer.schedule(eggDelay, (int)(30000));
	}
	
	
	public void _setShouts() {
		shouts.edit().putString("1", "Wehooo!").commit();
		shouts.edit().putString("2", "Woo-Hoo!").commit();
		shouts.edit().putString("3", "Party Over Here!").commit();
		shouts.edit().putString("4", "Yeah!").commit();
		shouts.edit().putString("5", "Mooooooo!").commit();
		shouts.edit().putString("6", "Go! Go! Go!").commit();
		shouts.edit().putString("7", "Hell Yeah!").commit();
		shouts.edit().putString("8", "Woop, Woop!").commit();
		shouts.edit().putString("9", "Wooot!").commit();
		shouts.edit().putString("10", "Keep it up!").commit();
		shouts.edit().putString("11", "JA! JA! JA!").commit();
		shouts.edit().putString("12", "Groovy!").commit();
		shouts.edit().putString("13", "*shakes ass*").commit();
		shouts.edit().putString("14", "Ow Yeah!").commit();
		shouts.edit().putString("15", "[whistles]").commit();
		shouts.edit().putString("16", "Booyaa!").commit();
		shouts.edit().putString("17", "Go On!").commit();
		shouts.edit().putString("18", "Damn!").commit();
		shouts.edit().putString("19", "Wiiii!").commit();
		shouts.edit().putString("20", "*faints*").commit();
		colors.edit().putString("1", "#FFEF5350").commit();
		colors.edit().putString("2", "#FFFFEB3B").commit();
		colors.edit().putString("3", "#FFFFC107").commit();
		colors.edit().putString("4", "#FF8BC34A").commit();
		colors.edit().putString("5", "#FF2196F3").commit();
		colors.edit().putString("6", "#FFF06292").commit();
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