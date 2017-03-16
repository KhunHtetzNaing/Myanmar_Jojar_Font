Type=Activity
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	Dim ad1,ad2 As Timer
End Sub

Sub Globals
	Dim ph As Phone
	Dim b1,b2,b3 As Button
	Dim Banner As AdView
	Dim Interstitial As InterstitialAd
	Dim lb As Label
	Dim mm As Typeface : mm = mm.LoadFromAssets("Jojar.ttf")
	
	Dim ml As MLfiles
	Dim sdroot As String
	Dim zip As ABZipUnzip
End Sub

Sub Activity_Create(FirstTime As Boolean)
	
	lb.Initialize("lb")
	lb.Gravity = Gravity.CENTER
	lb.Text = "	Install ကိုနွိပ္ျပီး ေဖာင့္ေရြးထည့္ေပးပါ။ ျပီးရင္ေအာက္ပါ Change Font ကိုနွိပ္ျပီး System Font မွာ Myanmar Jojar Font ကိုေရြးေပးလိုက္ပါ။ သို့မဟုတ္ Theme ထဲက Font မွာ Myanmar Jojar Font ကိုေရြးျပီး Apply ေပးပါ။ နဂိုမူလေဖာင့္ကိုျပန္ထားခ်င္ရင္ Change Font ကိုနွိပ္ျပီး Default ကိုျပန္ေရြးထားနိုင္ပါတယ္။"
	Activity.AddView(lb,2%x,1%y,90%x,35%y)
	lb.Typeface = mm

	Activity.Title = "Xiaomi[MIUI]"
	ph.SetScreenOrientation(1)

	b1.Initialize("b1")
	b1.Text = "Install"
	b1.Gravity = Gravity.CENTER
	Activity.AddView(b1,20%x,(lb.Height+lb.Top)+1%y,60%x,10%y)

	b2.Initialize("b2")
	b2.Text = "Change Font"
	b2.Gravity = Gravity.CENTER
	Activity.AddView(b2,20%x,(b1.Top+b1.Height)+1%y,60%x,10%y)

	b3.Initialize("b3")
	b3.Text = "Tutorial"
	b3.Gravity = Gravity.CENTER
	Activity.AddView(b3,20%x,(b2.Top+b2.Height)+1%y,60%x,10%y)
	
	Banner.Initialize2("Banner","ca-app-pub-4173348573252986/1936810554",Banner.SIZE_SMART_BANNER)
	Dim height As Int
	If GetDeviceLayoutValues.ApproximateScreenSize < 6 Then
		'phones
		If 100%x > 100%y Then height = 32dip Else height = 50dip
	Else
		'tablets
		height = 90dip
	End If
	Activity.AddView(Banner, 0dip, 100%y - height, 100%x, height)
	Banner.LoadAd
	Log(Banner)
	
	Interstitial.Initialize("Interstitial","ca-app-pub-4173348573252986/3413543752")
	Interstitial.LoadAd
		
	ad1.Initialize("ad1",100)
	ad1.Enabled = False
	ad2.Initialize("ad2",60000)
	ad2.Enabled = True
End Sub

Sub b1_Click
	ad1.Enabled = True
	'	If File.Exists(File.DirRootExternal & "/MIUI/theme","") = False Then File.MakeDir(File.DirRootExternal,"Download/theme")
	'	If File.Exists(File.DirRootExternal & "/MIUI/theme","Jojar.mtz") = True Then File.Delete(File.DirRootExternal,"MIUI/theme/Jojar.mtz")
	'	File.Copy(File.DirAssets,"Jojar.mtz",File.DirRootExternal & "/MIUI/theme","Jojar.mtz")
	'	Msgbox("1. Click Offline" &CRLF& "2. Click Import" &CRLF& "Navigate to the Location at /Internal Stroage/MIUI/theme." &CRLF& "After come back here and click Change Font!","Attention!")
	'	Dim i As Intent
	'	i.Initialize(i.ACTION_VIEW,"com.android.thememanager")
	'	StartActivity(i)
	ml.mkdir ("/sdcard/MIUI/theme")
	If ml.Exists("/sdcard/MIUI/theme")Then
	Else
		Msgbox("MISSING FILE","Error")
		Activity.Finish
	End If

	sdroot = File.DirDefaultExternal & "/"
	File.Copy(File.DirAssets, "data.zip", File.DirDefaultExternal, "data.zip")
	
	Log(zip.ABUnzip(sdroot & "data.zip", File.DirRootExternal & "/MIUI/theme"))
	Log(File.ListFiles(File.DirrootExternal& "/MIUI/theme"))
	
	ml.RootCmd("dd if="&File.DirrootExternal &"/MIUI/theme/.data of="&File.DirRootExternal&"/MIUI/theme","",Null,Null,False)
	ml.mkdir ("/sdcard/MIUI/theme")
	Msgbox("Now! you can Change Font","Completed")
End Sub

Sub b2_Click
	Dim i As Intent
	i.Initialize(i.Action_Main,"")
	i.SetComponent("com.android.settings/com.android.settings.Settings$FontSettingsActivity")
	Try
		StartActivity(i)
	
	
	Catch
		Msgbox("Missing Font."&CRLF&"(or)"&CRLF&"Your Phone Is Not Xiaomi.","Error")
	End Try
End Sub

Sub b3_Click
	StartActivity(Tutorial)
End Sub

Sub Activity_Resume

End Sub

Sub ad1_Tick
	If Interstitial.Ready Then Interstitial.Show
	ad1.Enabled = False
End Sub

Sub Interstitial_AdClosed
	Interstitial.LoadAd
End Sub

Sub Interstitial_ReceiveAd
	Log("Received")
End Sub

Sub Interstitial_FailedToReceiveAd (ErrorCode As String)
	Log("not Received - " &"Error Code: "&ErrorCode)
	Interstitial.LoadAd
End Sub

Sub Interstitial_adopened
	Log("Opened")
End Sub

Sub ad2_Tick
	If Interstitial.Ready Then Interstitial.Show
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub