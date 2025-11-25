package com.example.songplayer

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.bumptech.glide.Glide
import org.json.JSONArray

class MainActivity : AppCompatActivity(), Observateur {

    lateinit var liste : ListView
    val url = "https://api.jsonbin.io/v3/b/69090376ae596e708f425664?meta=false"
    //var monModele = Modele(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.liste)

        //monModele.ajouterObservateur(this)

        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val tab = response.getJSONArray("music")
                decomposerReponse(tab)
            },
            { error ->
                Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        )

        queue.add(jsonObjectRequest)
    }

    fun decomposerReponse(tab : JSONArray) {
        val remplir = ArrayList<HashMap<String, String>>()

        for (i in 0..tab.length()-1) {
            var temp = HashMap<String, String>()
            //temp.put("id", tab.getJSONObject(i).get("id").toString())
            temp.put("title", tab.getJSONObject(i).get("title").toString())
            temp.put("album", tab.getJSONObject(i).get("album").toString())
            temp.put("artist", tab.getJSONObject(i).get("artist").toString())
            //temp.put("genre", tab.getJSONObject(i).get("genre").toString())
            //temp.put("source", tab.getJSONObject(i).get("source").toString())
            temp.put("image", tab.getJSONObject(i).get("image").toString())
            //temp.put("trackNumber", tab.getJSONObject(i).get("trackNumber").toString())
            //temp.put("totalTrackCount", tab.getJSONObject(i).get("totalTrackCount").toString())
            //temp.put("duration", tab.getJSONObject(i).get("duration").toString())
            //temp.put("site", tab.getJSONObject(i).get("site").toString())

            remplir.add(temp)
        }
        // créer un SimpleAdapter
        val adapter = MonAdapter(this, remplir, R.layout.item, arrayOf("title","album","artist","image"), intArrayOf(R.id.textTitle, R.id.textAlbum, R.id.textArtist,R.id.image))

        //val adapter = SimpleAdapter(this, remplir, R.layout.test, arrayOf("title", "album","artist"), intArrayOf(R.id.test, R.id.test2, R.id.test3))
        //val maliste = listOf("sdfg","sdfg","sdfg")
        //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1 , maliste)

        liste.adapter = adapter

    }

    inner class MonAdapter (context: Context, data: List<Map<String, Any>>, resource: Int, from: Array<String>, to: IntArray) :SimpleAdapter( context,data, resource, from, to ){
        override fun setViewImage (v: ImageView, value:String) {
            Glide.with(this@MainActivity).load(value).into(v);

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //monModele!!.enleverObservateur(this)
    }

    override fun changement(nouvelleValeur: List<ListeChansons>) {
    }
}