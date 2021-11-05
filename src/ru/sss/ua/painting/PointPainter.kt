package ru.sss.ua.painting

import CartesianPlane
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D

class PointPainter(private val plane: CartesianPlane,private val m:Map<Double,Double>) : Painter {

    var coLor: Color = Color.BLACK

    override fun paint(g: Graphics){
        with(plane){
            (g as Graphics2D).apply {
                color= coLor
                stroke = BasicStroke(5F)
                m.keys.forEach {x->
                    m[x]?.let {  y->
                        drawOval(xCrt2Src(x)-2,yCrt2Src(y)-2,5,5)
                    }
                }
            }
        }
    }
}