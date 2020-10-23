package com.example.warehpusecaller

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.text.TextUtils
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.warehpusecaller.model.OrderDetailsPojo
import com.example.warehpusecaller.model.SubmitBody
import com.example.warehpusecaller.model.SubmitPojo
import com.example.warehpusecaller.retrofit.APIInterface
import com.example.warehpusecaller.retrofit.RetrofitInstance
import com.example.warehpusecaller.utils.FileUtils
import kotlinx.android.synthetic.main.activity_call.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream


class Call_Activity : AppCompatActivity() {

    lateinit var LoanproposalID: String
    lateinit var CustomerName: String
    lateinit var SpouseName: String
    lateinit var Address: String
    lateinit var Village: String
    lateinit var District: String
    lateinit var MobileNo: String
    lateinit var AlternateMobileNo: String
    private var SkuCode: String = ""
    private var Model: String = ""
    private var Flag: String = ""
    private var Count: String = ""
    var myAudioRecorder: MediaRecorder? = null
    var mediaPlayer: MediaPlayer? = null
    private var AUDIO_FILEPATH = ""
    private var AUDIO_FILENAME = ""
    lateinit var AUDIO_FILETOSUBMIT: File
    private lateinit var fileUtils: FileUtils
    private var encodedAudio: String = ""
    private var callerId: String? = ""
    private var stateId: String? = ""
    private var wareHouseId: String? = ""
    private var callStatus: String = ""


    private lateinit var orderDetailsPojo: OrderDetailsPojo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        orderDetailsPojo = intent.getSerializableExtra("orderDetailsPojo") as OrderDetailsPojo
        callerId = intent.getStringExtra("callerId")
        stateId = intent.getStringExtra("stateId")
        wareHouseId = intent.getStringExtra("wareHouseId")


        LoanproposalID = orderDetailsPojo.getLoanproposalID()
        CustomerName = orderDetailsPojo.getCustomerName()
        SpouseName = orderDetailsPojo.getSpouseName()
        Address = orderDetailsPojo.getAddress()
        Village = orderDetailsPojo.getVillage()
        District = orderDetailsPojo.getDistrict()
        MobileNo = orderDetailsPojo.getMobileNo()
        AlternateMobileNo = orderDetailsPojo.getAlternateMobileNo()
        SkuCode = orderDetailsPojo.getSkuCode()
        Model = orderDetailsPojo.getModel()
        Flag = orderDetailsPojo.getFlag()
        Count = orderDetailsPojo.getCount()
        fileUtils = FileUtils()

        tv_lid.text = LoanproposalID
        tv_customer_name.text = CustomerName
        tv_spouseName.text = SpouseName
        tv_address.text = Address
        tv_village.text = Village
        tv_district.text = District
        contact_number.text = MobileNo
        alternate_number.text = AlternateMobileNo
        tv_skuCode.text = SkuCode
        model_name.text = Model

        mediaPlayer = MediaPlayer()
        myAudioRecorder = MediaRecorder()
        myAudioRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        myAudioRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        myAudioRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)


        call1.setOnClickListener {
            setCallingInterface()
        }
        call2.setOnClickListener {
            setCallingInterface()
        }
        btn_stop.setOnClickListener {
            stopRecording()
        }
        btn_play_pause.setOnClickListener {
            playPauseRecording()
        }
        btn_cancel.setOnClickListener {
            mediaPlayer!!.pause()
            simpleChronometer.stop()
            ll_calling_interface.visibility = View.GONE
            Toast.makeText(this@Call_Activity, "Call Again !!", Toast.LENGTH_SHORT).show()
            AUDIO_FILETOSUBMIT.delete()
        }
        btn_submit.setOnClickListener {
            loading.visibility = View.VISIBLE
            if (fileUtils.checkInternet(this)) {
                encodedAudio =
                    encodeAudioToBase64Binary(File(AUDIO_FILEPATH, AUDIO_FILENAME)).toString()
                submitData();
            } else {
                fileUtils.showOfflineDialog(this)
                loading.visibility = View.GONE
            }

        }

        sp_select_call_status.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val item: String = sp_select_call_status.selectedItem.toString().trim { it <= ' ' }
                callStatus = i.toString()
                if(i==0)
                {
                    btn_submit.isEnabled = false
                    btn_submit?.isClickable = false
                    btn_submit?.setBackgroundColor(
                        ContextCompat.getColor(
                            this@Call_Activity,
                            R.color.light_grey_color
                        )
                    )
                }
                else
                {
                    btn_submit.isEnabled = true
                    btn_submit?.isClickable = true
                    btn_submit?.setBackgroundColor(
                        ContextCompat.getColor(
                            this@Call_Activity,
                            R.color.colorAccent
                        )
                    )
                }

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun setCallingInterface() {
        ll_calling_interface.visibility = View.VISIBLE
        tv_call_recorded.visibility = View.GONE
        ll_recording.visibility = View.VISIBLE
        btn_play_pause?.isEnabled = false
        btn_play_pause?.isClickable = false
        btn_play_pause?.setTextColor(
            ContextCompat.getColor(
                this@Call_Activity,
                R.color.light_grey_color
            )
        )
        btn_cancel?.isEnabled = false
        btn_cancel?.isClickable = false
        btn_cancel?.setTextColor(
            ContextCompat.getColor(
                this@Call_Activity,
                R.color.light_grey_color
            )
        )
        btn_submit.isEnabled = false
        btn_submit?.isClickable = false
        btn_submit?.setBackgroundColor(
            ContextCompat.getColor(
                this@Call_Activity,
                R.color.light_grey_color
            )
        )
        btn_stop?.isEnabled = true
        btn_stop?.isClickable = true
        btn_stop?.setTextColor(
            ContextCompat.getColor(
                this@Call_Activity,
                R.color.colorAccent
            )
        )
        sp_select_call_status.isEnabled = false

        startRecording()
    }

    private fun startRecording() {
        simpleChronometer.base = SystemClock.elapsedRealtime()
        simpleChronometer.start()

        AUDIO_FILEPATH = Environment.getExternalStorageDirectory().path + File.separator +
                "warehouse call recordings" + File.separator
        AUDIO_FILENAME = LoanproposalID + CustomerName
            .replace("-", "")
            .replace(":", "")
            .replace(" ", "").toString() + ".mp3"

        AUDIO_FILETOSUBMIT = File(AUDIO_FILEPATH, AUDIO_FILENAME)
        val audioPath = File(AUDIO_FILEPATH)

        if (audioPath.exists())
            audioPath.delete()
        else
            audioPath.mkdirs()


        myAudioRecorder?.setOutputFile(AUDIO_FILEPATH + AUDIO_FILENAME)

        try {
            myAudioRecorder?.prepare()
            myAudioRecorder?.start()
            startCalling();

        } catch (e: IOException) {
            Toast.makeText(
                this@Call_Activity,
                "Mediarecorder isn't able to prepare !! Try contact Developer",
                Toast.LENGTH_LONG
            ).show()
            mediaPlayer!!.pause()
            simpleChronometer.stop()
            ll_calling_interface.visibility = View.GONE
            AUDIO_FILETOSUBMIT.delete()
        }
    }

    private fun stopRecording() {
        myAudioRecorder?.stop()
        myAudioRecorder?.release()
        myAudioRecorder = null

        simpleChronometer.stop()

        try {
            mediaPlayer!!.setDataSource(AUDIO_FILEPATH + AUDIO_FILENAME)
            mediaPlayer!!.prepare()
            mediaPlayer!!.setVolume(10f, 10f)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        setStopUI()
    }

    private fun playPauseRecording() {
        simpleChronometer.stop()

        if (mediaPlayer!!.isPlaying) {
            btn_play_pause.text = getString(R.string.play)
            mediaPlayer!!.pause()
        } else {
            btn_play_pause.text = getString(R.string.pause)
            mediaPlayer!!.start()
            mediaPlayer!!.setVolume(10f, 10f)
        }
    }

    private fun startCalling() {

        if (!TextUtils.isEmpty(MobileNo))
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$MobileNo")))
        else
            Toast.makeText(this@Call_Activity, "Phone Number is not in Service", Toast.LENGTH_SHORT)
                .show()

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    while (true) {
                        sleep(1000)
                        audioManager.mode = AudioManager.MODE_IN_CALL
                        if (!audioManager.isSpeakerphoneOn) audioManager.isSpeakerphoneOn = true
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()

    }

    private fun setStopUI() {
        callStatus = "0"
        ll_calling_interface.visibility = View.VISIBLE
        tv_call_recorded.visibility = View.VISIBLE
        ll_recording.visibility = View.GONE

        btn_play_pause?.isEnabled = true
        btn_play_pause?.isClickable = true
        btn_play_pause?.setTextColor(
            ContextCompat.getColor(
                this@Call_Activity,
                R.color.colorAccent
            )
        )
        btn_cancel?.isEnabled = true
        btn_cancel?.isClickable = true
        btn_cancel?.setTextColor(
            ContextCompat.getColor(
                this@Call_Activity,
                R.color.colorAccent
            )
        )
        btn_submit.isEnabled = false
        btn_submit?.isClickable = false
        btn_submit?.setBackgroundColor(
            ContextCompat.getColor(
                this@Call_Activity,
                R.color.light_grey_color
            )
        )
        btn_stop?.isEnabled = false
        btn_stop?.isClickable = false
        btn_stop?.setTextColor(
            ContextCompat.getColor(
                this@Call_Activity,
                R.color.light_grey_color
            )
        )

        sp_select_call_status.isEnabled = true
    }

    @Throws(IOException::class)
    private fun encodeAudioToBase64Binary(file: File): String? {
        val file1 = File(file.toString())
        val bytes: ByteArray = loadFile(file1)!!
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    @Throws(IOException::class)
    private fun loadFile(file: File): ByteArray? {
        val `is`: InputStream = FileInputStream(file)
        val length = file.length()
        if (length > Int.MAX_VALUE) {
            // File is too large
            Toast.makeText(this@Call_Activity, "File is too large", Toast.LENGTH_SHORT).show()
        }
        val bytes = ByteArray(length.toInt())
        var offset = 0
        var numRead = 0
        while (offset < bytes.size
            && `is`.read(bytes, offset, bytes.size - offset).also { numRead = it } >= 0
        ) {
            offset += numRead
        }
        if (offset < bytes.size) {
            throw IOException("Could not completely read file " + file.name)
        }
        `is`.close()
        return bytes
    }

    private fun submitData() {
        if (encodedAudio == "") {
            Toast.makeText(
                this@Call_Activity,
                "Call not recorded properly, Retry !!",
                Toast.LENGTH_LONG
            ).show()
            ll_calling_interface.visibility = View.GONE
        } else {
            val retIn = RetrofitInstance.getRetrofitInstance().create(APIInterface::class.java)
            val submitData = SubmitBody(
                callerId.toString(), wareHouseId.toString(), stateId.toString(),
                LoanproposalID, encodedAudio, callStatus
            )

            retIn.submit(submitData).enqueue(object : Callback<SubmitPojo> {

                override fun onFailure(call: Call<SubmitPojo>, t: Throwable) {
                    loading.visibility = View.GONE
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<SubmitPojo>, response: Response<SubmitPojo>) {

                    if (response.isSuccessful) {
                        if (response.body()?.getStatus().equals("1")) {

                            loading.visibility = View.GONE

                            AUDIO_FILETOSUBMIT.delete()
                            Toast.makeText(this@Call_Activity, "Record Submitted Successfully !!", Toast.LENGTH_LONG).show()
                            ll_calling_interface.visibility = View.GONE
                            onBackPressed()

                        } else {

                            loading.visibility = View.GONE
                            val remarks = response.body()?.getRemarks().toString()
                            fileUtils.showWarningDialog(this@Call_Activity, remarks)
                            AUDIO_FILETOSUBMIT.delete()
                            ll_calling_interface.visibility = View.GONE
                        }
                    }
                }

            })
        }
    }
}