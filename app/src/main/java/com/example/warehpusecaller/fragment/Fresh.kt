package com.example.warehpusecaller.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehpusecaller.HomeActivity
import com.example.warehpusecaller.R
import com.example.warehpusecaller.adapter.OrderDetailsAdapterRV
import com.example.warehpusecaller.model.AfterSignInBody
import com.example.warehpusecaller.model.GetOrdersPojo
import com.example.warehpusecaller.model.OrderDetailsPojo
import com.example.warehpusecaller.retrofit.APIInterface
import com.example.warehpusecaller.retrofit.RetrofitInstance
import com.example.warehpusecaller.utils.FileUtils
import kotlinx.android.synthetic.main.fragment_fresh.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fresh : Fragment() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var orderDetailsAdapterRV: OrderDetailsAdapterRV
    private var orderDetailsList: ArrayList<OrderDetailsPojo> = ArrayList()

    private var callerId: String = ""
    var mobile: String = ""
    private var stateId: String = ""
    private var wareHouseId: String = ""

    var remarks: String = ""

    private lateinit var fileUtils: FileUtils
    private lateinit var homeActivity: HomeActivity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fresh, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnTouchListener { _, _ -> return@setOnTouchListener true }

        fileUtils = FileUtils()
        homeActivity = HomeActivity()

        val sharedPreferences =
            this.requireActivity().getSharedPreferences("App Data", Context.MODE_PRIVATE)
        stateId = sharedPreferences.getString(fileUtils.stateId, "").toString()
        wareHouseId = sharedPreferences.getString(fileUtils.wareHouseId, "").toString()
        callerId = sharedPreferences.getString(fileUtils.callerId, "").toString()
        mobile = sharedPreferences.getString(fileUtils.mobile, "").toString()

        apiCallGetOrders()

    }

    private fun apiCallGetOrders() {

        val retIn = RetrofitInstance.getRetrofitInstance().create(APIInterface::class.java)
        val userInfo = AfterSignInBody(callerId, wareHouseId, stateId)

        retIn.getOrders(userInfo).enqueue(object : Callback<GetOrdersPojo> {

            override fun onFailure(call: Call<GetOrdersPojo>, t: Throwable) {
                cv_loading?.visibility = View.GONE
                activity?.let { fileUtils.showWarningDialog(it, "Contact IT team !! Error : $t") }
            }

            override fun onResponse(call: Call<GetOrdersPojo>, response: Response<GetOrdersPojo>) {

                if (response.isSuccessful) {
                    if (response.body()?.getStatus().equals("1")) {
                        cv_loading?.visibility = View.GONE
                        orderDetailsList = response.body()?.getOrderDetails()!!

                        rv_order_details?.isNestedScrollingEnabled = true
                        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        rv_order_details.layoutManager = layoutManager
                        orderDetailsAdapterRV = OrderDetailsAdapterRV(requireContext(), orderDetailsList,
                            stateId, wareHouseId, callerId, rv_order_details)
                        rv_order_details.adapter = orderDetailsAdapterRV

                    } else {
                        cv_loading?.visibility = View.GONE
                        remarks = response.body()?.getRemarks().toString()
                        activity?.let { fileUtils.showWarningDialog(it, remarks) }
                    }
                }
            }
        })
    }
}