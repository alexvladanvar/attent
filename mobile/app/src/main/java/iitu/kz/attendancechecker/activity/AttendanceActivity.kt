package iitu.kz.attendancechecker.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import iitu.kz.attendancechecker.R
import iitu.kz.attendancechecker.model.Attendance
import iitu.kz.attendancechecker.model.User
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendanceActivity : BaseActivity() {

	private lateinit var recyclerView: RecyclerView
	private lateinit var viewAdapter: RecyclerView.Adapter<*>
	private lateinit var viewManager: RecyclerView.LayoutManager

	private val data = listOf(
		Attendance(true, 1, "Managing technical people", "Abdulanova A.K.", "CSSE-1601"),
		Attendance(false, 2, "System level programming", "Rakhimzhanova N.K.", "CSSE-1601"),
		Attendance(true, 3, "Fundamentals of Information Security", "Sagymbekova A.", "CSSE-1601"),
		Attendance(true, 4, "Web-technologies", "Mukazhanov N.K.", "CSSE-1601")
	)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_attendance)
		getData()

		viewManager = LinearLayoutManager(this)
		viewAdapter = AttendanceListAdapter(data)

		recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
			setHasFixedSize(true)
			layoutManager = viewManager
			adapter = viewAdapter
		}
	}

	fun openScan(view: View) {
		try {
			val intent = Intent("com.google.zxing.client.android.SCAN")
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE")
			startActivityForResult(intent, 0)
		} catch (e: Exception) {
			val marketUri = Uri.parse("market://details?id=com.google.zxing.client.android")
			val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
			startActivity(marketIntent)
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == 0) {
//			val textView = findViewById<TextView>(R.id.result)
			if (resultCode == Activity.RESULT_OK) {
				val contents = data!!.getStringExtra("SCAN_RESULT")
				SweetAlertDialog(this@AttendanceActivity)
					.setTitleText("Data: $contents")
					.show()
//				textView.text = contents
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				SweetAlertDialog(this@AttendanceActivity)
					.setTitleText("Error occured")
					.show()
//				textView.text = "Error"
			}
		}
	}

	private fun getData() {
		val textView = findViewById<TextView>(R.id.greeting)
		val sessionId = Paper.book().read<String>("sessionId")
		attentService.userInfo(sessionId).enqueue(object : Callback<User> {
			override fun onResponse(call: Call<User>, response: Response<User>) {
				println(response.body())
				textView.text = "Welcome, ${response.body()?.login}!"
			}

			override fun onFailure(call: Call<User>, t: Throwable) {
				t.printStackTrace()
				println(t.message)
				textView.text = t.message
			}
		})
	}
}

class AttendanceListAdapter(
	private val attendances: List<Attendance>
) : RecyclerView.Adapter<AttendanceListAdapter.AttendanceListViewHolder>() {

	class AttendanceListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val textView = view.findViewById<TextView>(R.id.attendance_name)
		val isAttended = view.findViewById<CheckBox>(R.id.attendance_checkbox)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceListAdapter.AttendanceListViewHolder {
		val view: View = LayoutInflater.from(parent.context).inflate(R.layout.content_attendance, parent, false)
		return AttendanceListViewHolder(view)
	}

	override fun onBindViewHolder(holder: AttendanceListViewHolder, position: Int) {
		holder.textView.text = attendances[position].lessonName + "\n" + attendances[position].teacherName
		holder.isAttended.isChecked = attendances[position].attended
	}

	override fun getItemCount() = attendances.size
}