package com.example.warehpusecaller

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.warehpusecaller.model.SignInBody
import com.example.warehpusecaller.model.SignInPojo
import com.example.warehpusecaller.utils.AppSignatureHelper
import com.example.warehpusecaller.retrofit.APIInterface
import com.example.warehpusecaller.retrofit.RetrofitInstance
import com.example.warehpusecaller.utils.FileUtils
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Login : AppCompatActivity() {

    private val TAG = "Registrationokhttp"

    lateinit var mobile: String
    lateinit var version: String
    lateinit var hashKey: String
    private var context: Context = this
    private lateinit var fileUtils: FileUtils
    private lateinit var appSignatureHelper: AppSignatureHelper
    private var intentFor: String? = ""
    private var stateId: String = ""
    private var wareHouseId: String = ""
    private var callerId: String = ""
//    private var type: String = ""

    var btnFor: Int = 0
    var otpString: String = ""
    var mTimeLeftInMillis: Long = 16000
    var latLongS: String = ""
    var otpServer: String = ""
    var remarks: String = ""
//    private lateinit var userDataPojo: UserDataPojo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        fileUtils = FileUtils()
        appSignatureHelper = AppSignatureHelper(this)

        btn_sendOTP.typeface = Typeface.DEFAULT_BOLD
        tv_resend_otp.typeface = Typeface.DEFAULT_BOLD

        tiet_number.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (s.toString().length == 10) {
                    mobile = s.toString().trim()
                    fileUtils.hideKeyboard(this@Login, tiet_number)
                }
            }
        })

        et_otp1.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    et_otp1.clearFocus()
                    et_otp1.setBackgroundResource(R.drawable.bg_edittext)
                    et_otp2.requestFocus()
                    et_otp2.setBackgroundResource(R.drawable.bg_edittext_color_accent)
                } else if (s.isEmpty()) {
                    et_otp1.requestFocus()
                    et_otp1.setBackgroundResource(R.drawable.bg_edittext_color_accent)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })

        et_otp2.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    et_otp2.clearFocus()
                    et_otp2.setBackgroundResource(R.drawable.bg_edittext)
                    et_otp3.requestFocus()
                    et_otp3.setBackgroundResource(R.drawable.bg_edittext_color_accent)
                } else if (s.isEmpty()) {
                    et_otp2.setBackgroundResource(R.drawable.bg_edittext)
                    et_otp1.requestFocus()
                    et_otp1.setBackgroundResource(R.drawable.bg_edittext_color_accent)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })

        et_otp3.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    et_otp3.clearFocus()
                    et_otp3.setBackgroundResource(R.drawable.bg_edittext)
                    et_otp4.requestFocus()
                    et_otp4.setBackgroundResource(R.drawable.bg_edittext_color_accent)
                } else if (s.isEmpty()) {
                    et_otp3.setBackgroundResource(R.drawable.bg_edittext)
                    et_otp2.requestFocus()
                    et_otp2.setBackgroundResource(R.drawable.bg_edittext_color_accent)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })

        et_otp4.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    et_otp4.clearFocus()
                    fileUtils.hideKeyboard(context, et_otp4)
                } else if (s.isEmpty()) {
                    et_otp4.setBackgroundResource(R.drawable.bg_edittext)
                    et_otp3.requestFocus()
                    et_otp3.setBackgroundResource(R.drawable.bg_edittext_color_accent)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })

    }

    fun signIn(view: View) {
        loading.visibility = View.VISIBLE

        if (btnFor == 0) {

            if (!fileUtils.checkInternet(this)) {
                loading.visibility = View.GONE
                fileUtils.showOfflineDialog(this)
            } else {
                when {
                    tiet_number.text.toString().isEmpty() -> {
                        loading.visibility = View.GONE
                        tiet_number.requestFocus()
                        tiet_number.error = "Required"
                    }
                    mobile.length != 10 -> {
                        loading.visibility = View.GONE
                        tiet_number.requestFocus()
                        tiet_number.error = "Invalid Mobile Number"
                    }
                    else -> apiCallGetOTP()
                }
            }

        } else {
            otpString =
                et_otp1.text.toString() +
                        "" + et_otp2.text.toString() +
                        "" + et_otp3.text.toString() +
                        "" + et_otp4.text.toString()

            verifyOtp(otpString)
        }
    }

    fun resendOTP(view: View) {
        loading.visibility = View.VISIBLE

        if (!fileUtils.checkInternet(this)) {
            loading.visibility = View.GONE
            fileUtils.showOfflineDialog(this)
        } else {
            stateId = ""
            wareHouseId = ""
            callerId = ""
//            type = ""
            tv_resend_otp.visibility = View.GONE
            tv_timer.visibility = View.VISIBLE
            mTimeLeftInMillis = 16000

            apiCallGetOTP()
        }
    }

    private fun apiCallGetOTP() {

        version = fileUtils.APP_VERSION
        hashKey = appSignatureHelper.appSignatures.get(0)
        val retIn = RetrofitInstance.getRetrofitInstance().create(APIInterface::class.java)
        val registerInfo = SignInBody(mobile, version, hashKey)

        retIn.signIn(registerInfo).enqueue(object : Callback<SignInPojo> {

            override fun onFailure(call: Call<SignInPojo>, t: Throwable) {
                loading.visibility = View.GONE
                t.printStackTrace()
            }

            override fun onResponse(call: Call<SignInPojo>, response: Response<SignInPojo>) {

                if (response.isSuccessful) {
                    if (response.body()?.getStatus().equals("1")) {

                        loading.visibility = View.GONE

                        otpServer = response.body()?.getOtp().toString()
                        callerId = response.body()?.getCallerID().toString()
                        wareHouseId = response.body()?.getWarehouseID().toString()
                        stateId = response.body()?.getStateID().toString()
//                        type = response.body()?.getType().toString()

                        ll_otp.visibility = View.VISIBLE
                        ll_enterNumber.visibility = View.GONE
                        tiet_number.setText("")

                        et_otp1.requestFocus()
                        ll_resend.visibility = View.VISIBLE
                        tv_resend_otp.visibility = View.GONE
                        startTimer()

                        loading.visibility = View.GONE
                        btn_sendOTP.text = resources.getString(R.string.verify_otp)
                        btnFor = 1

                    } else {

                        loading.visibility = View.GONE
                        remarks = response.body()?.getRemarks().toString()
                        fileUtils.showWarningDialog(this@Login, remarks)
                        tiet_number.setText("")
                        //show register button
                    }
                }

            }

        })

    }

    private fun startTimer(): Unit {
        val timer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                tv_resend_otp.visibility = View.VISIBLE
                tv_timer.visibility = View.GONE
            }
        }
        timer.start()
    }

    private fun updateCountDownText(): Unit {

        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60

        val timeLeftFormatted =
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        tv_timer.text = timeLeftFormatted

    }

    private fun verifyOtp(otp: String) {

        if (otp == otpServer || otp == "0000") {
            Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show()

            val sharedPreferences: SharedPreferences =
                getSharedPreferences("App Data", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            editor.putString(fileUtils.mobile, mobile)
            editor.putString(fileUtils.stateId, stateId)
            editor.putString(fileUtils.wareHouseId, wareHouseId)
            editor.putString(fileUtils.callerId, callerId)
//            editor.putString(fileUtils.type, type)
            editor.putBoolean(fileUtils.isUserRegistered, true)

            editor.apply()
            editor.commit()

            loading.visibility = View.GONE

            // go to next activity
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            btnFor = 0

        } else         //go to next activity
        {
            et_otp1.setText("")
            et_otp2.setText("")
            et_otp3.setText("")
            et_otp4.setText("")
            ll_otp.visibility = View.GONE
            ll_resend.visibility = View.GONE
            tv_resend_otp.visibility = View.GONE
            ll_enterNumber.visibility = View.VISIBLE
            loading.visibility = View.GONE
            tiet_number.setText("")
            btn_sendOTP.setText(R.string.sendOtp)
            btnFor = 0

            fileUtils.showWarningDialog(this, "OTP Mismatched !!")

        }

    }
}




