package com.example.warehpusecaller.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class OrderDetailsPojo : Serializable {
    @SerializedName("LoanproposalID")
    @Expose
    private val LoanproposalID: String? = null

    @SerializedName("CustomerName")
    @Expose
    private val CustomerName: String? = null

    @SerializedName("SpouseName")
    @Expose
    private val SpouseName: String? = null

    @SerializedName("Address")
    @Expose
    private val Address: String? = null

    @SerializedName("Village")
    @Expose
    private val Village: String? = null

    @SerializedName("District")
    @Expose
    private val District: String? = null

    @SerializedName("MobileNo")
    @Expose
    private val MobileNo: String? = null

    @SerializedName("AlternateMobileNo")
    @Expose
    private val AlternateMobileNo: String? = null

    @SerializedName("SkuCode")
    @Expose
    private val SkuCode: String? = null

    @SerializedName("Model")
    @Expose
    private val Model: String? = null

    @SerializedName("Flag")
    @Expose
    private val Flag: String? = null

    @SerializedName("Count")
    @Expose
    private val Count: String? = null

    fun getLoanproposalID(): String {
        return LoanproposalID.toString()
    }

    fun getCustomerName(): String {
        return CustomerName.toString()
    }

    fun getSpouseName(): String {
        return SpouseName.toString()
    }

    fun getAddress(): String {
        return Address.toString()
    }

    fun getVillage(): String {
        return Village.toString()
    }

    fun getDistrict(): String {
        return District.toString()
    }

    fun getMobileNo(): String {
        return MobileNo.toString()
    }

    fun getAlternateMobileNo(): String {
        return AlternateMobileNo.toString()
    }

    fun getSkuCode(): String {
        return SkuCode.toString()
    }

    fun getModel(): String {
        return Model.toString()
    }

    fun getFlag(): String {
        return Flag.toString()
    }

    fun getCount(): String {
        return Count.toString()
    }

}