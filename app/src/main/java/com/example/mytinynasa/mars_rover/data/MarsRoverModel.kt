package com.example.mytinynasa.mars_rover.data

class MarsRoverModel {
    var image: String = ""
    var date: String = ""
    var roverType : String = ""
    var cameraInfo : String = ""

    constructor() {}

    constructor(image: String, date: String, roverType: String, cameraInfo: String)
    {
        this.image = image
        this.date = date
        this.roverType = roverType
        this.cameraInfo = cameraInfo
    }

}