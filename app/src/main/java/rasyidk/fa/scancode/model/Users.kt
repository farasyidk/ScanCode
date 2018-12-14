package rasyidk.fa.scancode.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Users (
        val id: Int,
        val nama: String,
        val username: String,
        val foto: String)

