package com.example.warehpusecaller.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.sql.StatementEvent

class GetOrdersPojo {

    @SerializedName("Status")
    @Expose
    private lateinit var status: String

    @SerializedName("Remarks")
    @Expose
    private lateinit var remarks: String

    @SerializedName("OrderDetails")
    @Expose
    private lateinit var orderDetails: ArrayList<OrderDetailsPojo>

    public fun getRemarks(): String {
        return remarks
    }

    public fun getStatus(): String {
        return status
    }

    fun getOrderDetails(): ArrayList<OrderDetailsPojo> {
        return orderDetails
    }

}