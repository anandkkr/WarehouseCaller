package com.example.warehpusecaller.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.sql.StatementEvent

class SignInPojo {
    /*@SerializedName("Type")
    @Expose
    private lateinit var type: String*/

    @SerializedName("Status")
    @Expose
    private lateinit var status: String

    @SerializedName("Remarks")
    @Expose
    private lateinit var remarks: String

    @SerializedName("OTP")
    @Expose
    private lateinit var otp: String

    @SerializedName("StateID")
    @Expose
    private lateinit var stateID: String

    @SerializedName("WarehouseID")
    @Expose
    private lateinit var warehouseID: String

    @SerializedName("CallerID")
    @Expose
    private lateinit var callerID: String

    /*public fun getType(): String {
        return type
    }*/

    public fun getRemarks(): String {
        return remarks
    }

    public fun getStatus(): String {
        return status
    }

    public fun getOtp(): String {
        return otp
    }

    public fun getWarehouseID(): String {
        return warehouseID
    }

    public fun getCallerID(): String {
        return callerID
    }

    public fun getStateID(): String {
        return stateID
    }
}