package com.example.mytinynasa.eonet.data

data class Event(
    val id : String,
    val title : String,
    val description : String?,
    val link : String,
    //TODO Decorator ou jsp pour dire que c'est une date
    val closed : String?,
    val categories : List<Any>?,
    val sources : List<Any>?,
    val geometry : List<Any>?
)
