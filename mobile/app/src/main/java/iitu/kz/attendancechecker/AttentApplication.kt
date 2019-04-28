package iitu.kz.attendancechecker

import android.app.Application
import io.paperdb.Paper

class AttentApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		Paper.init(this)
	}
}