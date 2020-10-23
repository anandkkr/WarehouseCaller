package com.example.warehpusecaller.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.sql.StatementEvent

class SubmitPojo {
    

    @SerializedName("Status")
    @Expose
    private lateinit var status: String

    @SerializedName("Remarks")
    @Expose
    private lateinit var remarks: String

    public fun getRemarks(): String {
        return remarks
    }

    public fun getStatus(): String {
        return status
    }


}