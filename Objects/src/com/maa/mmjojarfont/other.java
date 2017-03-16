package com.maa.mmjojarfont;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class other extends Activity implements B4AActivity{
	public static other mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.maa.mmjojarfont", "com.maa.mmjojarfont.other");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (other).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.maa.mmjojarfont", "com.maa.mmjojarfont.other");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.maa.mmjojarfont.other", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (other) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (other) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return other.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (other) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (other) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.Timer _ad1 = null;
public static anywheresoftware.b4a.objects.Timer _ad2 = null;
public static anywheresoftware.b4a.objects.Timer _ti = null;
public static anywheresoftware.b4a.objects.Timer _tr = null;
public static anywheresoftware.b4a.objects.Timer _ist = null;
public static anywheresoftware.b4a.objects.Timer _rst = null;
public anywheresoftware.b4a.phone.Phone _ph = null;
public static String _os = "";
public anywheresoftware.b4a.objects.ButtonWrapper _b1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b3 = null;
public anywheresoftware.b4a.admobwrapper.AdViewWrapper _banner = null;
public anywheresoftware.b4a.admobwrapper.AdViewWrapper.InterstitialAdWrapper _interstitial = null;
public MLfiles.Fileslib.MLfiles _ml = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb = null;
public anywheresoftware.b4a.keywords.constants.TypefaceWrapper _mm = null;
public static String _rooot = "";
public com.maa.mmjojarfont.main _main = null;
public com.maa.mmjojarfont.install _install = null;
public com.maa.mmjojarfont.samsung _samsung = null;
public com.maa.mmjojarfont.oppo _oppo = null;
public com.maa.mmjojarfont.vivo _vivo = null;
public com.maa.mmjojarfont.xiaomi _xiaomi = null;
public com.maa.mmjojarfont.huawei _huawei = null;
public com.maa.mmjojarfont.tutorial _tutorial = null;
public com.maa.mmjojarfont.starter _starter = null;
public com.maa.mmjojarfont.about _about = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
int _height = 0;
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 28;BA.debugLine="Select ph.SdkVersion";
switch (BA.switchObjectToInt(mostCurrent._ph.getSdkVersion(),(int) (2),(int) (3),(int) (4),(int) (5),(int) (6),(int) (7),(int) (8),(int) (9),(int) (10),(int) (11),(int) (12),(int) (13),(int) (14),(int) (15),(int) (16),(int) (17),(int) (18),(int) (19),(int) (21),(int) (22),(int) (23),(int) (24),(int) (25))) {
case 0: {
 //BA.debugLineNum = 29;BA.debugLine="Case 2 : OS = \"1.1\"";
mostCurrent._os = "1.1";
 break; }
case 1: {
 //BA.debugLineNum = 30;BA.debugLine="Case 3 : OS = \"1.5\"";
mostCurrent._os = "1.5";
 break; }
case 2: {
 //BA.debugLineNum = 31;BA.debugLine="Case 4 : OS = \"1.6\"";
mostCurrent._os = "1.6";
 break; }
case 3: {
 //BA.debugLineNum = 32;BA.debugLine="Case 5 : OS = \"2.0\"";
mostCurrent._os = "2.0";
 break; }
case 4: {
 //BA.debugLineNum = 33;BA.debugLine="Case 6 : OS = \"2.0.1\"";
mostCurrent._os = "2.0.1";
 break; }
case 5: {
 //BA.debugLineNum = 34;BA.debugLine="Case 7 : OS = \"2.1\"";
mostCurrent._os = "2.1";
 break; }
case 6: {
 //BA.debugLineNum = 35;BA.debugLine="Case 8 : OS = \"2.2\"";
mostCurrent._os = "2.2";
 break; }
case 7: {
 //BA.debugLineNum = 36;BA.debugLine="Case 9 : OS = \"2.3 - 2.3.2\"";
mostCurrent._os = "2.3 - 2.3.2";
 break; }
case 8: {
 //BA.debugLineNum = 37;BA.debugLine="Case 10 : OS = \"	2.3.3 - 2.3.7\" ' 2.3.3 or 2.3.4";
mostCurrent._os = "	2.3.3 - 2.3.7";
 break; }
case 9: {
 //BA.debugLineNum = 38;BA.debugLine="Case 11 : OS = \"3.0\"";
mostCurrent._os = "3.0";
 break; }
case 10: {
 //BA.debugLineNum = 39;BA.debugLine="Case 12 : OS = \"3.1\"";
mostCurrent._os = "3.1";
 break; }
case 11: {
 //BA.debugLineNum = 40;BA.debugLine="Case 13 : OS = \"3.2\"";
mostCurrent._os = "3.2";
 break; }
case 12: {
 //BA.debugLineNum = 41;BA.debugLine="Case 14 : OS = \"	4.0.1 - 4.0.2\"";
mostCurrent._os = "	4.0.1 - 4.0.2";
 break; }
case 13: {
 //BA.debugLineNum = 42;BA.debugLine="Case 15 : OS = \"4.0.3 - 4.0.4\"";
mostCurrent._os = "4.0.3 - 4.0.4";
 break; }
case 14: {
 //BA.debugLineNum = 43;BA.debugLine="Case 16 : OS = \"	4.1.x\"";
mostCurrent._os = "	4.1.x";
 break; }
case 15: {
 //BA.debugLineNum = 44;BA.debugLine="Case 17 : OS = \"	4.2.x\"";
mostCurrent._os = "	4.2.x";
 break; }
case 16: {
 //BA.debugLineNum = 45;BA.debugLine="Case 18 : OS = 	\"4.3.x\"";
mostCurrent._os = "4.3.x";
 break; }
case 17: {
 //BA.debugLineNum = 46;BA.debugLine="Case 19 : OS = \"	4.4 - 4.4.4\"";
mostCurrent._os = "	4.4 - 4.4.4";
 break; }
case 18: {
 //BA.debugLineNum = 47;BA.debugLine="Case 21: OS = \"5.0\"";
mostCurrent._os = "5.0";
 break; }
case 19: {
 //BA.debugLineNum = 48;BA.debugLine="Case 22: OS = \"5.1\"";
mostCurrent._os = "5.1";
 break; }
case 20: {
 //BA.debugLineNum = 49;BA.debugLine="Case 23: OS = \"6.0\"";
mostCurrent._os = "6.0";
 break; }
case 21: {
 //BA.debugLineNum = 50;BA.debugLine="Case 24 : OS = \"	7.0\"";
mostCurrent._os = "	7.0";
 break; }
case 22: {
 //BA.debugLineNum = 51;BA.debugLine="Case 25 : OS = \"	7.1\"";
mostCurrent._os = "	7.1";
 break; }
default: {
 //BA.debugLineNum = 52;BA.debugLine="Case Else : OS = \"?\"";
mostCurrent._os = "?";
 break; }
}
;
 //BA.debugLineNum = 55;BA.debugLine="ml.GetRoot";
mostCurrent._ml.GetRoot();
 //BA.debugLineNum = 56;BA.debugLine="If ml.HaveRoot Then";
if (mostCurrent._ml.HaveRoot) { 
 //BA.debugLineNum = 57;BA.debugLine="rooot = \"Rooted\"";
mostCurrent._rooot = "Rooted";
 }else {
 //BA.debugLineNum = 59;BA.debugLine="rooot = \"Unroot\"";
mostCurrent._rooot = "Unroot";
 };
 //BA.debugLineNum = 62;BA.debugLine="lb.Initialize(\"lb\")";
mostCurrent._lb.Initialize(mostCurrent.activityBA,"lb");
 //BA.debugLineNum = 63;BA.debugLine="lb.Gravity = Gravity.CENTER";
mostCurrent._lb.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 64;BA.debugLine="lb.Text = \"Brand Name : \" & ph.Manufacturer & CRL";
mostCurrent._lb.setText(BA.ObjectToCharSequence("Brand Name : "+mostCurrent._ph.getManufacturer()+anywheresoftware.b4a.keywords.Common.CRLF+"Device Name : "+mostCurrent._ph.getModel()+anywheresoftware.b4a.keywords.Common.CRLF+"Android Version : "+mostCurrent._os+anywheresoftware.b4a.keywords.Common.CRLF+"Root Status : "+mostCurrent._rooot));
 //BA.debugLineNum = 65;BA.debugLine="Activity.AddView(lb,0%x,1%y,100%x,30%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._lb.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 66;BA.debugLine="lb.Typeface = mm";
mostCurrent._lb.setTypeface((android.graphics.Typeface)(mostCurrent._mm.getObject()));
 //BA.debugLineNum = 68;BA.debugLine="Activity.Title = \"All Android[#Root]\"";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence("All Android[#Root]"));
 //BA.debugLineNum = 69;BA.debugLine="ph.SetScreenOrientation(1)";
mostCurrent._ph.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 71;BA.debugLine="b1.Initialize(\"b1\")";
mostCurrent._b1.Initialize(mostCurrent.activityBA,"b1");
 //BA.debugLineNum = 72;BA.debugLine="b1.Text = \"Install\"";
mostCurrent._b1.setText(BA.ObjectToCharSequence("Install"));
 //BA.debugLineNum = 73;BA.debugLine="b1.Gravity = Gravity.CENTER";
mostCurrent._b1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 74;BA.debugLine="Activity.AddView(b1,20%x,(lb.Height+lb.Top)+1%y,6";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),(int) ((mostCurrent._lb.getHeight()+mostCurrent._lb.getTop())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 76;BA.debugLine="b2.Initialize(\"b2\")";
mostCurrent._b2.Initialize(mostCurrent.activityBA,"b2");
 //BA.debugLineNum = 77;BA.debugLine="b2.Text = \"Restore\"";
mostCurrent._b2.setText(BA.ObjectToCharSequence("Restore"));
 //BA.debugLineNum = 78;BA.debugLine="b2.Gravity = Gravity.CENTER";
mostCurrent._b2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 79;BA.debugLine="Activity.AddView(b2,20%x,(b1.Top+b1.Height)+1%y,6";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),(int) ((mostCurrent._b1.getTop()+mostCurrent._b1.getHeight())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 81;BA.debugLine="b3.Initialize(\"b3\")";
mostCurrent._b3.Initialize(mostCurrent.activityBA,"b3");
 //BA.debugLineNum = 82;BA.debugLine="b3.Text = \"Tutorial\"";
mostCurrent._b3.setText(BA.ObjectToCharSequence("Tutorial"));
 //BA.debugLineNum = 83;BA.debugLine="b3.Gravity = Gravity.CENTER";
mostCurrent._b3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 84;BA.debugLine="Activity.AddView(b3,20%x,(b2.Top+b2.Height)+1%y,6";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),(int) ((mostCurrent._b2.getTop()+mostCurrent._b2.getHeight())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 86;BA.debugLine="Banner.Initialize2(\"Banner\",\"ca-app-pub-417334857";
mostCurrent._banner.Initialize2(mostCurrent.activityBA,"Banner","ca-app-pub-4173348573252986/1936810554",mostCurrent._banner.SIZE_SMART_BANNER);
 //BA.debugLineNum = 87;BA.debugLine="Dim height As Int";
_height = 0;
 //BA.debugLineNum = 88;BA.debugLine="If GetDeviceLayoutValues.ApproximateScreenSize <";
if (anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(mostCurrent.activityBA).getApproximateScreenSize()<6) { 
 //BA.debugLineNum = 90;BA.debugLine="If 100%x > 100%y Then height = 32dip Else height";
if (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)>anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)) { 
_height = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32));}
else {
_height = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50));};
 }else {
 //BA.debugLineNum = 93;BA.debugLine="height = 90dip";
_height = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90));
 };
 //BA.debugLineNum = 95;BA.debugLine="Activity.AddView(Banner, 0dip, 100%y - height, 10";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._banner.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-_height),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),_height);
 //BA.debugLineNum = 96;BA.debugLine="Banner.LoadAd";
mostCurrent._banner.LoadAd();
 //BA.debugLineNum = 97;BA.debugLine="Log(Banner)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._banner));
 //BA.debugLineNum = 99;BA.debugLine="Interstitial.Initialize(\"Interstitial\",\"ca-app-pu";
mostCurrent._interstitial.Initialize(mostCurrent.activityBA,"Interstitial","ca-app-pub-4173348573252986/3413543752");
 //BA.debugLineNum = 100;BA.debugLine="Interstitial.LoadAd";
mostCurrent._interstitial.LoadAd();
 //BA.debugLineNum = 102;BA.debugLine="ad1.Initialize(\"ad1\",100)";
_ad1.Initialize(processBA,"ad1",(long) (100));
 //BA.debugLineNum = 103;BA.debugLine="ad1.Enabled = False";
_ad1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 104;BA.debugLine="ad2.Initialize(\"ad2\",60000)";
_ad2.Initialize(processBA,"ad2",(long) (60000));
 //BA.debugLineNum = 105;BA.debugLine="ad2.Enabled = True";
_ad2.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 107;BA.debugLine="ti.Initialize(\"ti\",100)";
_ti.Initialize(processBA,"ti",(long) (100));
 //BA.debugLineNum = 108;BA.debugLine="ti.Enabled = False";
_ti.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 109;BA.debugLine="tr.Initialize(\"tr\",100)";
_tr.Initialize(processBA,"tr",(long) (100));
 //BA.debugLineNum = 110;BA.debugLine="tr.Enabled = False";
_tr.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 112;BA.debugLine="ist.Initialize(\"ist\",5000)";
_ist.Initialize(processBA,"ist",(long) (5000));
 //BA.debugLineNum = 113;BA.debugLine="ist.Enabled = False";
_ist.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 114;BA.debugLine="rst.Initialize(\"rst\",5000)";
_rst.Initialize(processBA,"rst",(long) (5000));
 //BA.debugLineNum = 115;BA.debugLine="rst.Enabled = False";
_rst.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _in = null;
anywheresoftware.b4a.phone.PackageManagerWrapper _pm = null;
 //BA.debugLineNum = 543;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 544;BA.debugLine="Dim in As Intent";
_in = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 545;BA.debugLine="Dim pm As PackageManager";
_pm = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 546;BA.debugLine="in = pm.GetApplicationIntent(\"com.xinmei365.fonu\"";
_in = _pm.GetApplicationIntent("com.xinmei365.fonu");
 //BA.debugLineNum = 547;BA.debugLine="If in.IsInitialized Then";
if (_in.IsInitialized()) { 
 //BA.debugLineNum = 548;BA.debugLine="Dim ml As MLfiles";
mostCurrent._ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 549;BA.debugLine="ml.rmrf(File.DirRootExternal & \"/.MyanmarHeartFo";
mostCurrent._ml.rmrf(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/.MyanmarHeartFont");
 };
 //BA.debugLineNum = 551;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _in = null;
anywheresoftware.b4a.phone.PackageManagerWrapper _pm = null;
 //BA.debugLineNum = 507;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 508;BA.debugLine="Dim in As Intent";
_in = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 509;BA.debugLine="Dim pm As PackageManager";
_pm = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 510;BA.debugLine="in = pm.GetApplicationIntent(\"com.xinmei365.fonu\"";
_in = _pm.GetApplicationIntent("com.xinmei365.fonu");
 //BA.debugLineNum = 511;BA.debugLine="If in.IsInitialized Then";
if (_in.IsInitialized()) { 
 //BA.debugLineNum = 512;BA.debugLine="Dim ml As MLfiles";
mostCurrent._ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 513;BA.debugLine="ml.rmrf(File.DirRootExternal & \"/.MyanmarHeartFo";
mostCurrent._ml.rmrf(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/.MyanmarHeartFont");
 };
 //BA.debugLineNum = 515;BA.debugLine="End Sub";
return "";
}
public static String  _ad1_tick() throws Exception{
 //BA.debugLineNum = 517;BA.debugLine="Sub ad1_Tick";
 //BA.debugLineNum = 518;BA.debugLine="If Interstitial.Ready Then Interstitial.Show";
if (mostCurrent._interstitial.getReady()) { 
mostCurrent._interstitial.Show();};
 //BA.debugLineNum = 519;BA.debugLine="ad1.Enabled = False";
_ad1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 520;BA.debugLine="End Sub";
return "";
}
public static String  _ad2_tick() throws Exception{
 //BA.debugLineNum = 539;BA.debugLine="Sub ad2_Tick";
 //BA.debugLineNum = 540;BA.debugLine="If Interstitial.Ready Then Interstitial.Show";
if (mostCurrent._interstitial.getReady()) { 
mostCurrent._interstitial.Show();};
 //BA.debugLineNum = 541;BA.debugLine="End Sub";
return "";
}
public static String  _b1_click() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub b1_Click";
 //BA.debugLineNum = 119;BA.debugLine="ad1.Enabled = True";
_ad1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 120;BA.debugLine="ml.GetRoot";
mostCurrent._ml.GetRoot();
 //BA.debugLineNum = 121;BA.debugLine="If ml.HaveRoot Then";
if (mostCurrent._ml.HaveRoot) { 
 //BA.debugLineNum = 122;BA.debugLine="File.Copy(File.DirAssets,\"Jojar.ttf\",File.DirRoo";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Jojar.ttf",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"MyanmarHeart.ttf");
 //BA.debugLineNum = 123;BA.debugLine="ProgressDialogShow(\"Installing...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Installing..."));
 //BA.debugLineNum = 124;BA.debugLine="ti.Enabled = True";
_ti.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 126;BA.debugLine="Msgbox(\"Your device not have Root Access!\",\"Atte";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Your device not have Root Access!"),BA.ObjectToCharSequence("Attention!"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 128;BA.debugLine="End Sub";
return "";
}
public static String  _b2_click() throws Exception{
 //BA.debugLineNum = 360;BA.debugLine="Sub b2_Click";
 //BA.debugLineNum = 361;BA.debugLine="ml.GetRoot";
mostCurrent._ml.GetRoot();
 //BA.debugLineNum = 362;BA.debugLine="If ml.HaveRoot Then";
if (mostCurrent._ml.HaveRoot) { 
 //BA.debugLineNum = 363;BA.debugLine="ProgressDialogShow(\"Please Wait...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Please Wait..."));
 //BA.debugLineNum = 364;BA.debugLine="tr.Enabled = True";
_tr.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 366;BA.debugLine="Msgbox(\"Your device not have Root Access!\",\"Atte";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Your device not have Root Access!"),BA.ObjectToCharSequence("Attention!"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 368;BA.debugLine="End Sub";
return "";
}
public static String  _b3_click() throws Exception{
 //BA.debugLineNum = 503;BA.debugLine="Sub b3_Click";
 //BA.debugLineNum = 504;BA.debugLine="StartActivity(Tutorial)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._tutorial.getObject()));
 //BA.debugLineNum = 505;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim ph As Phone";
mostCurrent._ph = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 14;BA.debugLine="Dim OS As String";
mostCurrent._os = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim b1,b2,b3 As Button";
mostCurrent._b1 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b2 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Dim Banner As AdView";
mostCurrent._banner = new anywheresoftware.b4a.admobwrapper.AdViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim Interstitial As InterstitialAd";
mostCurrent._interstitial = new anywheresoftware.b4a.admobwrapper.AdViewWrapper.InterstitialAdWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim ml As MLfiles";
mostCurrent._ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 20;BA.debugLine="Dim lb As Label";
mostCurrent._lb = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim mm As Typeface : mm = mm.LoadFromAssets(\"Joja";
mostCurrent._mm = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim mm As Typeface : mm = mm.LoadFromAssets(\"Joja";
mostCurrent._mm.setObject((android.graphics.Typeface)(mostCurrent._mm.LoadFromAssets("Jojar.ttf")));
 //BA.debugLineNum = 22;BA.debugLine="Dim ml As MLfiles";
mostCurrent._ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 23;BA.debugLine="Dim rooot As String";
mostCurrent._rooot = "";
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _interstitial_adclosed() throws Exception{
 //BA.debugLineNum = 522;BA.debugLine="Sub Interstitial_AdClosed";
 //BA.debugLineNum = 523;BA.debugLine="Interstitial.LoadAd";
mostCurrent._interstitial.LoadAd();
 //BA.debugLineNum = 524;BA.debugLine="End Sub";
return "";
}
public static String  _interstitial_adopened() throws Exception{
 //BA.debugLineNum = 535;BA.debugLine="Sub Interstitial_adopened";
 //BA.debugLineNum = 536;BA.debugLine="Log(\"Opened\")";
anywheresoftware.b4a.keywords.Common.Log("Opened");
 //BA.debugLineNum = 537;BA.debugLine="End Sub";
return "";
}
public static String  _interstitial_failedtoreceivead(String _errorcode) throws Exception{
 //BA.debugLineNum = 530;BA.debugLine="Sub Interstitial_FailedToReceiveAd (ErrorCode As S";
 //BA.debugLineNum = 531;BA.debugLine="Log(\"not Received - \" &\"Error Code: \"&ErrorCode)";
anywheresoftware.b4a.keywords.Common.Log("not Received - "+"Error Code: "+_errorcode);
 //BA.debugLineNum = 532;BA.debugLine="Interstitial.LoadAd";
mostCurrent._interstitial.LoadAd();
 //BA.debugLineNum = 533;BA.debugLine="End Sub";
return "";
}
public static String  _interstitial_receivead() throws Exception{
 //BA.debugLineNum = 526;BA.debugLine="Sub Interstitial_ReceiveAd";
 //BA.debugLineNum = 527;BA.debugLine="Log(\"Received\")";
anywheresoftware.b4a.keywords.Common.Log("Received");
 //BA.debugLineNum = 528;BA.debugLine="End Sub";
return "";
}
public static String  _ist_tick() throws Exception{
 //BA.debugLineNum = 350;BA.debugLine="Sub ist_Tick";
 //BA.debugLineNum = 351;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 352;BA.debugLine="Msgbox(\"Congratulations! Myanmar Installed in you";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Congratulations! Myanmar Installed in your device"+anywheresoftware.b4a.keywords.Common.CRLF+"Now, your device will be reboot!"),BA.ObjectToCharSequence("Completed"),mostCurrent.activityBA);
 //BA.debugLineNum = 353;BA.debugLine="ml.GetRoot";
mostCurrent._ml.GetRoot();
 //BA.debugLineNum = 354;BA.debugLine="If ml.HaveRoot Then";
if (mostCurrent._ml.HaveRoot) { 
 //BA.debugLineNum = 355;BA.debugLine="ml.RootCmd(\"reboot\",\"\",Null,Null,False)";
mostCurrent._ml.RootCmd("reboot","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 357;BA.debugLine="ist.Enabled = False";
_ist.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 358;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim ad1,ad2 As Timer";
_ad1 = new anywheresoftware.b4a.objects.Timer();
_ad2 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 8;BA.debugLine="Dim ti,tr As Timer";
_ti = new anywheresoftware.b4a.objects.Timer();
_tr = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 9;BA.debugLine="Dim ist,rst As Timer";
_ist = new anywheresoftware.b4a.objects.Timer();
_rst = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _rst_tick() throws Exception{
 //BA.debugLineNum = 491;BA.debugLine="Sub rst_Tick";
 //BA.debugLineNum = 492;BA.debugLine="ml.GetRoot";
mostCurrent._ml.GetRoot();
 //BA.debugLineNum = 493;BA.debugLine="If ml.HaveRoot Then";
if (mostCurrent._ml.HaveRoot) { 
 //BA.debugLineNum = 494;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 495;BA.debugLine="Msgbox(\"Congratulations! Original Restored\" & CR";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Congratulations! Original Restored"+anywheresoftware.b4a.keywords.Common.CRLF+"Now, your device will be reboot!"),BA.ObjectToCharSequence("Completed"),mostCurrent.activityBA);
 //BA.debugLineNum = 496;BA.debugLine="ml.RootCmd(\"reboot\",\"\",Null,Null,False)";
mostCurrent._ml.RootCmd("reboot","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 498;BA.debugLine="Msgbox(\"Your device not have Root Access!\",\"Atte";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Your device not have Root Access!"),BA.ObjectToCharSequence("Attention!"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 500;BA.debugLine="rst.Enabled = False";
_rst.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 501;BA.debugLine="End Sub";
return "";
}
public static String  _ti_tick() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub ti_Tick";
 //BA.debugLineNum = 131;BA.debugLine="ml.RootCmd(\"mount -o rw,remount /system\",\"\",Null,";
mostCurrent._ml.RootCmd("mount -o rw,remount /system","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 132;BA.debugLine="If ml.Exists(\"/system/Ht3tzN4ing.ttf\") = False Th";
if (mostCurrent._ml.Exists("/system/Ht3tzN4ing.ttf")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 133;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /syst";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/Ht3tzN4ing.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 135;BA.debugLine="If ml.Exists(\"/system/fonts/Padauk.ttf\") = True";
if (mostCurrent._ml.Exists("/system/fonts/Padauk.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 136;BA.debugLine="ml.mv(\"/system/fonts/Padauk.ttf\",\"/system/fonts";
mostCurrent._ml.mv("/system/fonts/Padauk.ttf","/system/fonts/Padauk.ttf.bak");
 //BA.debugLineNum = 137;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Padauk.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 138;BA.debugLine="ml.chmod(\"/system/fonts/Padauk.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Padauk.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 142;BA.debugLine="If ml.Exists(\"/system/fonts/Padauk-book.ttf\") =";
if (mostCurrent._ml.Exists("/system/fonts/Padauk-book.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 143;BA.debugLine="ml.mv(\"/system/fonts/Padauk-book.ttf\",\"/system/";
mostCurrent._ml.mv("/system/fonts/Padauk-book.ttf","/system/fonts/Padauk-book.ttf.bak");
 //BA.debugLineNum = 144;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Padauk-book.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 145;BA.debugLine="ml.chmod(\"/system/fonts/Padauk-book.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Padauk-book.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 149;BA.debugLine="If ml.Exists(\"/system/fonts/Padauk-bookbold.ttf\"";
if (mostCurrent._ml.Exists("/system/fonts/Padauk-bookbold.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 150;BA.debugLine="ml.mv(\"/system/fonts/Padauk-bookbold.ttf\",\"/sys";
mostCurrent._ml.mv("/system/fonts/Padauk-bookbold.ttf","/system/fonts/Padauk-bookbold.ttf.bak");
 //BA.debugLineNum = 151;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Padauk-bookbold.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 152;BA.debugLine="ml.chmod(\"/system/fonts/Padauk-bookbold.ttf\",64";
mostCurrent._ml.chmod("/system/fonts/Padauk-bookbold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 156;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmar-Bold";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmar-Bold.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 157;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmar-Bold.ttf\",";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmar-Bold.ttf","/system/fonts/NotoSansMyanmar-Bold.ttf.bak");
 //BA.debugLineNum = 158;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmar-Bold.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 159;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmar-Bold.tt";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmar-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 163;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmar-Regu";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmar-Regular.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 164;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmar-Regular.tt";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmar-Regular.ttf","/system/fonts/NotoSansMyanmar-Regular.ttf.bak");
 //BA.debugLineNum = 165;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmar-Regular.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 166;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmar-Regular";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmar-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 170;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarUI-Bo";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarUI-Bold.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 171;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmarUI-Bold.ttf";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmarUI-Bold.ttf","/system/fonts/NotoSansMyanmarUI-Bold.ttf.bak");
 //BA.debugLineNum = 172;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmarUI-Bold.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 173;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarUI-Bold.";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarUI-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 177;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarUI-Re";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarUI-Regular.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 178;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmarUI-Regular.";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmarUI-Regular.ttf","/system/fonts/NotoSansMyanmarUI-Regular.ttf.bak");
 //BA.debugLineNum = 179;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmarUI-Regular.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 180;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarUI-Regul";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarUI-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 185;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarZawgy";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 186;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmarZawgyi-Bold";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf","/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf.bak");
 //BA.debugLineNum = 187;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmarZawgyi-Bold.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 188;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarZawgyi-B";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 192;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarZawgy";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 193;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmarZawgyi-Regu";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf","/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf.bak");
 //BA.debugLineNum = 194;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmarZawgyi-Regular.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 195;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarZawgyi-R";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 199;BA.debugLine="If ml.Exists(\"/system/fonts/SamsungMyanmar.ttf\")";
if (mostCurrent._ml.Exists("/system/fonts/SamsungMyanmar.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 200;BA.debugLine="ml.mv(\"/system/fonts/SamsungMyanmar.ttf\",\"/syst";
mostCurrent._ml.mv("/system/fonts/SamsungMyanmar.ttf","/system/fonts/SamsungMyanmar.ttf.bak");
 //BA.debugLineNum = 201;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/SamsungMyanmar.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 202;BA.debugLine="ml.chmod(\"/system/fonts/SamsungMyanmar.ttf\",644";
mostCurrent._ml.chmod("/system/fonts/SamsungMyanmar.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 206;BA.debugLine="If ml.Exists(\"/system/fonts/ZawgyiOne.ttf\") = Tr";
if (mostCurrent._ml.Exists("/system/fonts/ZawgyiOne.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 207;BA.debugLine="ml.mv(\"/system/fonts/ZawgyiOne.ttf\",\"/system/fo";
mostCurrent._ml.mv("/system/fonts/ZawgyiOne.ttf","/system/fonts/ZawgyiOne.ttf.bak");
 //BA.debugLineNum = 208;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/ZawgyiOne.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 209;BA.debugLine="ml.chmod(\"/system/fonts/ZawgyiOne.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/ZawgyiOne.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 213;BA.debugLine="If ml.Exists(\"/system/fonts/ZawgyiOne2008.ttf\")";
if (mostCurrent._ml.Exists("/system/fonts/ZawgyiOne2008.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 214;BA.debugLine="ml.mv(\"/system/fonts/ZawgyiOne2008.ttf\",\"/syste";
mostCurrent._ml.mv("/system/fonts/ZawgyiOne2008.ttf","/system/fonts/ZawgyiOne2008.ttf.bak");
 //BA.debugLineNum = 215;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/ZawgyiOne2008.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 216;BA.debugLine="ml.chmod(\"/system/fonts/ZawgyiOne2008.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/ZawgyiOne2008.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 220;BA.debugLine="If ml.Exists(\"/system/fonts/mmsd.ttf\") = True Th";
if (mostCurrent._ml.Exists("/system/fonts/mmsd.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 221;BA.debugLine="ml.mv(\"/system/fonts/mmsd.ttf\",\"/system/fonts/m";
mostCurrent._ml.mv("/system/fonts/mmsd.ttf","/system/fonts/mmsd.ttf.bak");
 //BA.debugLineNum = 222;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/mmsd.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 223;BA.debugLine="ml.chmod(\"/system/fonts/mmsd.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/mmsd.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 227;BA.debugLine="If ml.Exists(\"/system/fonts/Roboto-Regular.ttf\")";
if (mostCurrent._ml.Exists("/system/fonts/Roboto-Regular.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 228;BA.debugLine="ml.mv(\"/system/fonts/Roboto-Regular.ttf\",\"/syst";
mostCurrent._ml.mv("/system/fonts/Roboto-Regular.ttf","/system/fonts/Roboto-Regular.ttf.bak");
 //BA.debugLineNum = 229;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Roboto-Regular.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 230;BA.debugLine="ml.chmod(\"/system/fonts/Roboto-Regular.ttf\",644";
mostCurrent._ml.chmod("/system/fonts/Roboto-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 233;BA.debugLine="If ml.Exists(\"/system/fonts/Roboto-Light.ttf\") =";
if (mostCurrent._ml.Exists("/system/fonts/Roboto-Light.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 234;BA.debugLine="ml.mv(\"/system/fonts/Roboto-Light.ttf\",\"/system";
mostCurrent._ml.mv("/system/fonts/Roboto-Light.ttf","/system/fonts/Roboto-Light.ttf.bak");
 //BA.debugLineNum = 235;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Roboto-Light.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 236;BA.debugLine="ml.chmod(\"/system/fonts/Roboto-Light.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Roboto-Light.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 239;BA.debugLine="If ml.Exists(\"/system/fonts/Roboto-Bold.ttf\") =";
if (mostCurrent._ml.Exists("/system/fonts/Roboto-Bold.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 240;BA.debugLine="ml.mv(\"/system/fonts/Roboto-Bold.ttf\",\"/system/";
mostCurrent._ml.mv("/system/fonts/Roboto-Bold.ttf","/system/fonts/Roboto-Bold.ttf.bak");
 //BA.debugLineNum = 241;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Roboto-Bold.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 242;BA.debugLine="ml.chmod(\"/system/fonts/Roboto-Bold.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Roboto-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 245;BA.debugLine="ist.Enabled = True";
_ist.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 246;BA.debugLine="ti.Enabled = False";
_ti.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 251;BA.debugLine="If ml.Exists(\"/system/fonts/Padauk.ttf\") = True";
if (mostCurrent._ml.Exists("/system/fonts/Padauk.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 252;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Padauk.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 253;BA.debugLine="ml.chmod(\"/system/fonts/Padauk.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Padauk.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 257;BA.debugLine="If ml.Exists(\"/system/fonts/Padauk-book.ttf\") =";
if (mostCurrent._ml.Exists("/system/fonts/Padauk-book.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 258;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Padauk-book.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 259;BA.debugLine="ml.chmod(\"/system/fonts/Padauk-book.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Padauk-book.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 263;BA.debugLine="If ml.Exists(\"/system/fonts/Padauk-bookbold.ttf\"";
if (mostCurrent._ml.Exists("/system/fonts/Padauk-bookbold.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 264;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Padauk-bookbold.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 265;BA.debugLine="ml.chmod(\"/system/fonts/Padauk-bookbold.ttf\",64";
mostCurrent._ml.chmod("/system/fonts/Padauk-bookbold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 269;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmar-Bold";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmar-Bold.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 270;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmar-Bold.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 271;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmar-Bold.tt";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmar-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 275;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmar-Regu";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmar-Regular.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 276;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmar-Regular.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 277;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmar-Regular";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmar-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 281;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarUI-Bo";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarUI-Bold.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 282;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmarUI-Bold.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 283;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarUI-Bold.";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarUI-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 287;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarUI-Re";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarUI-Regular.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 288;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmarUI-Regular.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 289;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarUI-Regul";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarUI-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 294;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarZawgy";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 295;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmarZawgyi-Bold.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 296;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarZawgyi-B";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 300;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarZawgy";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 301;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/NotoSansMyanmarZawgyi-Regular.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 302;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarZawgyi-R";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 306;BA.debugLine="If ml.Exists(\"/system/fonts/SamsungMyanmar.ttf\")";
if (mostCurrent._ml.Exists("/system/fonts/SamsungMyanmar.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 307;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/SamsungMyanmar.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 308;BA.debugLine="ml.chmod(\"/system/fonts/SamsungMyanmar.ttf\",644";
mostCurrent._ml.chmod("/system/fonts/SamsungMyanmar.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 312;BA.debugLine="If ml.Exists(\"/system/fonts/ZawgyiOne.ttf\") = Tr";
if (mostCurrent._ml.Exists("/system/fonts/ZawgyiOne.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 313;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/ZawgyiOne.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 314;BA.debugLine="ml.chmod(\"/system/fonts/ZawgyiOne.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/ZawgyiOne.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 318;BA.debugLine="If ml.Exists(\"/system/fonts/ZawgyiOne2008.ttf\")";
if (mostCurrent._ml.Exists("/system/fonts/ZawgyiOne2008.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 319;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/ZawgyiOne2008.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 320;BA.debugLine="ml.chmod(\"/system/fonts/ZawgyiOne2008.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/ZawgyiOne2008.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 324;BA.debugLine="If ml.Exists(\"/system/fonts/mmsd.ttf\") = True Th";
if (mostCurrent._ml.Exists("/system/fonts/mmsd.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 325;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/mmsd.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 326;BA.debugLine="ml.chmod(\"/system/fonts/mmsd.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/mmsd.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 330;BA.debugLine="If ml.Exists(\"/system/fonts/Roboto-Regular.ttf\")";
if (mostCurrent._ml.Exists("/system/fonts/Roboto-Regular.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 331;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Roboto-Regular.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 332;BA.debugLine="ml.chmod(\"/system/fonts/Roboto-Regular.ttf\",644";
mostCurrent._ml.chmod("/system/fonts/Roboto-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 335;BA.debugLine="If ml.Exists(\"/system/fonts/Roboto-Light.ttf\") =";
if (mostCurrent._ml.Exists("/system/fonts/Roboto-Light.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 336;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Roboto-Light.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 337;BA.debugLine="ml.chmod(\"/system/fonts/Roboto-Light.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Roboto-Light.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 340;BA.debugLine="If ml.Exists(\"/system/fonts/Roboto-Bold.ttf\") =";
if (mostCurrent._ml.Exists("/system/fonts/Roboto-Bold.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 341;BA.debugLine="ml.RootCmd(\"cp -r /sdcard/MyanmarHeart.ttf /sys";
mostCurrent._ml.RootCmd("cp -r /sdcard/MyanmarHeart.ttf /system/fonts/Roboto-Bold.ttf","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 342;BA.debugLine="ml.chmod(\"/system/fonts/Roboto-Bold.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Roboto-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 345;BA.debugLine="ist.Enabled = True";
_ist.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 346;BA.debugLine="ti.Enabled = False";
_ti.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
return "";
}
public static String  _tr_tick() throws Exception{
 //BA.debugLineNum = 370;BA.debugLine="Sub tr_Tick";
 //BA.debugLineNum = 371;BA.debugLine="ml.RootCmd(\"mount -o rw,remount /system\",\"\",Null,";
mostCurrent._ml.RootCmd("mount -o rw,remount /system","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 374;BA.debugLine="If ml.Exists(\"/system/fonts/Padauk.ttf.bak\") = Tr";
if (mostCurrent._ml.Exists("/system/fonts/Padauk.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 375;BA.debugLine="ml.rm(\"/system/fonts/Padauk.ttf\")";
mostCurrent._ml.rm("/system/fonts/Padauk.ttf");
 //BA.debugLineNum = 376;BA.debugLine="ml.mv(\"/system/fonts/Padauk.ttf.bak\",\"/system/fo";
mostCurrent._ml.mv("/system/fonts/Padauk.ttf.bak","/system/fonts/Padauk.ttf");
 //BA.debugLineNum = 377;BA.debugLine="ml.chmod(\"/system/fonts/Padauk.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Padauk.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 381;BA.debugLine="If ml.Exists(\"/system/fonts/Padauk-book.ttf.bak\")";
if (mostCurrent._ml.Exists("/system/fonts/Padauk-book.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 382;BA.debugLine="ml.rm(\"/system/fonts/Padauk-book.ttf\")";
mostCurrent._ml.rm("/system/fonts/Padauk-book.ttf");
 //BA.debugLineNum = 383;BA.debugLine="ml.mv(\"/system/fonts/Padauk-book.ttf.bak\",\"/syst";
mostCurrent._ml.mv("/system/fonts/Padauk-book.ttf.bak","/system/fonts/Padauk-book.ttf");
 //BA.debugLineNum = 384;BA.debugLine="ml.chmod(\"/system/fonts/Padauk-book.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Padauk-book.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 388;BA.debugLine="If ml.Exists(\"/system/fonts/Padauk-bookbold.ttf.b";
if (mostCurrent._ml.Exists("/system/fonts/Padauk-bookbold.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 389;BA.debugLine="ml.rm(\"/system/fonts/Padauk-bookbold.ttf\")";
mostCurrent._ml.rm("/system/fonts/Padauk-bookbold.ttf");
 //BA.debugLineNum = 390;BA.debugLine="ml.mv(\"/system/fonts/Padauk-bookbold.ttf.bak\",\"/";
mostCurrent._ml.mv("/system/fonts/Padauk-bookbold.ttf.bak","/system/fonts/Padauk-bookbold.ttf");
 //BA.debugLineNum = 391;BA.debugLine="ml.chmod(\"/system/fonts/Padauk-bookbold.ttf\",644";
mostCurrent._ml.chmod("/system/fonts/Padauk-bookbold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 395;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmar-Bold.";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmar-Bold.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 396;BA.debugLine="ml.rm(\"/system/fonts/NotoSansMyanmar-Bold.ttf\")";
mostCurrent._ml.rm("/system/fonts/NotoSansMyanmar-Bold.ttf");
 //BA.debugLineNum = 397;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmar-Bold.ttf.ba";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmar-Bold.ttf.bak","/system/fonts/NotoSansMyanmar-Bold.ttf");
 //BA.debugLineNum = 398;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmar-Bold.ttf";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmar-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 402;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmar-Regul";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmar-Regular.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 403;BA.debugLine="ml.rm(\"/system/fonts/NotoSansMyanmar-Regular.ttf";
mostCurrent._ml.rm("/system/fonts/NotoSansMyanmar-Regular.ttf");
 //BA.debugLineNum = 404;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmar-Regular.ttf";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmar-Regular.ttf.bak","/system/fonts/NotoSansMyanmar-Regular.ttf");
 //BA.debugLineNum = 405;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmar-Regular.";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmar-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 409;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarUI-Bol";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarUI-Bold.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 410;BA.debugLine="ml.rm(\"/system/fonts/NotoSansMyanmarUI-Bold.ttf\"";
mostCurrent._ml.rm("/system/fonts/NotoSansMyanmarUI-Bold.ttf");
 //BA.debugLineNum = 411;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmarUI-Bold.ttf.";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmarUI-Bold.ttf.bak","/system/fonts/NotoSansMyanmarUI-Bold.ttf");
 //BA.debugLineNum = 412;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarUI-Bold.t";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarUI-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 416;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarUI-Reg";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarUI-Regular.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 417;BA.debugLine="ml.rm(\"/system/fonts/NotoSansMyanmarUI-Regular.t";
mostCurrent._ml.rm("/system/fonts/NotoSansMyanmarUI-Regular.ttf");
 //BA.debugLineNum = 418;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmarUI-Regular.t";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmarUI-Regular.ttf.bak","/system/fonts/NotoSansMyanmarUI-Regular.ttf");
 //BA.debugLineNum = 419;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarUI-Regula";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarUI-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 423;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarZawgyi";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 424;BA.debugLine="ml.rm(\"/system/fonts/NotoSansMyanmarZawgyi-Bold.";
mostCurrent._ml.rm("/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf");
 //BA.debugLineNum = 425;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmarZawgyi-Bold.";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf.bak","/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf");
 //BA.debugLineNum = 426;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarZawgyi-Bo";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 430;BA.debugLine="If ml.Exists(\"/system/fonts/NotoSansMyanmarZawgyi";
if (mostCurrent._ml.Exists("/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 431;BA.debugLine="ml.rm(\"/system/fonts/NotoSansMyanmarZawgyi-Regul";
mostCurrent._ml.rm("/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf");
 //BA.debugLineNum = 432;BA.debugLine="ml.mv(\"/system/fonts/NotoSansMyanmarZawgyi-Regul";
mostCurrent._ml.mv("/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf.bak","/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf");
 //BA.debugLineNum = 433;BA.debugLine="ml.chmod(\"/system/fonts/NotoSansMyanmarZawgyi-Re";
mostCurrent._ml.chmod("/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 437;BA.debugLine="If ml.Exists(\"/system/fonts/SamsungMyanmar.ttf.ba";
if (mostCurrent._ml.Exists("/system/fonts/SamsungMyanmar.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 438;BA.debugLine="ml.rm(\"/system/fonts/SamsungMyanmar.ttf\")";
mostCurrent._ml.rm("/system/fonts/SamsungMyanmar.ttf");
 //BA.debugLineNum = 439;BA.debugLine="ml.mv(\"/system/fonts/SamsungMyanmar.ttf.bak\",\"/s";
mostCurrent._ml.mv("/system/fonts/SamsungMyanmar.ttf.bak","/system/fonts/SamsungMyanmar.ttf");
 //BA.debugLineNum = 440;BA.debugLine="ml.chmod(\"/system/fonts/SamsungMyanmar.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/SamsungMyanmar.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 444;BA.debugLine="If ml.Exists(\"/system/fonts/ZawgyiOne.ttf.bak\") =";
if (mostCurrent._ml.Exists("/system/fonts/ZawgyiOne.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 445;BA.debugLine="ml.rm(\"/system/fonts/ZawgyiOne.ttf\")";
mostCurrent._ml.rm("/system/fonts/ZawgyiOne.ttf");
 //BA.debugLineNum = 446;BA.debugLine="ml.mv(\"/system/fonts/ZawgyiOne.ttf.bak\",\"/system";
mostCurrent._ml.mv("/system/fonts/ZawgyiOne.ttf.bak","/system/fonts/ZawgyiOne.ttf");
 //BA.debugLineNum = 447;BA.debugLine="ml.chmod(\"/system/fonts/ZawgyiOne.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/ZawgyiOne.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 451;BA.debugLine="If ml.Exists(\"/system/fonts/ZawgyiOne2008.ttf.bak";
if (mostCurrent._ml.Exists("/system/fonts/ZawgyiOne2008.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 452;BA.debugLine="ml.rm(\"/system/fonts/ZawgyiOne2008.ttf\")";
mostCurrent._ml.rm("/system/fonts/ZawgyiOne2008.ttf");
 //BA.debugLineNum = 453;BA.debugLine="ml.mv(\"/system/fonts/ZawgyiOne2008.ttf.bak\",\"/sy";
mostCurrent._ml.mv("/system/fonts/ZawgyiOne2008.ttf.bak","/system/fonts/ZawgyiOne2008.ttf");
 //BA.debugLineNum = 454;BA.debugLine="ml.chmod(\"/system/fonts/ZawgyiOne2008.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/ZawgyiOne2008.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 458;BA.debugLine="If ml.Exists(\"/system/fonts/mmsd.ttf.bak\") = True";
if (mostCurrent._ml.Exists("/system/fonts/mmsd.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 459;BA.debugLine="ml.rm(\"/system/fonts/mmsd.ttf\")";
mostCurrent._ml.rm("/system/fonts/mmsd.ttf");
 //BA.debugLineNum = 460;BA.debugLine="ml.mv(\"/system/fonts/mmsd.ttf.bak\",\"/system/font";
mostCurrent._ml.mv("/system/fonts/mmsd.ttf.bak","/system/fonts/mmsd.ttf");
 //BA.debugLineNum = 461;BA.debugLine="ml.chmod(\"/system/fonts/mmsd.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/mmsd.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 465;BA.debugLine="If ml.Exists(\"/system/fonts/Roboto-Bold.ttf.bak\")";
if (mostCurrent._ml.Exists("/system/fonts/Roboto-Bold.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 466;BA.debugLine="ml.rm(\"/system/fonts/Roboto-Bold.ttf\")";
mostCurrent._ml.rm("/system/fonts/Roboto-Bold.ttf");
 //BA.debugLineNum = 467;BA.debugLine="ml.mv(\"/system/fonts/Roboto-Bold.ttf.bak\",\"/syst";
mostCurrent._ml.mv("/system/fonts/Roboto-Bold.ttf.bak","/system/fonts/Roboto-Bold.ttf");
 //BA.debugLineNum = 468;BA.debugLine="ml.chmod(\"/system/fonts/Roboto-Bold.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Roboto-Bold.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 471;BA.debugLine="If ml.Exists(\"/system/fonts/Roboto-Light.ttf.bak\"";
if (mostCurrent._ml.Exists("/system/fonts/Roboto-Light.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 472;BA.debugLine="ml.rm(\"/system/fonts/Roboto-Light.ttf\")";
mostCurrent._ml.rm("/system/fonts/Roboto-Light.ttf");
 //BA.debugLineNum = 473;BA.debugLine="ml.mv(\"/system/fonts/Roboto-Light.ttf.bak\",\"/sys";
mostCurrent._ml.mv("/system/fonts/Roboto-Light.ttf.bak","/system/fonts/Roboto-Light.ttf");
 //BA.debugLineNum = 474;BA.debugLine="ml.chmod(\"/system/fonts/Roboto-Light.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Roboto-Light.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 477;BA.debugLine="If ml.Exists(\"/system/fonts/Roboto-Regular.ttf.ba";
if (mostCurrent._ml.Exists("/system/fonts/Roboto-Regular.ttf.bak")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 478;BA.debugLine="ml.rm(\"/system/fonts/Roboto-Regular.ttf\")";
mostCurrent._ml.rm("/system/fonts/Roboto-Regular.ttf");
 //BA.debugLineNum = 479;BA.debugLine="ml.mv(\"/system/fonts/Roboto-Regular.ttf.bak\",\"/s";
mostCurrent._ml.mv("/system/fonts/Roboto-Regular.ttf.bak","/system/fonts/Roboto-Regular.ttf");
 //BA.debugLineNum = 480;BA.debugLine="ml.chmod(\"/system/fonts/Roboto-Regular.ttf\",644)";
mostCurrent._ml.chmod("/system/fonts/Roboto-Regular.ttf",BA.NumberToString(644));
 };
 //BA.debugLineNum = 483;BA.debugLine="If ml.Exists(\"/system/Ht3tzN4ing.ttf\") = True The";
if (mostCurrent._ml.Exists("/system/Ht3tzN4ing.ttf")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 484;BA.debugLine="ml.rm(\"/system/Ht3tzN4ing.ttf\")";
mostCurrent._ml.rm("/system/Ht3tzN4ing.ttf");
 };
 //BA.debugLineNum = 487;BA.debugLine="rst.Enabled = True";
_rst.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 488;BA.debugLine="tr.Enabled = False";
_tr.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 489;BA.debugLine="End Sub";
return "";
}
}
