package rasyidk.fa.scancode.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetworkInterface {

    @POST("auth/signin")
    @FormUrlEncoded
    fun loginRequest(@Field("name") name: String,
                     @Field("password") password: String): Call<ResponseBody>
}