package com.example.songplayer

import android.content.Context
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.bumptech.glide.Glide
import org.json.JSONArray

class Modele(var context : Context) : Sujet{

    private lateinit var lesChansons: List<ListeChansons>
    private var obs: Observateur? = null

    fun chercherChansons(liste: ListView) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.jsonbin.io/v3/b/69090376ae596e708f425664?meta=false"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val tab = response.getJSONArray("music")
                decomposerReponse(tab, liste)
            },
            { error ->
                Toast.makeText(context, "That didn't work!", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun decomposerReponse(tab : JSONArray, liste : ListView) {
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
        val adapter = MonAdapter(context, remplir, R.layout.item, arrayOf("title","album","artist","image"), intArrayOf(R.id.textTitle, R.id.textAlbum, R.id.textArtist,R.id.image))
        liste.adapter = adapter

    }

    inner class MonAdapter (context: Context, data: List<Map<String, Any>>, resource: Int, from: Array<String>, to: IntArray) :
        SimpleAdapter( context,data, resource, from, to ){
        override fun setViewImage (v: ImageView, value:String) {
            Glide.with(context).load(value).into(v);
        }
    }

    fun getUneChanson(): List<ListeChansons>? {
        return lesChansons
    }

    fun setValeur(lesChansons: List<ListeChansons>) {
        this.lesChansons = lesChansons
        avertirObservateurs()
    }

    override fun ajouterObservateur(o: Observateur) {
        this.obs = obs
    }

    override fun enleverObservateur(o: Observateur) {
        this.obs = null
    }

    override fun avertirObservateurs() {
        obs!!.changement(lesChansons)
    }

}