package iitu.kz.attendancechecker.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.HORIZONTAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import iitu.kz.attendancechecker.R
import iitu.kz.attendancechecker.model.Attendance
import iitu.kz.attendancechecker.model.ResponseData
import iitu.kz.attendancechecker.model.User
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendanceActivity : BaseActivity() {

	private lateinit var recyclerView: RecyclerView
	private lateinit var viewAdapter: RecyclerView.Adapter<*>
	private lateinit var viewManager: RecyclerView.LayoutManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_attendance)

		viewManager = LinearLayoutManager(this@AttendanceActivity)
		recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
			setHasFixedSize(true)
			layoutManager = viewManager
			val divider = DividerItemDecoration(this.context, HORIZONTAL)
			val drawable = ContextCompat.getDrawable(this.context, R.drawable.ic_border)
			drawable?.let { divider.setDrawable(it) }
			addItemDecoration(divider)
		}

		getUserInfo()
		getData()
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
			if (resultCode == Activity.RESULT_OK) {
				val contents = data!!.getStringExtra("SCAN_RESULT")
				val lessonId = try {
					Integer.parseInt(contents)
				} catch (e: Exception) {
					SweetAlertDialog(this@AttendanceActivity)
						.setTitleText("Invalid QR code!")
						.show()
					return
				}
				val sessionId = Paper.book().read<String>("sessionId")
				val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
				pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
				pDialog.titleText = "Loading"
				pDialog.setCancelable(false)
				pDialog.show()
				attentService.checkAttendance(sessionId, lessonId).enqueue(object : Callback<ResponseData> {
					override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
						pDialog.hide()
						if (response.body()?.success == true) {
							SweetAlertDialog(this@AttendanceActivity)
								.setTitleText("Successfully checked!")
								.show()
							getData()
						} else {
							SweetAlertDialog(this@AttendanceActivity)
								.setTitleText("Something went wrong!")
								.show()
						}
					}
					override fun onFailure(call: Call<ResponseData>, t: Throwable) {
						pDialog.hide()
						SweetAlertDialog(this@AttendanceActivity)
							.setTitleText("Error occured!")
							.show()
					}
				})
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				SweetAlertDialog(this@AttendanceActivity)
					.setTitleText("Error occured!")
					.show()
			}
		}
	}

	private fun getUserInfo() {
		val textView = findViewById<TextView>(R.id.greeting)
		val sessionId = Paper.book().read<String>("sessionId")
		attentService.getUserData(sessionId).enqueue(object : Callback<User> {
			override fun onResponse(call: Call<User>, response: Response<User>) {
				textView.text = "${response.body()?.firstName} ${response.body()?.lastName}"
			}

			override fun onFailure(call: Call<User>, t: Throwable) {
				t.printStackTrace()
				SweetAlertDialog(this@AttendanceActivity)
					.setTitleText(t.message)
					.show()
			}
		})
	}

	private fun getData() {
		val sessionId = Paper.book().read<String>("sessionId")
		attentService.getAttendances(sessionId).enqueue(object : Callback<List<Attendance>> {
			override fun onResponse(call: Call<List<Attendance>>, response: Response<List<Attendance>>) {
				viewAdapter = AttendanceListAdapter(response.body() ?: emptyList())
				recyclerView.adapter = viewAdapter
			}
			override fun onFailure(call: Call<List<Attendance>>, t: Throwable) {
				SweetAlertDialog(this@AttendanceActivity)
					.setTitleText("Error occured")
					.show()
			}
		})
	}
}

class AttendanceListAdapter(
	private val attendances: List<Attendance>
) : RecyclerView.Adapter<AttendanceListAdapter.AttendanceListViewHolder>() {

	class AttendanceListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val textView = view.findViewById<TextView>(R.id.attendance_name)
		val imageView = view.findViewById<ImageView>(R.id.attendance_check)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceListAdapter.AttendanceListViewHolder {
		val view: View = LayoutInflater.from(parent.context).inflate(R.layout.content_attendance, parent, false)
		return AttendanceListViewHolder(view)
	}

	override fun onBindViewHolder(holder: AttendanceListViewHolder, position: Int) {
		holder.textView.text = attendances[position].lessonName + "\n" + attendances[position].teacherName
		if(attendances[position].attended) {
			holder.imageView.setBackgroundResource(R.drawable.ic_check_box_active_42dp)
		} else {
			holder.imageView.setBackgroundResource(R.drawable.ic_check_box_outline_blank_42dp)
		}
	}

	override fun getItemCount() = attendances.size
}