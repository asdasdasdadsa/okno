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
       // osXInt(g)
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
        //        drawLine(xCrt2Src(1.0),yCrt2Src(1.0)+4,xCrt2Src(1.0),yCrt2Src(1.0)-4)
                var (sW, sH) = with(fontMetrics.getStringBounds(s,g)){Pair(width.toInt(),height.toInt())}
//                var sH = fontMetrics.getStringBounds((1.0).toString(),g).height
                if(x==xCrt2Src(0.0) && y == yCrt2Src(0.0)) {
                    drawString(s, x+sH/2, y+sH+sH/2 )
                    return@apply
                }
                if(x == 0){
                    //drawString(s, x+sW/2, y + sH / 4)

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


    private fun osXInt(g: Graphics) {
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(2F)
                var k = xCrt2Src(0.1) - xCrt2Src(0.0)
                var i = xMin.toInt()-1
                while (i*k<width){
                    if(i%10==0) {
                        color = intColor
                        drawLine((k * i), yCrt2Src(0.0) - intLen, (k * i), yCrt2Src(0.0) + intLen)
                    }
                    else if(i%5 == 0) {
                        color = halfColor
                        drawLine(k * i, yCrt2Src(0.0) - halfLen, k * i, yCrt2Src(0.0) + halfLen)
                    }
                    else {
                        color = tenthColor
                        drawLine(i * k, yCrt2Src(0.0) - tenthLen, i * k, yCrt2Src(0.0) + tenthLen)
                    }
                    i++
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

                var s = if(t>=25) 0.02 else if(t>=10) 0.05 else if(t == 0) 2.0 else if(t<=1) 1.0 else if(t<=2) 0.5 else if(t<=4) 0.2 else 0.1//0.1//if(kk>15) (if (kk>30) 0.5 else 0.2) else 0.1
//0,,02 не особо работает
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
                var s = if(t>=25) 0.02 else if(t>=10) 0.05 else if(t == 0) 2.0 else if(t<=1) 1.0 else if(t<=2) 0.5 else if(t<=4) 0.2 else 0.1//0.1//if(kk>15) (if (kk>30) 0.5 else 0.2) else 0.1
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




    private fun xIntDel(g: Graphics)
    {
        with(plane) {
            (g as Graphics2D).apply {
                val l: Int = (xMax-xMin).toInt()
           //     xYvel = (if(l>14) (if(l>29) (if(l>79) (0.125) else 0.25) else 0.5) else 1.0)
                val ll:Int = (10*xMax-10*xMin).toInt()
                xYvel = if (ll<60) (if(ll<30) (if (ll<10) (8.0) else 4.0) else 2.0) else ((if(l>14) (if(l>29) (if(l>79) (0.125) else 0.25) else 0.5) else 1.0))
                stroke = BasicStroke(2F)
                color =intColor
         //       xYvel =1.0
                val j: Int =xCrt2Src(xMin.toInt()+0.0)
//                val k =abs ((xCrt2Src(0.0) - 0*j)/(xMin * xYvel)) //.toInt()+0.0))
                var k = ((if (l != 0 ) l.toDouble() else 1.0) / (xMax - xMin))*width / ((if (l != 0 ) l.toDouble() else 1.0) * xYvel)
   //             var k = abs(xCrt2Src(0.0)-xCrt2Src(1.0))/xYvel
//                for(i in 0..((if(l>0) l else 1)*xYvel).toInt()+1)
                var i:Int =-(j / k).toInt()
                while ((j + i * k).toInt() <=width) {
                    if (yMin <= 0 && yMax >= 0)
                        drawLine((j + i * k).toInt(),yCrt2Src(0.0) - intLen,(j + i * k).toInt(),yCrt2Src(0.0) + intLen)
                    else {
                        drawLine((j + i * k).toInt(), 0, (j + i * k).toInt(), intLen)
                        drawLine((j + i * k).toInt(), height - intLen, (j + i * k).toInt(), height)
                    }
                    i++
                }
 /*               val j: Int =xCrt2Src(0.0)
                val k = abs ((xCrt2Src(0.0) - 0*j)/(xMin * xYvel))//.toInt()+0.0))
                val d:Int = (j / k).toInt()
                for(i in -d..(l*xYvel).toInt()+1)
                    if (yMin<= 0 && yMax>=0)
                        drawLine((j + i * k).toInt(),yCrt2Src(0.0)-intLen,(j + i * k).toInt(),yCrt2Src(0.0)+intLen)
                    else{
                        drawLine((j + i * k).toInt(),0,(j + i * k).toInt(),intLen)
                        drawLine((j + i * k).toInt(),height-intLen,(j + i * k).toInt(),height)
                    }
*/
            }
        }
    }

    private fun xHalfDel(g: Graphics) {
        with(plane) {
            (g as Graphics2D).apply {
                val l = (2*xMax-2*xMin).toInt()
                stroke = BasicStroke(2F)
                color =halfColor
                val j: Int =xCrt2Src(xMin.toInt()+0.0)
            //    val k = abs ((xCrt2Src(0.0) - 0*j)/(xMin* xYvel))//.toInt()+0.0))
                var k =(l.toDouble() / (1.0*(xMax - xMin)))*width / ((if (l != 0 ) l.toDouble() else 1.0) * xYvel)
                for(i in 0..(l*xYvel).toInt()+1){
                    val x: Int = (if(i ==0 && k/2.0 <= j ) (j - k / 2.0).toInt() else (j+ k / 2.0).toInt()) + ((if (i == 0) 0 else i-1) * k + 0*k / 2.0).toInt()
                    if (yMin<= 0 && yMax>=0)
                        drawLine(x,yCrt2Src(0.0)-halfLen,x,yCrt2Src(0.0)+halfLen)
                    else{
                        drawLine(x,0,x,halfLen)
                        drawLine(x,height-halfLen,x,height)
                    }
                }
            }
        }
    }

    private fun xTenthDel(g: Graphics)
    {
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(2F)
                color =tenthColor
                val l = (10*xMax-10*xMin).toInt()
                val j: Int = if(xMin == xMin.toInt().toDouble() || xMin == xMin.toInt().toDouble()+0.5) xCrt2Src(xMin+0.1) else xCrt2Src(xMin)
            //    val k = abs((xCrt2Src(0.0) - j*0)/(xMin* xYvel))//.toInt()+0.0))//xCrt2Src(xMin+0.1)
                val k =(l.toDouble() / (1.0*(xMax - xMin)))*width / ((if (l != 0 ) l.toDouble() else 1.0) * xYvel)
                var kkk = abs(((((xMin)+if (xMin < 0) -0.01 else 0.01) *10).toInt())%5)
                if(xMin>0 && kkk != 0) kkk = 5 - kkk
                for(i in 0..(l*xYvel).toInt()+1){
                    if(i%5!=kkk) {
                        if (yMin <= 0 && yMax >= 0)
                            drawLine((0 * j + i * k / 10.0).toInt(),yCrt2Src(0.0) - tenthLen,(0 * j + i * k / 10.0).toInt(),yCrt2Src(0.0) + tenthLen)
                        else{
                            drawLine((0 * j + i * k / 10.0).toInt(),0,(0 * j + i * k / 10.0).toInt(),tenthLen)
                            drawLine((0 * j + i * k / 10.0).toInt(),height-tenthLen,(0 * j + i * k / 10.0).toInt(),height)
                        }
                    }
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


    private fun intxxxx(g: Graphics){
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(1F)
                color = intColor
                var o: Int = 1   /*
                while (o<(xMax-xMin).toInt()) o++*/

                var kkkk = okr(xMin)

                o = (okr(xMax).toInt() + if (okr(xMax)<0 && okr(okr(xMax)).toInt().toDouble() != okr(xMax)) -1.0 else 0.0 -
                        (okr(xMin).toInt() + if (okr(xMin)>0 && okr(okr(xMin)).toInt().toDouble() != okr(xMin)) 1.0 else 0.0)).toInt()
                if (o == 0) o = 1
                var k = (xCrt2Src(okr(xMax).toInt() + if (okr(xMax)<0 && okr(okr(xMax)).toInt().toDouble() != okr(xMax)) -1.0 else 0.0) -
                        xCrt2Src(okr(xMin).toInt() + if (okr(xMin)>0 && okr(okr(xMin)).toInt().toDouble() != okr(xMin)) 1.0 else 0.0)).toDouble() /o.toDouble()
                val kk = xCrt2Src(0.0)
                if(k==0.0)
                {
                   // var l =
                  //  k = width
                }
             //   if(xMin<=0.0 && xMax >= 0.0){
                    var i: Int=okr(xMin).toInt()-1
                var s:String= i.toString()
                do{
                        color = intColor
                        drawLine(kk+(i*k).toInt(),yCrt2Src(0.0) - intLen,kk+(i*k).toInt(),yCrt2Src(0.0) + intLen)
                        drawString(s,kk+(i*k).toInt()-5,yCrt2Src(0.0) - intLen-15)
                        aldaldj(g,kk+(i*k).toInt(),(k/2.0).toInt(),(i+0.5).toString())
                        adfsfrfs(g,kk+(i*k).toInt(),(k/10.0).toInt(),i+0.0)
                        adfsfrfs(g,kk+(i*k+k/2.0).toInt(),(k/10.0).toInt(),i+0.5)
                        i++
                        s = i.toString()
                    }while (kk+(i*k).toInt()<=width && k!=0.0)
           /*         i=0
                    while (xCrt2Src(0.0)+i*k>=0){
                        drawLine(xCrt2Src(0.0)+(i*k).toInt(),yCrt2Src(0.0) - intLen,xCrt2Src(0.0)-(i*k).toInt(),yCrt2Src(0.0) + intLen)
                        drawString(s,xCrt2Src(xMin.toInt().toDouble())+(i*k).toInt()-5,yCrt2Src(0.0) - intLen-15)
                        i--
                        s = i.toString()
                    }       */

            }
        }
    }


    private fun aldaldj(g: Graphics,x: Int,k: Int,h: String) {//половины
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(1F)
                color = halfColor
                drawLine(x+k,yCrt2Src(0.0)-halfLen,x+k,yCrt2Src(0.0)+halfLen)
                drawString(h,x+k-10,yCrt2Src(0.0) - intLen-15)
            }
        }
    }


    private fun adfsfrfs(g: Graphics,x: Int,k: Int,h: Double) {//десятые
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(1F)
                color = tenthColor
                for(i in 1..4) {
                    drawLine(x + i * k, yCrt2Src(0.0) - tenthLen, x + i * k, yCrt2Src(0.0) + tenthLen)
                    if(k>25)
                        drawString((h+i/10.0).toString(),x+k*i-10,yCrt2Src(0.0) - intLen-15)
                }
            }
        }
    }




















    private fun yIntDel(g: Graphics)
    {
        with(plane) {
            (g as Graphics2D).apply {
                val l: Int = (yMax-yMin).toInt()
                //yYvel = (if(l>14) (if(l>29) (if(l>79) (0.125) else 0.25) else 0.5) else 1.0)
                val ll:Int = (10*yMax-10*yMin).toInt()
                yYvel = if (ll<60) (if(ll<30) (if (ll<10) (8.0) else 4.0) else 2.0) else ((if(l>14) (if(l>29) (if(l>79) (0.125) else 0.25) else 0.5) else 1.0))
                stroke = BasicStroke(2F)
                color =intColor
                val j: Int =yCrt2Src(yMin.toInt()+0.0)
                val k = abs ((yCrt2Src(0.0) - 0*j)/(yMin*yYvel))//.toInt()+0.0))
                for(i in 0..(l*yYvel).toInt()+1)
                    if(xMin<=0 && xMax>=0)
                        drawLine(xCrt2Src(0.0)-intLen,(j + i * k).toInt(),xCrt2Src(0.0)+intLen,(j + i * k).toInt())
                    else{
                        drawLine(intLen,(j + i * k).toInt(),0,(j + i * k).toInt())
                        drawLine(width-intLen,(j + i * k).toInt(),width,(j + i * k).toInt())
                    }
            }
        }
    }

    private fun yHalfDel(g: Graphics) {
        with(plane) {
            (g as Graphics2D).apply {
                val l = (2*yMax-2*yMin).toInt()
                stroke = BasicStroke(2F)
                color =halfColor
                val j: Int =yCrt2Src(yMin.toInt()+0.0)
                val k = abs ((yCrt2Src(0.0) - 0*j)/(yMin*yYvel))//.toInt()+0.0))
                for(i in 0..(l*yYvel).toInt()+1){
                    val x: Int = (if(i ==0 && k/2.0 <= j ) (j - k / 2.0).toInt() else (j+ k / 2.0).toInt()) + ((if (i == 0) 0 else i-1) * k + 0*k / 2.0).toInt()
                    //drawLine(x,yCrt2Src(0.0)-halfLen,x,yCrt2Src(0.0)+halfLen)
                    if(xMin<=0 && xMax>=0)
                        drawLine(xCrt2Src(0.0)-halfLen,x,xCrt2Src(0.0)+halfLen,x)
                    else{
                        drawLine(halfLen,x,0,x)
                        drawLine(width-halfLen,x,width,x)
                    }
                }
            }
        }
    }

    private fun yTenthDel(g: Graphics)
    {
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(2F)
                color =tenthColor
                val l = (10*yMax-10*yMin).toInt()
                val j: Int = if(yMin == yMin.toInt().toDouble() || yMin == yMin.toInt().toDouble()+0.5) yCrt2Src(yMin+0.1) else xCrt2Src(yMin)
                val k = abs((yCrt2Src(0.0) - j*0)/(yMin * (yYvel)))//.toInt()+0.0))//xCrt2Src(xMin+0.1)
                val kkk = (((abs(yMin)+0.01) *10).toInt())%5
                for(i in 0..(l*yYvel).toInt()+1){
                    if(i%5!=kkk) {
                        if(xMin<=0 && xMax>=0)
                            drawLine(xCrt2Src(0.0) - tenthLen,(0 * j + i * k / 10.0).toInt(),xCrt2Src(0.0) + tenthLen,(0 * j + i * k / 10.0).toInt())
                        else{
                            drawLine(tenthLen,(0 * j + i * k / 10.0).toInt(),0,(0 * j + i * k / 10.0).toInt())
                            drawLine(width-tenthLen,(0 * j + i * k / 10.0).toInt(),width,(0 * j + i * k / 10.0).toInt())
                        }
                    }
                }
            }
        }
    }




}