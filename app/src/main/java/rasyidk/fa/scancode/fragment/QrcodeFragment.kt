package rasyidk.fa.scancode.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_qrcode.view.*
import net.glxn.qrgen.android.QRCode
import rasyidk.fa.scancode.R

class QrcodeFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_qrcode, container, false)

        val bitmap = QRCode.from("Fadqurrosyidik").withSize(1000, 1000).bitmap()
        view.imgBarcode.setImageBitmap(bitmap)

        return view
    }

}