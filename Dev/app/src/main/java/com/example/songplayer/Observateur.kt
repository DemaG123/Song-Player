package com.example.songplayer

interface Observateur {
    fun changement(nouvelleValeur: List<ListeChansons>)
}