package com.example.mytinynasa.eonet.data

data class EonetResult(
    val title : String,
    val description : String,
    val link : String,
    //TODO Change events to list of Event objects
    val events : List<Event>?
) {

}
