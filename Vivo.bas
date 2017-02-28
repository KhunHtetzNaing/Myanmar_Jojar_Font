Type=Activity
Version=6.5
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	Dim ad1,ad2 As Timer
End Sub

Sub Globals
	Dim ph As Phone
	Dim b1,b2,b3,b4 As Label
	Dim Banner As AdView
	Dim Interstitial As InterstitialAd

	Dim sm As SlideMenu
	Dim tlb As Label
	Dim menu,share As Button
	Dim sbg,mbg As BitmapDrawable
	Dim copy As BClipboard
	Dim lb As Label
	Dim mm As Typeface : mm = mm.LoadFromAssets("Jojar.ttf")
	Dim msg As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	
	lb.Initialize("lb")
	lb.Gravity = Gravity.CENTER
	lb.Text = "	ပထမဦးစြာ Install ကိုႏွိပ္ပါ။ ျပီးရင္ေအာက္ပါ Change Font1 ကိုနွိပ္ပါ Jojar.txj ကိုဖြင့္ၿပီး Apply လုပ္ေပးလိုက္ပါ မရခဲ့ရင္ Change Font2 ကိုနွိပ္ျပီး Myanmar Jojar Font နာမည္နဲ့ Theme ကိုေရြးေပးလိုက္ပါ။"
	Activity.AddView(lb,2%x,55dip+1%y,90%x,35%y)
	lb.TextColor = Colors.Black
	lb.Typeface = mm
	msg.Initialize("msg")
	msg.Color = Colors.White
	msg.Gravity = Gravity.CENTER
	msg.Typeface = mm

	Activity.Color = Colors.White
	ph.SetScreenOrientation(1)

	b1.Initialize("b1")
	Dim b1bg As ColorDrawable
	b1bg.Initialize(Colors.Black,10)
	b1.Text = "Install"
	b1.Background = b1bg
	b1.Gravity = Gravity.CENTER
	b1.Textcolor = Colors.White
	b1.TextSize = 17
	Activity.AddView(b1,20%x,(lb.Height+lb.Top)+1%y,60%x,50dip)

	b2.Initialize("b2")
	b2.Background = b1bg
	b2.Text = "Change Font1"
	b2.Gravity = Gravity.CENTER
	b2.Textcolor = Colors.White
	b2.TextSize = 17
	Activity.AddView(b2,20%x,(b1.Top+b1.Height)+2%y,60%x,50dip)

	b4.Initialize("b4")
	b4.Background = b1bg
	b4.Text = "Change Font2"
	b4.Gravity = Gravity.CENTER
	b4.TextColor = Colors.White
	b4.TextSize = 17
	Activity.AddView(b4,20%x,(b2.Top+b2.Height)+2%y,60%x,50dip)
	
	b3.Initialize("b3")
	b3.Text = "Tutorial"
	b3.Background = b1bg
	b3.Gravity = Gravity.CENTER
	b3.Textcolor = Colors.White
	b3.TextSize = 17
	Activity.AddView(b3,20%x,(b4.Top+b4.Height)+2%y,60%x,50dip)
	
	'NEWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
	tlb.Initialize("tlb")
	tlb.Text = "Vivo"
	tlb.Color = Colors.rgb(233, 30, 99)
	tlb.TextColor = Colors.White
	tlb.TextSize = 25
	tlb.Typeface = Typeface.DEFAULT_BOLD
	
	tlb.Gravity = Gravity.CENTER
	Activity.AddView(tlb,0%x,0%y,100%x,55dip)
	
	sm.Initialize(Activity, Me, "SlideMenu",0,70%x)
	sm.AddItem("Samsung",LoadBitmap(File.DirAssets,"samsung.png"),1)
	sm.AddItem("Oppo",LoadBitmap(File.DirAssets,"oppo.png"),2)
	sm.AddItem("Vivo",LoadBitmap(File.DirAssets,"vivo.png"),3)
	sm.AddItem("Huawei",LoadBitmap(File.DirAssets,"huawei.jpg"),4)
	sm.AddItem("Xiaomi",LoadBitmap(File.DirAssets,"xiaomi.png"),5)
	sm.AddItem("Other [#Root]",LoadBitmap(File.DirAssets,"other.png"),6)
	sm.AddItem("Share App",LoadBitmap(File.DirAssets,"share.png"),7)
	sm.AddItem("More App",LoadBitmap(File.DirAssets,"moreapp.png"),8)
	sm.AddItem("About",LoadBitmap(File.DirAssets,"about.png"),9)
	
	mbg.Initialize(LoadBitmap(File.DirAssets,"menu.png"))
	menu.Initialize("menu")
	menu.Background = mbg
	menu.Gravity = Gravity.CENTER
	Activity.AddView(menu,10dip,12.5dip,30dip,30dip)
	
	sbg.Initialize(LoadBitmap(File.DirAssets,"share.png"))
	share.Initialize("share")
	share.Background = sbg
	share.Gravity = Gravity.CENTER
	Activity.AddView(share,100%x - 40dip,12.5dip,30dip,30dip)
	
	Banner.Initialize2("Banner","ca-app-pub-4173348573252986/4696438557",Banner.SIZE_SMART_BANNER)
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
		
	Interstitial.Initialize("Interstitial","ca-app-pub-4173348573252986/6173171758")
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
		msg.Text = "File Manager တစ္ခုခုထဲသို႔သြားၿပီး Vivo Myanmar Font ဖိုဒါထဲကိုဝင္ပါ။ Jojar.txj ကိုဖြင့္ၿပီး Apply လုပ္ေပးလိုက္ပါ သို႔မဟုတ္ Change Font2 နဲ႔ထပ္သြင္းၾကည့္ပါ။"
		ad1.Enabled =True
	End Try
End Sub

Sub b4_Click
	If File.Exists(File.DirRootExternal & "/Download/i Theme/Font","") = False Then File.MakeDir(File.DirRootExternal,"Download/i Theme/Font")
	If File.Exists(File.DirRootExternal & "/Download/i Theme/Font","Jojar.txj") = True Then File.Delete(File.DirRootExternal,"Download/i Theme/Font/Jojar.txj")
	File.Copy(File.DirAssets,"Jojar.txj",File.DirRootExternal & "/Download/i Theme/Font","Jojar.txj")
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
	msg.Text = "Install ကိုႏွိပ္ၿပီးတာနဲ႔ ဖုန္းမွာ Default File Manager တစ္ခုခုမေရြးထားရင္ေတာ့ ကိုယ့္ဘာသာကို ဖုန္းထဲက File Manager တစ္ခုခုထဲကိုသြားလိုက္ပါ။	ၿပီးရင္ Vivo Myanmar Font ဆိုတဲ့ဖိုဒါကိုရွာၿပီး ဝင္လိုက္ပါ။ အဲ့ဖိုဒါထဲမွာ Jojar.txj ဆိုတဲ့ဖိုင္ေလးကိုဖြင့္ၿပီး Apply ေပးလိုက္ပါ။ အဆင္မေျပတာမ်ားရွိရင္ Facebook က Myanmar Android Apps မွာလာေမးနိုင္ပါတယ္ :)"
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

Sub ad2_Tick
	If Interstitial.Ready Then Interstitial.Show
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub SlideMenu_Click(Item As Object)
	sm.Hide
	Select Item
		Case 1 :
			StartActivity(Samsung)
		Case 2 :
			StartActivity(Oppo)
		Case 3 :
			StartActivity(Me)
		Case 4 :
			StartActivity(Huawei)
		Case 5 :
			StartActivity(Xiaomi)
		Case 6 :
			StartActivity(Other)
		Case 7 :
			Dim ShareIt As Intent
			copy.clrText
			copy.setText("#Myanmar_Jojar_Font App! Beautiful Myanmar Zawgyi Font Style!	You can Use in Samung, Oppo,Vivo, Huawei (EMUI) and Xiaomi (MIUI) without Root Access!!!! Download Free at : http://bit.ly/2mqSEWy")
			ShareIt.Initialize (ShareIt.ACTION_SEND,"")
			ShareIt.SetType ("text/plain")
			ShareIt.PutExtra ("android.intent.extra.TEXT",copy.getText)
			ShareIt.PutExtra ("android.intent.extra.SUBJECT","Get Free!!")
			ShareIt.WrapAsIntentChooser("Share App Via...")
			StartActivity (ShareIt)
		Case 8 :
			Dim p As PhoneIntents
			StartActivity(p.OpenBrowser("http://www.htetznaing.com"))
		Case 9 :
			StartActivity(About)
	End Select
End Sub

Sub menu_Click
	If sm.isVisible Then sm.Hide Else sm.Show
End Sub

Sub share_Click
	Dim ShareIt As Intent
	copy.clrText
	copy.setText("#Myanmar_Jojar_Font App! Beautiful Myanmar Zawgyi Font Style!	You can Use in Samung, Oppo,Vivo, Huawei (EMUI) and Xiaomi (MIUI) without Root Access!!!! Download Free at : http://bit.ly/2mqSEWy")
	ShareIt.Initialize (ShareIt.ACTION_SEND,"")
	ShareIt.SetType ("text/plain")
	ShareIt.PutExtra ("android.intent.extra.TEXT",copy.getText)
	ShareIt.PutExtra ("android.intent.extra.SUBJECT","Get Free!!")
	ShareIt.WrapAsIntentChooser("Share App Via...")
	StartActivity (ShareIt)
End Sub