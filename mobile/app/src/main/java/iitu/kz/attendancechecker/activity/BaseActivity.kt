package iitu.kz.attendancechecker.activity

import android.support.v7.app.AppCompatActivity
import iitu.kz.attendancechecker.service.AttentService
import iitu.kz.attendancechecker.service.NetworkService
import retrofit2.create

abstract class BaseActivity : AppCompatActivity() {
	val attentService = NetworkService.retrofit.create<AttentService>()
}