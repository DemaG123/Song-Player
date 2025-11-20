package com.example.songplayer

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class Modele(var context : Context) : Sujet{

    private lateinit var lesChansons: List<ListeChansons>
    private var obs: Observateur? = null

    fun chercherChansons() {
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.jsonbin.io/v3/b/69090376ae596e708f425664?meta=false"

        // Notre requête demande une String du serveur
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val listeProduits:ListeChansons = Klaxon().parse<ListeChansons>(response) ?: ListeChansons()
                Toast.makeText(context, listeProduits.chansons.size.toString(), Toast.LENGTH_SHORT).show()
                lesChansons = listeProduits.chansons

            },
            { Toast.makeText(context, "That didn't work!", Toast.LENGTH_SHORT).show() })

        // Ajouter notre requête à la queue.
        queue.add(stringRequest)
    }

    fun getUneChanson(): List<ListeChansons>? {
        return lesChansons
    }


    fun setValeur(lesChansons: List<ListeChansons>) {
        this.lesChansons = lesChansons
        //changement de l'état du sujet, avertir les observateurs
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