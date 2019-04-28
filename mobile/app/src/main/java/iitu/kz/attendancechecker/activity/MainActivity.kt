package iitu.kz.attendancechecker.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import iitu.kz.attendancechecker.R
import iitu.kz.attendancechecker.model.LoginRequest
import iitu.kz.attendancechecker.model.ResponseData
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	fun login(view: View) {
		val login = findViewById<EditText>(R.id.login).text.toString()
		val password = findViewById<EditText>(R.id.password).text.toString()
		val loginRequest = LoginRequest(login, password)
		val errorTextView = findViewById<TextView>(R.id.error)

		val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
		pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
		pDialog.titleText = "Loading"
		pDialog.setCancelable(false)
		pDialog.show()

		attentService.login(loginRequest).enqueue(object : Callback<ResponseData> {
			override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
				when (response.body()?.success) {
					true -> {
						val responseCookie = response.raw().header("Set-Cookie")
						val sessionId = responseCookie?.split("; ")?.get(0)
						Paper.book().write("sessionId", sessionId)
						openWelcomeActivity()
					}
					else -> {
						pDialog.hide()
						SweetAlertDialog(this@MainActivity)
							.setTitleText("Incorrect login or password")
							.show()
					}

				}
			}

			override fun onFailure(call: Call<ResponseData>, t: Throwable) {
				pDialog.hide()
				SweetAlertDialog(this@MainActivity)
					.setTitleText("Error: ${t.message}")
					.show()
			}
		})
	}

	fun openWelcomeActivity() {
		val intent = Intent(this, WelcomeActivity::class.java)
		startActivity(intent)
	}
}
