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
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim wb As WebView
	Dim wv As WebViewSettings
	Dim Banner As AdView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	wb.Initialize("wb")
	Activity.AddView(wb,0%x,0%y,100%x,100%y)
	wb.LoadUrl("http://www.myanmarandroidapp.com/2017/02/myanmar-font-styles.html")
	wv.setDisplayZoomControls(wb , False)
	
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
	
Activity.AddMenuItem("Open in Browser","lb")
End Sub

Sub lb_Click
	Dim p As PhoneIntents
	StartActivity(p.OpenBrowser("http://www.myanmarandroidapp.com/2017/02/myanmar-font-styles.html"))
End Sub
Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub