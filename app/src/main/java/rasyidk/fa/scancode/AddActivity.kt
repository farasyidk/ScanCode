package rasyidk.fa.scancode

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*
import rasyidk.fa.scancode.fragment.QrcodeFragment
import rasyidk.fa.scancode.fragment.ScanFragment


class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val toolbar = supportActionBar
        toolbar?.hide()
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        initFragment(ScanFragment())
    }

    private fun initFragment(classFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_add, classFragment)
        transaction.commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_scan -> {
                initFragment(ScanFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_qrcode -> {
                initFragment(QrcodeFragment())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

}