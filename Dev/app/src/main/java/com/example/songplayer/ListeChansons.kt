package com.example.songplayer

import com.beust.klaxon.Json

class ListeChansons {

    @Json(name = "music") // si tu veux utiliser un autre nom que celui dans le JSON
    var chansons : List<ListeChansons> = emptyList()

}