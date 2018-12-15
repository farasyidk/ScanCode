package rasyidk.fa.scancode

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import rasyidk.fa.scancode.helper.UserSession
import rasyidk.fa.scancode.model.UserResponse
import rasyidk.fa.scancode.model.Users
import rasyidk.fa.scancode.network.NetworkApi
import rasyidk.fa.scancode.network.NetworkInterface


class MainActivity : AppCompatActivity() {

    private lateinit var session: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        session = UserSession(this)
        if (session.checkLogin()) {
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_logout -> {
                finish()
                session.logoutUser()

                return true
            }

            R.id.action_add -> {
                startActivity<AddActivity>()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}
