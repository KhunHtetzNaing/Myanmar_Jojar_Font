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
End Sub

Sub Activity_Create(FirstTime As Boolean)
	
	lb.Initialize("lb")
	lb.Gravity = Gravity.CENTER
	lb.Text = "Install ကိုနွိပ္ျပီး Myanmar Jojar(iFont) ကို Install လုပ္ေပးလိုက္ပါ။  Install လုပ္လို့ၿပီးရင္ Done ကိုသာနွိပ္ေပးပါ။ ျပီးရင္ေအာက္ပါ Change Font ကိုနွိပ္ျပီး Myanmar Jojar(iFont) ကိုေရြးေပးလိုက္ပါ။ နဂိုမူလေဖာင့္ကိုျပန္ထားခ်င္ရင္ Change Font ကိုနွိပ္ျပီး Default ကိုျပန္ေရြးထားနိုင္ပါတယ္။"
	Activity.AddView(lb,2%x,1%y,90%x,35%y)
	lb.Typeface = mm

	Activity.Title = "Oppo"
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
	File.Copy(File.DirAssets,"Jojar.apk",File.DirRootExternal,"Jojar.apk")
	Msgbox("1. Install Fone App" & CRLF & "2. After Installed click done!" & CRLF & "3. Click Change Font and Choose your Font!","Attention!")
	Dim i As Intent
	i.Initialize(i.ACTION_VIEW,"file:///"&File.DirRootExternal& "/Jojar.apk")
	i.SetType("application/vnd.android.package-archive")
	StartActivity(i)
End Sub

Sub b2_Click
	If File.Exists(File.DirRootExternal,"Jojar.apk") = True Then
		File.Delete(File.DirRootExternal,"Jojar.apk")
	End If
	Try
		Dim i As Intent
		i.Initialize("", "")
		i.SetComponent("com.android.settings/.flipfont.FontListActivity")
		StartActivity(i)
	Catch
		Dim abc As Intent
		abc.Initialize("android.settings.DISPLAY_SETTINGS","")
		StartActivity(abc)
	End Try
End Sub

Sub b3_Click
	If File.Exists(File.DirRootExternal,"Jojar.apk") = True Then
		File.Delete(File.DirRootExternal,"Jojar.apk")
	End If
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