package rasyidk.fa.scancode.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_qrcode.view.*
import net.glxn.qrgen.android.QRCode
import org.json.JSONObject
import rasyidk.fa.scancode.R
import rasyidk.fa.scancode.helper.UserSession
import rasyidk.fa.scancode.model.Users
import rasyidk.fa.scancode.network.NetworkApi
import rasyidk.fa.scancode.network.NetworkInterface

class QrcodeFragment: Fragment() {

    lateinit var session: UserSession
    val profil = "id_user"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_qrcode, container, false)

        session = UserSession(context!!)
//        Log.d("img", session.getUserDetails()["Name"])
        val bitmap = QRCode.from(session.getUserDetails()["Name"]).withSize(1000, 1000).bitmap()
        view.imgBarcode.setImageBitmap(bitmap)

        return view
    }

}