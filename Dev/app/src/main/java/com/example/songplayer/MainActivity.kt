package com.example.songplayer

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class MainActivity : AppCompatActivity() {

    lateinit var liste : ListView

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

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.jsonbin.io/v3/b/69090376ae596e708f425664?meta=false"


        // Notre requête demande une String du serveur
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val listeProduits:ListeChansons = Klaxon().parse<ListeChansons>(response) ?: ListeChansons()
                Toast.makeText(this, listeProduits.chansons.size.toString(), Toast.LENGTH_SHORT).show()

            },
            { Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show() })

        // Ajouter notre requête à la queue.
        queue.add(stringRequest)


    }
}