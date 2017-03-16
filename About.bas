﻿Type=Activity
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim su As StringUtils
	Dim p As PhoneIntents
	Dim lstOne As ListView
	Dim abg As BitmapDrawable
	Dim ph As Phone
	Dim Banner As AdView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	ph.SetScreenOrientation(1)

	abg.Initialize(LoadBitmap(File.DirAssets,"bg.jpg"))
	Activity.Background = abg
	Activity.Title = "About"
	
	Dim imvLogo As ImageView
	imvLogo.Initialize ("imv")
	imvLogo.Bitmap = LoadBitmap(File.DirAssets , "icon.png")
	imvLogo.Gravity = Gravity.FILL
	Activity.AddView ( imvLogo , 50%x - 50dip  , 10dip ,  100dip  ,  100dip )
	
	Dim lblName As  Label
	Dim bg As ColorDrawable
	bg.Initialize (Colors.DarkGray , 10dip)
	lblName.Initialize ("lbname")
	lblName.Background = bg
	lblName.Gravity = Gravity.CENTER
	lblName.Text = "Myanmar Jojar Font"
	lblName.TextSize = 13
	lblName.TextColor = Colors.White
	Activity.AddView (lblName , 100%x / 2 - 90dip , (imvLogo.Height+imvLogo.Top)+10dip , 180dip , 50dip)
	lblName.Height = su.MeasureMultilineTextHeight (lblName, lblName.Text ) + 5dip
	
	
	Dim c As ColorDrawable
	c.Initialize (Colors.White , 10dip )
	lstOne.Initialize ("lstOnes")
	lstOne.Background = c
	lstOne.SingleLineLayout .Label.TextSize = 12
	lstOne.SingleLineLayout .Label .TextColor = Colors.DarkGray
	lstOne.SingleLineLayout .Label .Gravity = Gravity.CENTER
	lstOne.SingleLineLayout .ItemHeight = 40dip
	lstOne.AddSingleLine2("App Name : Myanmar Jojar Font",1)
	lstOne.AddSingleLine2("Version : 1.0",2)
	lstOne.AddSingleLine2 ("Font Developer : Anonymous   ", 3)
	lstOne.AddSingleLine2 ("Developed By : Myanmar Android App   ", 4)
	lstOne.AddSingleLine2("Powered By : Myanmar Android App",5)
	lstOne.AddSingleLine2 ("Website : www.MyanmarAndroidApp.com  ", 6)
	lstOne.AddSingleLine2 ("Facebook : www.fb.com/MmFreeAndroidApps   ", 7)
	Activity.AddView ( lstOne, 30dip , 170dip , 100%x -  60dip, 280dip)
	
	Dim lblCredit As Label
	lblCredit.Initialize ("lblCredit")
	lblCredit.TextColor = Colors.Green
	lblCredit.TextSize = 13
	lblCredit.Gravity = Gravity.CENTER
	lblCredit.Text = "If You have any Problem You can contact Me."
	Activity.AddView (lblCredit, 10dip,(lstOne.Top+lstOne.Height)+3%y, 100%x - 20dip, 50dip)
	lblCredit.Height = su.MeasureMultilineTextHeight (lblCredit, lblCredit.Text )

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
End Sub

Sub imv_Click
	StartActivity(p.OpenBrowser("http://www.myanmarandroidapp.com/search?q=Myanmar+Jojar+Font"))
End Sub

Sub lbname_Click
	StartActivity(p.OpenBrowser("http://www.MyanmarAndroidApp.com"))
End Sub

Sub lblCredit_Click
	Try
 
		Dim Facebook As Intent
 
		Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
		StartActivity(Facebook)
 
	Catch
 
		Dim i As Intent
		i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
 
		StartActivity(i)
 
	End Try
End Sub
Sub Activity_Resume
     
End Sub

Sub Activity_Pause (UserClosed As Boolean)
     
End Sub

Sub lstOnes_ItemClick (Position As Int, Value As Object)
	Select Value
		Case 1
			Try
 
				Dim Facebook As Intent
 
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
				StartActivity(Facebook)
 
			Catch
 
				Dim i As Intent
				i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
 
				StartActivity(i)
 
			End Try
			
		Case 3
			Try
 
				Dim Facebook As Intent
 
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
				StartActivity(Facebook)
 
			Catch
 
				Dim i As Intent
				i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
 
				StartActivity(i)
 
			End Try
		Case 4
			Try
 
				Dim Facebook As Intent
 
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
				StartActivity(Facebook)
 
			Catch
 
				Dim i As Intent
				i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
 
				StartActivity(i)
 
			End Try
		Case 1
			Try
 
				Dim Facebook As Intent
 
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
				StartActivity(Facebook)
 
			Catch
 
				Dim i As Intent
				i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
 
				StartActivity(i)
 
			End Try
		Case 5
			Try
 
				Dim Facebook As Intent
 
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
				StartActivity(Facebook)
 
			Catch
 
				Dim i As Intent
				i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
 
				StartActivity(i)
			End Try
			
		Case 6
			StartActivity(p.OpenBrowser("http://www.MyanmarAndroidApp.com"))
		Case 7
			Try
 
				Dim Facebook As Intent
 
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
				StartActivity(Facebook)
 
			Catch
 
				Dim i As Intent
				i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
 
				StartActivity(i)
			End Try
	End Select
End Sub