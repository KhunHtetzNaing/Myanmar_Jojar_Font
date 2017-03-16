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
	Dim ad2 As Timer
End Sub

Sub Globals
	Dim ph As Phone
	Dim b1,b2,b3,b4,b5,b6 As Button
	Dim Banner As AdView
	Dim Interstitial As InterstitialAd
End Sub

Sub Activity_Create(FirstTime As Boolean)
	ph.SetScreenOrientation(1)
Activity.Title = "Choose Your Phone!"
	b1.Initialize("b1")
		b1.Text = "Samsung"
	b1.Gravity = Gravity.CENTER
	Activity.AddView(b1,20%x,10%y,60%x,10%y)

	b2.Initialize("b2")
	b2.Text = "Oppo"
	b2.Gravity = Gravity.CENTER
	Activity.AddView(b2,20%x,(b1.Top+b1.Height)+1%y,60%x,10%y)

	b3.Initialize("b3")
	b3.Text = "Vivo"
	b3.Gravity = Gravity.CENTER
	Activity.AddView(b3,20%x,(b2.Top+b2.Height)+1%y,60%x,10%y)

	b4.Initialize("b4")
	b4.Text = "Huawei"
	b4.Gravity = Gravity.CENTER 
	Activity.AddView(b4,20%x,(b3.Top+b3.Height)+1%y,60%x,10%y)

	b5.Initialize("b5")
	b5.Text = "Xiaomi"
	b5.Gravity = Gravity.CENTER
	Activity.AddView(b5,20%x,(b4.Top+b4.Height)+1%y,60%x,10%y)

	b6.Initialize("b6")
	b6.Text = "Other [#Root]"
	b6.Gravity = Gravity.CENTER
	Activity.AddView(b6,20%x,(b5.Top+b5.Height)+1%y,60%x,10%y)
	
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
		
	ad2.Initialize("ad2",60000)
	ad2.Enabled = True
End Sub

Sub b1_Click
	StartActivity(Samsung)
End Sub

Sub b2_Click
	StartActivity(Oppo)
End Sub

Sub b3_Click
	StartActivity(Vivo)
End Sub

Sub b4_Click
	StartActivity(Huawei)
End Sub

Sub b5_Click
	StartActivity(Xiaomi)
End Sub

Sub b6_Click
	StartActivity(Other)
End Sub

Sub Activity_Resume

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