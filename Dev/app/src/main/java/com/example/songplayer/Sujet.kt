package com.example.songplayer

interface Sujet {
    fun ajouterObservateur(o:Observateur)
    fun enleverObservateur(o:Observateur)
    fun avertirObservateurs()
}