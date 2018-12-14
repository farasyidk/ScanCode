package rasyidk.fa.scancode.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanFragment: Fragment(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null
    private val RECORD_REQUEST_CODE = 101

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        mScannerView = ZXingScannerView(activity)

        setupPermissions()

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
        Log.d("hasil", result?.text)

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