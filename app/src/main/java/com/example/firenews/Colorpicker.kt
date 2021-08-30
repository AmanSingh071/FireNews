package com.example.firenews

object Colorpicker{
    val colors=
        arrayOf("#EFDECD","#84DE02","#5D8AA8","#C9FFE5","#FFBF00","#000099","#CC00AA")
    var colorIndex =1
    fun getColor():String{
        return colors[colorIndex++ % colors.size]
    }
}