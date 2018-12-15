package rasyidk.fa.scancode.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import rasyidk.fa.scancode.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface NetworkInterface {

    @POST("auth/signin")
    @FormUrlEncoded
    fun loginRequest(@Field("name") name: String,
                     @Field("password") password: String): Call<ResponseBody>

    @GET("profile/{id_user}")
    fun getUser(@Path("id_user")id_user: String): Observable<UserResponse>
}