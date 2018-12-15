package rasyidk.fa.scancode

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.SharedPreferences
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.jetbrains.anko.startActivity
import org.json.JSONException
import org.json.JSONObject
import rasyidk.fa.scancode.helper.UserSession
import rasyidk.fa.scancode.network.NetworkApi
import rasyidk.fa.scancode.network.NetworkInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class LoginActivity : AppCompatActivity() {

    private val PREFER_NAME = "Reg"
    lateinit var session: UserSession
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = UserSession(this)
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE)
        val networkApi = NetworkApi()

        btn_login.setOnClickListener {
            networkApi.getRetrofit().create(NetworkInterface::class.java)
                    .loginRequest(input_username.text.toString(), input_password.text.toString())
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.d("debug", "onFailure "+t.toString())
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
//                                Log.d("hasil", response.body().toString())
                                try {
                                    val jsonResult = JSONObject(response.body()?.string())

                                    if (jsonResult.getString("error").isEmpty()) {
                                        val key = jsonResult.getString("token")
                                        val nama = jsonResult.getString("username")
                                        val id = jsonResult.getString("id_user")
                                        Log.d("loginn", "cetak $key $nama $id")
                                        session.createUserLoginSession(key, nama, id)
                                        startActivity<MainActivity>()
                                        finish()
                                    } else {
                                        Log.d("hasil", jsonResult.getString("error"))
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    })
        }
    }

}