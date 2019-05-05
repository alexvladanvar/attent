//package iitu.kz.attendancechecker.activity
//
//import android.app.Activity
//import android.os.Bundle
//import android.view.View
//import android.widget.TextView
//import android.content.Intent
//import android.net.Uri
//import iitu.kz.attendancechecker.R
//import iitu.kz.attendancechecker.model.User
//import io.paperdb.Paper
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
//class WelcomeActivity : BaseActivity() {
//
//	override fun onCreate(savedInstanceState: Bundle?) {
//		super.onCreate(savedInstanceState)
//		setContentView(R.layout.activity_welcome)
//		getData()
//	}
//
//	fun openScan(view: View) {
//		try {
//			val intent = Intent("com.google.zxing.client.android.SCAN")
//			intent.putExtra("SCAN_MODE", "QR_CODE_MODE")
//			startActivityForResult(intent, 0)
//		} catch (e: Exception) {
//			val marketUri = Uri.parse("market://details?id=com.google.zxing.client.android")
//			val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
//			startActivity(marketIntent)
//		}
//	}
//
//	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//		super.onActivityResult(requestCode, resultCode, data)
//		if (requestCode == 0) {
//			val textView = findViewById<TextView>(R.id.result)
//			if (resultCode == Activity.RESULT_OK) {
//				val contents = data!!.getStringExtra("SCAN_RESULT")
//				textView.text = contents
//			}
//			if (resultCode == Activity.RESULT_CANCELED) {
//				textView.text = "Error"
//			}
//		}
//	}
//
//	private fun getData() {
//		val textView = findViewById<TextView>(R.id.greeting)
//		val sessionId = Paper.book().read<String>("sessionId")
//		attentService.userInfo(sessionId).enqueue(object : Callback<User> {
//			override fun onResponse(call: Call<User>, response: Response<User>) {
//				println(response.body())
//				textView.text = "Welcome, ${response.body()?.login}!"
//			}
//
//			override fun onFailure(call: Call<User>, t: Throwable) {
//				t.printStackTrace()
//				println(t.message)
//				textView.text = t.message
//			}
//		})
//	}
//}
