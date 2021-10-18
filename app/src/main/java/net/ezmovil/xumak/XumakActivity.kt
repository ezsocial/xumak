package net.ezmovil.xumak

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import net.ezmovil.xumak.info.infoACKSDetail
import net.ezmovil.xumak.info.infoContext
import net.ezmovil.xumak.info.infoXumakImage
import org.apache.http.HttpResponse
import org.apache.http.HttpStatus
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class XumakActivity : AppCompatActivity() {


    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xumak)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_weather)

        val _infoXumakrImage = infoXumakImage()

        val name: TextView = findViewById(R.id.name_xumak) as TextView
        val nickname: TextView = findViewById(R.id.nickname_xumak) as TextView
        val occupation: TextView = findViewById(R.id.occupation) as TextView
        val status: TextView = findViewById(R.id.status) as TextView
        val portrayed: TextView = findViewById(R.id.portrayed) as TextView

        name.text = _infoXumakrImage.name
        nickname.text = _infoXumakrImage.nickname
        occupation.text = _infoXumakrImage.occupation
        status.text = _infoXumakrImage.status
        portrayed.text = _infoXumakrImage.portrayed

        setSupportActionBar(toolbar)


        try {

            Glide.with(this).load(_infoXumakrImage.txt)
                .into((findViewById<View>(R.id.backdrop_xumak) as ImageView))

        } catch (e: Exception) {
            e.printStackTrace()
        }

        context = this

        val _infoContext = infoContext()
        _infoContext.set_context(context)

        val parentLayout = findViewById<View>(android.R.id.content)
        initCollapsingToolbar()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private fun initCollapsingToolbar() {
        val collapsingToolbar = findViewById<View>(R.id.collapsing_toolbar_weather) as CollapsingToolbarLayout
        collapsingToolbar.title = " "
        val appBarLayout = findViewById<View>(R.id.appbar_weather) as AppBarLayout
        appBarLayout.setExpanded(true)

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = getString(R.string.app_name)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }
}