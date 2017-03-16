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
	Dim b1,b2,b3,b4 As Button
	Dim Banner As AdView
	Dim Interstitial As InterstitialAd
	Dim lb As Label
	Dim mm As Typeface : mm = mm.LoadFromAssets("Jojar.ttf")
	Dim msg As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	
	lb.Initialize("lb")
	lb.Gravity = Gravity.CENTER
	lb.Text = "	Install  ကိုနွိပ္ပါ။ ျပီးရင္ေအာက္ပါ Change Font1 ကိုနွိပ္ပါ Jojar.txj (သို့) Jojar.itz ကိုဖြင့္ၿပီး Apply လုပ္ေပးလိုက္ပါ မရခဲ့ရင္ Change Font2 ကိုနွိပ္ျပီး Myanmar Jojar နာမည္နဲ့ Theme ကိုေရြးေပးလိုက္ပါ။ နဂိုမူလေဖာင့္ကိုျပန္ထားခ်င္ရင္ Change Font2 ကိုနွိပ္ျပီး Default ကိုျပန္ေရြးထားနိုင္ပါတယ္။"
	Activity.AddView(lb,2%x,1%y,90%x,35%y)
	lb.Typeface = mm
	
	msg.Initialize("msg")
	msg.Color = Colors.White
	msg.Gravity = Gravity.CENTER
	msg.Typeface = mm

	Activity.Title = "Vivo"
	ph.SetScreenOrientation(1)

	b1.Initialize("b1")
	b1.Text = "Install"
	b1.Gravity = Gravity.CENTER
	Activity.AddView(b1,20%x,(lb.Height+lb.Top)+1%y,60%x,10%y)

	b2.Initialize("b2")
	b2.Text = "Change Font1"
	b2.Gravity = Gravity.CENTER
	Activity.AddView(b2,20%x,(b1.Top+b1.Height)+1%y,60%x,10%y)

	b4.Initialize("b4")
	b4.Text = "Change Font2"
	b4.Gravity = Gravity.CENTER
	Activity.AddView(b4,20%x,(b2.Top+b2.Height)+1%y,60%x,10%y)
	
	b3.Initialize("b3")
	b3.Text = "Tutorial"
	b3.Gravity = Gravity.CENTER
	Activity.AddView(b3,20%x,(b4.Top+b4.Height)+1%y,60%x,10%y)
	
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
	If File.Exists(File.DirRootExternal & "/Vivo Myanmar Font","") = False Then File.MakeDir(File.DirRootExternal,"Vivo Myanmar Font")
	If File.Exists(File.DirRootExternal & "/Vivo Myanmar Font","Jojar.txj") = True Then File.Delete(File.DirRootExternal,"Vivo Myanmar Font/Jojar.txj")
	File.Copy(File.DirAssets,"Jojar.txj",File.DirRootExternal & "/Vivo Myanmar Font","Jojar.txj")
	
	If File.Exists(File.DirRootExternal & "/Vivo Myanmar Font","Jojar.itz") = True Then File.Delete(File.DirRootExternal,"Vivo Myanmar Font/Jojar.itz")
	File.Copy(File.DirAssets,"Jojar.itz",File.DirRootExternal & "/Vivo Myanmar Font","Jojar.itz")
	Msgbox("Installed" & CRLF & "Now! you can change font!","Attention!")
End Sub

Sub b2_Click
	Try
		Dim i As Intent
		i.Initialize(i.ACTION_VIEW, "file://" &  File.DirRootExternal&"/Vivo Myanmar Font" )
		i.SetType( "resource/folder" )
		StartActivity(i)
		ad1.Enabled = True
	Catch
		msg.TextSize = 25
		msg.TextColor = Colors.Magenta
		Activity.AddView(msg,0%x,0%y,100%x,100%y)
		msg.Visible = True
		b1.Visible=False
		b2.Visible=False
		msg.Text = "File Manager တစ္ခုခုထဲသို႔သြားၿပီး Vivo Myanmar Font ဖိုဒါထဲကိုဝင္ပါ။ Jojar.txj (သို့) Jojar.itz ကိုဖြင့္ၿပီး Apply လုပ္ေပးလိုက္ပါ သို႔မဟုတ္ Change Font2 နဲ႔ထပ္သြင္းပါ။"
		ad1.Enabled =True
	End Try
End Sub

Sub b4_Click
	If File.Exists(File.DirRootExternal & "/Download/i Theme/Font","") = False Then File.MakeDir(File.DirRootExternal,"Download/i Theme/Font")
	If File.Exists(File.DirRootExternal & "/Download/i Theme/Font","Jojar.txj") = True Then File.Delete(File.DirRootExternal,"Download/i Theme/Font/Jojar.txj")
	File.Copy(File.DirAssets,"Jojar.txj",File.DirRootExternal & "/Download/i Theme/Font","Jojar.txj")
	
	If File.Exists(File.DirRootExternal & "/Download/i Theme/Font","Jojar.itz") = True Then File.Delete(File.DirRootExternal,"Download/i Theme/Font/Jojar.itz")
	File.Copy(File.DirAssets,"Jojar.itz",File.DirRootExternal & "/Download/i Theme/Font","Jojar.itz")
	Try
		Dim i As Intent
		i.Initialize("", "")
		i.SetComponent("com.bbk.theme/.mixmatch.font.FontMain")
		StartActivity(i)
	Catch
		Dim i As Intent
		Dim pm As PackageManager
		i=pm.GetApplicationIntent("com.bbk.theme")
		StartActivity(i)
	End Try
End Sub

Sub b3_Click
	msg.Typeface = mm
	msg.TextSize = 15
	msg.TextColor = Colors.Red
	Activity.AddView(msg,0%x,0%y,100%x,100%y)
	msg.Visible = True
	b1.Visible=False
	b2.Visible=False
	msg.Text = "Install ကိုႏွိပ္ၿပီးတာနဲ႔ ဖုန္းမွာ Default File Manager တစ္ခုခုမေရြးထားရင္ေတာ့ ကိုယ့္ဘာသာကို ဖုန္းထဲက File Manager တစ္ခုခုထဲကိုသြားလိုက္ပါ။	ၿပီးရင္ Vivo Myanmar Font ဆိုတဲ့ဖိုဒါကိုရွာၿပီး ဝင္လိုက္ပါ။ အဲ့ဖိုဒါထဲမွာ Jojar.txj (သို့) Jojar.itz ဆိုတဲ့ဖိုင္ေလးကိုဖြင့္ၿပီး Apply ေပးလိုက္ပါ။ အဆင္မေျပတာမ်ားရွိရင္ Facebook က Myanmar Android App မွာလာေမးနိုင္ပါတယ္ :)"
	ad1.Enabled = True
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