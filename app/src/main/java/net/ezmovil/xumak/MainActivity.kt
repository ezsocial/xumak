package net.ezmovil.xumak

import android.app.ProgressDialog
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
import net.ezmovil.xumak.info.infoACKS
import net.ezmovil.xumak.info.infoACKSDetail
import net.ezmovil.xumak.info.infoContext
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

class MainActivity : AppCompatActivity() {

    var arrayHourlyWeather = ArrayList<JSONArray>()
    var arrayDays = ArrayList<JSONObject>()

    private var recyclerView: RecyclerView? = null
    private var adapter: AlbumAdapter2? = null
    private var albumList: List<Album2>? = null


    //char_id, name, birthday, occupation[2], img, status, nickname, appearance[5], portrayed, category, better_call_saul_appearance[0]
    var arrayEpisodes = ArrayList<Int>()
    var arrayName = ArrayList<String>()
    var arrayNickname = ArrayList<String>()
    var arrayImages = ArrayList<String>()

    var arrayOccupation = ArrayList<String>()
    var arrayStatus = ArrayList<String>()
    var arrayPortrayed = ArrayList<String>()

    var imageView: ImageView? = null

    var a: Album2? = null

    var b: Bitmap? = null

    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Xumak Challenge"
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setTitle("Xumak Progress Bar")
        progressDialog.setMessage("please wait")
        progressDialog.show()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        context = this

        val _infoContext = infoContext()
        _infoContext.set_context(context)

        imageView = findViewById<ImageView>(R.id.thumbnail)

        val parentLayout = findViewById<View>(android.R.id.content)
        initCollapsingToolbar()

        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView

        albumList = ArrayList()
        adapter = AlbumAdapter2(this, albumList)

        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.setLayoutManager(mLayoutManager)
        recyclerView!!.addItemDecoration(
            MainActivity.GridSpacingItemDecoration(
                2,
                dpToPx(10),
                true
            )
        )
        recyclerView!!.setItemAnimator(DefaultItemAnimator())
        recyclerView!!.setAdapter(adapter)

        try {

            Glide.with(this).load(R.drawable.ic_juangomez)
                .into((findViewById<View>(R.id.backdrop) as ImageView))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        /* task1 */
        val task1: MainActivity.apiListCities =
            MainActivity.apiListCities()
        val t1 = Thread(task1, "Thread - 1")

        // now, let's start all three threads
        t1.start()

        try {
            t1.join()
            if (!t1.isAlive) {
                val _infoACKS = infoACKS()
                if (_infoACKS.getToken() != null) {
                    prepareListXumak()
                    for (i in arrayImages.indices) {
                        a = Album2(
                            arrayName.get(i),
                            arrayNickname.get(i),
                            arrayEpisodes.get(i),
                            arrayImages.get(i),
                            arrayOccupation.get(i),
                            arrayStatus.get(i),
                            arrayPortrayed.get(i)

                        )
                        (albumList as ArrayList<Album2>).add(a!!)
                    }
                    progressDialog.hide()
                }
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        adapter!!.notifyDataSetChanged()

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
        val collapsingToolbar = findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbar.title = " "
        val appBarLayout = findViewById<View>(R.id.appbar) as AppBarLayout
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

    /**
     * Adding ListCities
     */
    private fun prepareListXumak() {
        val _infoACKS = infoACKS()
        val _token = _infoACKS.token
        try {
            val  obj = JSONArray(_token)
            for (i in 0 until obj.length()) {
                // char_id, name, birthday, occupation[2], img, status, nickname, appearance[5], portrayed

                arrayName.add((obj.get(i) as JSONObject).getString("name"))
                arrayNickname.add((obj.get(i) as JSONObject).getString("nickname"))
                arrayImages.add((obj.get(i) as JSONObject).getString("img"))
                arrayEpisodes.add((obj.get(i) as JSONObject).getString("char_id").toInt())

                val _vector = (obj.get(i) as JSONObject).getString("occupation")
                for(j in 0 until JSONArray(_vector).length()){
                    arrayOccupation.add(JSONArray(_vector).get(j) as String)
                    arrayOccupation.add(" , ")
                }

                //arrayOccupation.add((obj.get(i) as JSONObject).getString("status"))
                arrayStatus.add((obj.get(i) as JSONObject).getString("status"))
                arrayPortrayed.add((obj.get(i) as JSONObject).getString("portrayed"))

            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        } catch (tx: Throwable) {
            Log.e("My App", "Could not parse malformed JSON: \"$_token\"")
        }
    }

    /**
    * RecyclerView item decoration - give equal margin around grid item
    */
    class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }

    }

    /**
     * Converting dp to pixel
     */
    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    private class apiListCities : Runnable {
        private var predecessor: Thread? = null
        override fun run() {
            println(Thread.currentThread().name + " Started")
            val httpclient: HttpClient = DefaultHttpClient()
            val response: HttpResponse
            var responseString: String? = null
            try {
                //response = httpclient.execute(HttpGet("https://weather.exam.bottlerocketservices.com/cities"))
                response = httpclient.execute(HttpGet("https://www.breakingbadapi.com/api/characters?limit=100"))
                val statusLine = response.statusLine
                if (statusLine.statusCode == HttpStatus.SC_OK) {
                    val out = ByteArrayOutputStream()
                    response.entity.writeTo(out)
                    responseString = out.toString()
                    out.close()
                } else {
                    //Closes the connection.
                    response.entity.content.close()
                    throw IOException(statusLine.reasonPhrase)
                }
            } catch (e: ClientProtocolException) {
                //TODO Handle problems..
                e.printStackTrace()
            } catch (e: IOException) {
                //TODO Handle problems..
                e.printStackTrace()
            }
            val _infoACKS = infoACKS()
            _infoACKS.setAck(responseString)
            println(Thread.currentThread().name + " Finished")
        }

        fun setPredecessor(t: Thread?) {
            predecessor = t
        }
    }

}