package ru.sss.ua.painting

import CartesianPlane
import java.awt.*
import kotlin.math.abs

class CartesianPainter(private val plane:CartesianPlane) : Painter{

    var axesColor: Color = Color.GRAY
    var intColor: Color = Color.RED
    var halfColor: Color = Color.BLUE
    var tenthColor: Color = Color.BLACK

    var intLen: Int = 8
    var halfLen: Int = 5
    var tenthLen: Int = 3

    var xYvel: Double = 1.0
    var yYvel: Double = 1.0

    var mainFont: Font = Font("Cambria", Font.BOLD,14)

    override fun paint(g: Graphics){
        paintAxes(g)
        xDel(g)
        yDel(g)
    }

    private fun xDel(g: Graphics){
        osX(g)
    }

    private fun yDel(g: Graphics){
        osY(g)
    }


    private fun paintAxes(g: Graphics){
        with(plane){
            (g as Graphics2D).apply {
                stroke = BasicStroke(3F)
                color = axesColor
                if (yMin<= 0 && yMax>=0){
                    drawLine(0,yCrt2Src(0.0),width,yCrt2Src(0.0))
                } else{
       //             drawLine(0,0,width,0)
         //           drawLine(0,height,width,height)
                }

                if(xMin<=0 && xMax>=0){
                    drawLine(xCrt2Src(0.0),0,xCrt2Src(0.0),height)
                } else{
         //           drawLine(0,0,0,height)
           //         drawLine(width,0,width,height)
                }
            }
        }
    }

    private fun aeldaljd(g: Graphics,s: String,x:Int,y:Int,os:Boolean){
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(2F)
                font = mainFont
                var (sW, sH) = with(fontMetrics.getStringBounds(s,g)){Pair(width.toInt(),height.toInt())}
                if(x==xCrt2Src(0.0) && y == yCrt2Src(0.0)) {
                    drawString(s, x+sH/2, y+sH+sH/2 )
                    return@apply
                }
                if(os){
                    drawString(s,x-sW/2,y+sH+sH/2)
                    if(y==height)
                        drawString(s,x-sW/2,y-sH)
                }
                else {
                    drawString(s, x+sW/2, y + sH / 4)
                    if(x==width)
                        drawString(s, x-sW/2-sW, y + sH / 4)
                }

            }
        }
    }


    private fun osX(g: Graphics){
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(2F)
                val t = xCrt2Src(xMin+0.1)
                var i = 0

                if (xMin==-2.7) {
                    var yy = 3
                }

                var s = if(t>=25) 0.02 else if(t>=10) 0.05 else if(t == 0) 2.0 else if(t<=1) 1.0 else if(t<=2) 0.5 else if(t<=4) 0.2 else 0.1
                var l:Double

                if (xMin<0){
                    val r = (abs(xMin)/(s*10.0)).toInt()+1
                    l=-r*s*10.0
                }
                else{
                    val r = (xMin/(s*10.0)).toInt()-1
                    l=r*s*10.0
                }
                var v = xCrt2Src(s)
                while (xCrt2Src(l) <= width){
                    if(i%10==0){
                        color = intColor
                        if (yMin<= 0 && yMax>=0) {
                            drawLine(xCrt2Src(l), yCrt2Src(0.0) - intLen, xCrt2Src(l), yCrt2Src(0.0) + intLen)
                            aeldaljd(g,(okr(l)).toString(),xCrt2Src(l),yCrt2Src(0.0),true)
                        }
                        else{
                            drawLine(xCrt2Src(l),0 + intLen, xCrt2Src(l), 0)
                            aeldaljd(g,(okr(l)).toString(),xCrt2Src(l),0,true)
                            drawLine(xCrt2Src(l), height - intLen, xCrt2Src(l), height)
                            aeldaljd(g,(okr(l)).toString(),xCrt2Src(l),height,true)
                        }
                    }
                    else if(i%5==0){
                        color = halfColor
                        if (yMin<= 0 && yMax>=0) {
                            drawLine(xCrt2Src(l), yCrt2Src(0.0) - halfLen, xCrt2Src(l), yCrt2Src(0.0) + halfLen)
                            if(t>=25)
                                aeldaljd(g,((okr2(l))).toString(),xCrt2Src(l),yCrt2Src(0.0),true)
                        }
                        else{
                            drawLine(xCrt2Src(l),0 + halfLen, xCrt2Src(l), 0)
                            drawLine(xCrt2Src(l), height - halfLen, xCrt2Src(l), height)
                        }
                    }
                    else {
                        color = tenthColor
                        if (yMin<= 0 && yMax>=0) {
                            drawLine(xCrt2Src(l), yCrt2Src(0.0) - tenthLen, xCrt2Src(l), yCrt2Src(0.0) + tenthLen)
                            if(t>=150)
                                aeldaljd(g,((okr2(l))).toString(),xCrt2Src(l),yCrt2Src(0.0),true)
                        }
                        else{
                            drawLine(xCrt2Src(l),0 + tenthLen, xCrt2Src(l), 0)
                            drawLine(xCrt2Src(l), height - tenthLen, xCrt2Src(l), height)
                        }
                    }
                    i++
                    l = l + s
                    l=okr2(l)
                    if(okr2(l)==-0.0 || okr2(l)==0.0)
                        l = 0.0
                }
            }
        }
    }



    private fun osY(g: Graphics){
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(2F)
                val t = yCrt2Src(yMax-0.1)
                var i = 0
                var s = if(t>=25) 0.02 else if(t>=10) 0.05 else if(t == 0) 2.0 else if(t<=1) 1.0 else if(t<=2) 0.5 else if(t<=4) 0.2 else 0.1
                var l: Double

                if (yMin<0){
                    val r = (abs(yMin)/(s*10.0)).toInt()+1
                    l=-r*s*10.0
                }
                else{
                    val r = (yMin/(s*10.0)).toInt()-1
                    l=r*s*10.0
                }

                while (yCrt2Src(l) >= 0){
                    if(i%10==0){
                        color = intColor
                        if(xMin<=0 && xMax>=0) {
                            drawLine(xCrt2Src(0.0) - intLen, yCrt2Src(l), xCrt2Src(0.0) + intLen, yCrt2Src(l))
                            aeldaljd(g,(okr(l)).toString(),xCrt2Src(0.0),yCrt2Src(l),false)
                        }
                        else{
                            drawLine(0, yCrt2Src(l), intLen, yCrt2Src(l))
                            aeldaljd(g,(okr(l)).toString(),0,yCrt2Src(l),false)
                            drawLine(width, yCrt2Src(l), width - intLen, yCrt2Src(l))
                            aeldaljd(g,(okr(l)).toString(),width,yCrt2Src(l),false)
                        }
                    }
                    else if(i%5==0){
                        color = halfColor
                        if(xMin<=0 && xMax>=0) {
                            drawLine(xCrt2Src(0.0) - halfLen, yCrt2Src(l), xCrt2Src(0.0) + halfLen, yCrt2Src(l))
                            if(t>=25)
                                aeldaljd(g,((okr2(l))).toString(),xCrt2Src(0.0),yCrt2Src(l),false)
                        }
                        else{
                            drawLine(0, yCrt2Src(l), halfLen, yCrt2Src(l))
                            drawLine(width, yCrt2Src(l), width - halfLen, yCrt2Src(l))
                        }
                    }
                    else {
                        color = tenthColor
                        if(xMin<=0 && xMax>=0) {
                            drawLine(xCrt2Src(0.0) - tenthLen, yCrt2Src(l), xCrt2Src(0.0) + tenthLen, yCrt2Src(l))
                            if(t>=150)
                                aeldaljd(g,((okr2(l))).toString(),xCrt2Src(0.0),yCrt2Src(l),false)
                        }
                        else{
                            drawLine(0, yCrt2Src(l), tenthLen, yCrt2Src(l))
                            drawLine(width, yCrt2Src(l), width - tenthLen, yCrt2Src(l))
                        }
                    }
                    i++
                    l = l + s
                    l=okr2(l)
                    if(okr2(l)==-0.0 || okr2(l)==0.0)
                        l = 0.0
                }
            }
        }
    }

    private fun okr(x: Double): Double{
        var xx = (abs(x))*100
        var xx_ = xx.toInt()%10
        if (xx_<0) xx_+=10
        if(xx_>=5) xx=(xx/10.0).toInt()+1.0
        else xx=(xx/10.0).toInt()+0.0
        return if(x>=0) xx/10.0 else -xx/10.0
    }

    private fun okr2(x: Double): Double{
        var xx = (abs(x))*1000
        var xx_ = xx.toInt()%10
        if (xx_<0) xx_+=10
        if(xx_>=5) xx=(xx/10.0).toInt()+1.0
        else xx=(xx/10.0).toInt()+0.0
        return if(x>=0) xx/100.0 else -xx/100.0
    }
}