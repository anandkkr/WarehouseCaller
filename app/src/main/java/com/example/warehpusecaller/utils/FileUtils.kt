package com.example.warehpusecaller.utils

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import com.example.warehpusecaller.R

class FileUtils {

    var userId: String = "userID"
    var name: String = "name"
    var mobile: String = "mobile"
    var email: String = "email"
    var gender: String = "gender"
    var dob: String = "dob"
    var pincode: String = "pincode"
    var state: String = "state"
    var district: String = "district"
    var address: String = "address"
    var userImage: String = "userImage"
    var latitude: String = "latitude"
    var longitude: String = "longitude"
    var cartList: String = "cartList"
    var wishlistList: String = "wishlistList"

    var stateId: String = "stateId"
    var wareHouseId: String = "wareHouseId"
    var callerId: String = "callerId"
//    var type: String = "type"

    var intentFor: String = "intentFor"
    var intentFrom: String = "intentFrom"

    var returnValue: Boolean = false
    var classTag: String = "FileUtilsokhttp"

    var APP_VERSION = "1"

    var exploreButtomClicked: Boolean = false
    var isUserRegistered: String = "isUserRegistered"

    val mobile_category = arrayOf("PRICE (Rs)", "RAM (GB)", "CAMERA (MP)", "BATTERY (mAh)")
    val shoes_category = arrayOf("PRICE (Rs)", "SIZE")
    val all_category = arrayOf("PRICE (Rs)", "SIZE", "TYPE")
    val all_price = arrayOf("100-499", "500-999", "1000-1999", "2000-2999", "3000-5999", "6000-8999", "9000-12999",
        "13000-16999", "17000-21999", "22000-26999")
    val all_ram = arrayOf("1", "2", "3", "4", "5", "6")
    val all_camera = arrayOf("8", "12", "16", "24", "32", "48")
    val all_battery = arrayOf("1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000")
    val all_size_cloth = arrayOf("L", "M", "S")
    val all_size_shoes = arrayOf("6", "7", "8", "9", "10", "11", "12")
    val all_size_items = arrayOf("Free", "Variable")
    val cloths_type = arrayOf("Jacket", "Leather", "LUXARAZI", "T-shirts")

//    val all_category = arrayOf("Price", "RAM", "Camera", "Battery")

    fun showOfflineDialog(context: Context): Boolean {
        val dialog = Dialog(
            context,
            R.style.DialogTheme
        )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.z_layout_offline)
        val tryAgain = dialog.findViewById(R.id.btn_retry) as Button
        tryAgain.setOnClickListener {
            dialog.dismiss()
            returnValue = true
        }
        dialog.show()

        return returnValue
    }



    fun showMaintenanceDialog(context: Context): Boolean {
        val dialog = Dialog(
            context,
            R.style.DialogTheme
        )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.z_layout_maintenance)
        val tryAgain = dialog.findViewById(R.id.btn_retry) as Button
        tryAgain.setOnClickListener {
            dialog.dismiss()
            returnValue = true
        }
        dialog.show()
        return returnValue
    }

    fun showWarningDialog(context: Context, warning: String) {
        val dialog = Dialog(
            context,
            R.style.DialogTheme
        )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.z_layout_warning)
        val tryAgain = dialog.findViewById(R.id.btn_retry) as Button
        val warningText = dialog.findViewById(R.id.text) as TextView
        tryAgain.setOnClickListener {
            dialog.dismiss()
            returnValue = true
        }
        warningText.text = warning
        dialog.show()
    }

    fun checkInternet(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        Log.d(classTag, "hasNetworkAvailable: ${(network != null)}")
        return (network != null)
    }

    /*fun setRoundGlideImage(context: Context, imageView: ImageView, url: Uri?): Unit {
        val into: BitmapImageViewTarget = GlideApp.with(context)
            .asBitmap()
            .load( *//*ApiClient.API_BASE_URL_IMAGE + *//*url)
            .centerCrop()
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_user)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }

    fun setRoundGlideImageString(context: Context, imageView: ImageView, url: String?): Unit {
        val into: BitmapImageViewTarget = GlideApp.with(context)
            .asBitmap()
            .load( *//*ApiClient.API_BASE_URL_IMAGE + *//*url)
            .centerCrop()
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_user)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }

    fun setRoundGlideImageCategory(
        context: Context,
        imageView: ImageView,
        url: String?,
        errorDrawable: Int
    ): Unit {
        val into: BitmapImageViewTarget = GlideApp.with(context)
            .asBitmap()
            .load( /*ApiClient.API_BASE_URL_IMAGE + */url)
            .centerCrop()
            .placeholder(R.drawable.ic_loading)
            .error(errorDrawable)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.isCircular = false
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }*/

    /*fun setImageFromUrl(context: Context, imageView: ImageView, url: String, errorImage: Int): Unit {
        Picasso.get().load(url).placeholder(R.drawable.ic_loading).error(errorImage).into(imageView)
    }*/

    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
       /* var categoryList: List<Category> = ArrayList()
        var brandList: List<Brands> = ArrayList()*/
        var checkedBox: Int = 0
        var checkedId: Int = 0

        var subCategoryCheckedBox: Int = 0
        var cat_id_forFilter : String = ""

        var view : View? = null

        var arrayListFilteredTerms: ArrayList<HashMap<String, String>> = ArrayList()
        var arrayListDeletedTerms: ArrayList<HashMap<String, String>> = ArrayList()
    }

}

