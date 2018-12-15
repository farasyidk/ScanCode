package rasyidk.fa.scancode.helper

import android.content.Context
import android.content.SharedPreferences
import android.content.Intent
import rasyidk.fa.scancode.LoginActivity
import rasyidk.fa.scancode.MainActivity
import android.util.Log

class UserSession {
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor
    var context: Context
    private var PRIVATE_MODE = 0
    private val PREFER_NAME = "Reg"
    private val IS_USER_LOGIN = "IsUserLoggedIn"
    private val KEY_NAME = "Name"
    private val KEY_TOKEN = "token"
    private val KEY_ID = "id_user"

    constructor(context: Context) {
        this.context = context
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    fun createUserLoginSession(token: String, nama: String, id: String) {
        editor.putBoolean(IS_USER_LOGIN, true)

        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_NAME, nama)
        editor.putString(KEY_ID, id)

        editor.commit()
    }

    fun checkLogin(): Boolean {
        if (!isUserLoggedIn()) {
            Log.d("session", "logged")
            val i = Intent(context, LoginActivity::class.java)

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(i)

            return true
        }
        Log.d("session", "no loggin")
        return false
    }

    fun logoutUser() {

        editor.clear()
        editor.commit()

        val i = Intent(context, MainActivity::class.java)

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        context.startActivity(i)
    }

    fun getUserDetails(): HashMap<String, String> {

        val user = HashMap<String, String>()

        user[KEY_NAME] = pref.getString(KEY_NAME, null)

        user[KEY_TOKEN] = pref.getString(KEY_TOKEN, null)
        user[KEY_ID] = pref.getString(KEY_ID, null)


        return user
    }

    private fun isUserLoggedIn(): Boolean {
        return pref.getBoolean(IS_USER_LOGIN, false)
    }
}