package com.example.warehpusecaller

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.warehpusecaller.fragment.Attempted
import com.example.warehpusecaller.fragment.Fresh
import com.example.warehpusecaller.model.AfterSignInBody
import com.example.warehpusecaller.model.GetOrdersPojo
import com.example.warehpusecaller.model.OrderDetailsPojo
import com.example.warehpusecaller.retrofit.APIInterface
import com.example.warehpusecaller.retrofit.RetrofitInstance
import com.example.warehpusecaller.utils.FileUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    /*
    private var orderDetailsList: ArrayList<OrderDetailsPojo> = ArrayList()*/

    private lateinit var fileUtils: FileUtils

    private val fragmentManager = supportFragmentManager
    private val freshFragment = Fresh()
    private val attemptedFragment = Attempted()

    private var doubleBackToExitPressedOnce = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        fileUtils = FileUtils()
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if(fileUtils.checkInternet(this)) {
            fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, freshFragment)
                .addToBackStack(null).commit()
        } else
            fileUtils.showOfflineDialog(this)

    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fresh -> {

                    if(fileUtils.checkInternet(this)) {
                        fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, freshFragment)//.commit()
                            .addToBackStack(null).commit()
                    } else
                        fileUtils.showOfflineDialog(this)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.attempted -> {

                    if(fileUtils.checkInternet(this)) {
                        fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, attemptedFragment)//.commit()
                            .addToBackStack(null).commit()
                    } else
                        fileUtils.showOfflineDialog(this)

                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)

    }

}
