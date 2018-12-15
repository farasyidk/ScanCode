package rasyidk.fa.scancode.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.support.v7.app.AlertDialog
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.dm7.barcodescanner.zxing.ZXingScannerView
import rasyidk.fa.scancode.model.UserResponse
import rasyidk.fa.scancode.model.Users
import rasyidk.fa.scancode.network.NetworkApi
import rasyidk.fa.scancode.network.NetworkInterface

class ScanFragment: Fragment(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null
    private val RECORD_REQUEST_CODE = 101
    lateinit var networkApi: NetworkApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        mScannerView = ZXingScannerView(activity)

        setupPermissions()
        networkApi = NetworkApi()
        return mScannerView
    }

    private fun setupPermissions() {
        val permission = checkSelfPermission(context!!,
                Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.d("ijin", "Permission to record denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA),
                RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i("ijin", "Permission has been denied by user")
                } else {
                    Log.i("ijin", "Permission has been granted by user")
                }
            }
        }
    }
//
    override fun handleResult(result: Result?) {
//        Toast.makeText(activity, "hasil "+result?.text, Toast.LENGTH_SHORT).show()


        networkApi.getRetrofit().create(NetworkInterface::class.java)
            .getUser(result?.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                val builder = AlertDialog.Builder(context!!)

                // Set the alert dialog title
                builder.setTitle(it.data[0].nama)

                val pesan = "Menemukan teman baru, username "+"<b>"+"${it.data[0].name}"+"</b>"
                builder.setMessage(Html.fromHtml(pesan))

                // Set a positive button and its click listener on alert dialog
                builder.setPositiveButton("Coba Lagi"){dialog, which ->
                    // Do something when user press the positive button
                    //Toast.makeText(context,"Ok, user ${it.data[0].name}.",Toast.LENGTH_SHORT).show()

                }


                // Display a negative button on alert dialog
                builder.setNegativeButton("Cukup"){dialog,which ->
                    Toast.makeText(context,"Matur suwun.",Toast.LENGTH_SHORT).show()

                }


                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()

                // Display the alert dialog on app interface
                dialog.show()
                    Log.d("hasill", "inii ${it.data[0].name}")
                },{
                    Log.d("hasil", it.localizedMessage)
                }
            )
    val handler = Handler()
        handler.postDelayed({ mScannerView?.resumeCameraPreview(this@ScanFragment) }, 2000)
    }

    override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.startCamera()
    }

}