package com.example.warehpusecaller.retrofit

import com.example.warehpusecaller.model.AfterSignInBody
import com.example.warehpusecaller.model.GetOrdersPojo
import com.example.warehpusecaller.model.SignInBody
import com.example.warehpusecaller.model.SignInPojo
import com.example.warehpusecaller.model.*
import retrofit2.http.*

interface APIInterface {

    @Headers("Content-Type:application/json")
    @POST("warehouse_login_api.php")
    fun signIn(@Body info: SignInBody): retrofit2.Call<SignInPojo>

    @Headers("Content-Type:application/json")
    @POST("warehouse_order_api.php")
    fun getOrders(@Body info: AfterSignInBody): retrofit2.Call<GetOrdersPojo>

    @Headers("Content-Type:application/json")
    @POST("warehouse_calling_api.php")
    fun submit(@Body info: SubmitBody): retrofit2.Call<SubmitPojo>

}

