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
    var monModele = Modele(this)

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

        monModele.chercherChansons(liste)

        //monModele.ajouterObservateur(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        //monModele!!.enleverObservateur(this)
    }

    override fun changement(nouvelleValeur: List<ListeChansons>) {
    }
}