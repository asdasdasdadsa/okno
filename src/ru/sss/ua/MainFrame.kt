package ru.sss.ua

import CartesianPlane
import ru.sss.ua.math.polinom.Newton
import ru.sss.ua.math.polinom.Polinom
import ru.sss.ua.painting.CartesianPainter
import ru.sss.ua.painting.FunctionPainter
import ru.sss.ua.painting.PointPainter
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import kotlin.math.abs

class MainFrame:JFrame() {
    val mainPanel: GraphicsPanel
    val controlPanel: JPanel

    val xMinLabel: JLabel
    val yMinLabel: JLabel
    val xMaxLabel: JLabel
    val yMaxLabel: JLabel

    val sXMin: JSpinner
    val sXMax: JSpinner
    val sYMin: JSpinner
    val sYMax: JSpinner

    val sXMinM: SpinnerNumberModel
    val sXMaxM: SpinnerNumberModel
    val sYMinM: SpinnerNumberModel
    val sYMaxM: SpinnerNumberModel

    val chPoint: JCheckBox
    val chFunk: JCheckBox
    val chDiff: JCheckBox

    val lPoint: JLabel
    val lFunk: JLabel
    val lDiff: JLabel

    val pointColorP: JPanel
    val funColorP: JPanel
    val diffColorP: JPanel

    var polinom:Newton = Newton(mutableMapOf(0.0 to 10000.0))

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(600,700)

        controlPanel =  JPanel().apply {}

        lPoint = JLabel().apply { text = "Отображать точки" }
        lFunk = JLabel().apply { text = "Отображать функцию" }
        lDiff = JLabel().apply { text = "Отображать производную" }

        xMinLabel = JLabel().apply { text = "Xmin" }
        yMinLabel = JLabel().apply { text = "Ymin" }
        xMaxLabel = JLabel().apply { text = "Xmax" }
        yMaxLabel = JLabel().apply { text = "Ymax" }

        sXMinM = SpinnerNumberModel(-5.0,-100.0,4.9,0.1)
        sXMaxM = SpinnerNumberModel(5.0,-4.9,100.0,0.1)
        sYMinM = SpinnerNumberModel(-5.0,-100.0,4.9,0.1)
        sYMaxM = SpinnerNumberModel(5.0,-4.9,100.0,0.1)

        sXMin = JSpinner(sXMinM)
        sXMax = JSpinner(sXMaxM)
        sYMin = JSpinner(sYMinM)
        sYMax = JSpinner(sYMaxM)

        val mainPlane = CartesianPlane(
            sXMinM.value as Double,
            sXMaxM.value as Double,
            sYMinM.value as Double,
            sYMaxM.value as Double
        )

        val cartesianPainter = CartesianPainter(mainPlane)

        val tt = mutableMapOf<Double, Double>()
        var p : PointPainter = PointPainter(mainPlane,tt)
        p.coLor=Color.RED

  //      for(i in -5..5) {
    //        tt[i.toDouble()] = ((i*i*i-i+32*i)%5).toDouble()
      //  }

    //    val sinPainter = FunctionPainter(mainPlane)
      //  val cosPainter = FunctionPainter(mainPlane,Math::cos)
        val functionPainter = FunctionPainter(mainPlane,::polin).apply { funColor = Color.GREEN }

        val diffFunctionPainter = FunctionPainter(mainPlane,::poldiff).apply { funColor = Color.BLUE }

        val painters = mutableListOf(cartesianPainter,/*sinPainter,cosPainter,*/functionPainter,p)

        mainPanel = GraphicsPanel(painters).apply {//для изменения аругментов список
            background = Color.WHITE //при изменении надо пересоздать это
        }//лучше конструктор который принимает список а не варарг

        mainPlane.pixelSize = mainPanel.size

        mainPanel.addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                e?.let {
                    if(e.button == MouseEvent.BUTTON1) {
                        var t = 0
                        for(i in -5..5)
                            if(tt[mainPlane.xSct2Crt(e.x+i)] !is Double)
                                t++
                        if (t==11) tt[mainPlane.xSct2Crt(e.x)] = mainPlane.ySct2Crt(e.y)
                        p = PointPainter(mainPlane, tt)
                        polinom = Newton(tt)
                    }
                    if(e.button == MouseEvent.BUTTON3) {
                        for (i in -5..5)
                            if (tt[mainPlane.xSct2Crt(e.x+i)] is Double && abs(e.y-mainPlane.yCrt2Src(tt[mainPlane.xSct2Crt(e.x+i)]!!))<=5)
                                tt.remove(mainPlane.xSct2Crt(e.x))
                        p = PointPainter(mainPlane, tt)
                        polinom = Newton(tt)
                    }
                }
                mainPanel.repaint()
            }
        })

        mainPanel.addComponentListener(object: ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                mainPlane.pixelSize = mainPanel.size
                mainPanel.repaint()
            }
        })

        sXMin.addChangeListener{
            sXMaxM.minimum = (sXMinM.value as Double + 0.1)
            mainPlane.xSegment = Pair(sXMin.value as Double, sXMax.value as Double)
            mainPanel.repaint()
        }
        sXMax.addChangeListener{
            sXMinM.maximum = (sXMaxM.value as Double - 0.1)
            mainPlane.xSegment = Pair(sXMin.value as Double, sXMax.value as Double)
            mainPanel.repaint()
        }
        sYMin.addChangeListener{
            sYMaxM.minimum = (sYMinM.value as Double + 0.1)
            mainPlane.ySegment = Pair(sYMin.value as Double,sYMaxM.value as Double)
            mainPanel.repaint()
        }
        sYMax.addChangeListener{
            sYMinM.maximum = (sYMaxM.value as Double - 0.1)
            mainPlane.ySegment = Pair(sYMin.value as Double,sYMaxM.value as Double)
            mainPanel.repaint()
        }

        sXMin.addChangeListener{ mainPlane.pixelSize = mainPanel.size;            mainPanel.repaint()}
        sXMax.addChangeListener{ mainPlane.pixelSize = mainPanel.size;            mainPanel.repaint()}
        sYMin.addChangeListener{ mainPlane.pixelSize = mainPanel.size;            mainPanel.repaint()}
        sYMax.addChangeListener{ mainPlane.pixelSize = mainPanel.size;            mainPanel.repaint()}

        chPoint = JCheckBox().apply { isSelected = true }
        chFunk = JCheckBox().apply { isSelected = true }
        chDiff = JCheckBox().apply { isSelected = false }

        pointColorP = JPanel().apply { background = Color.RED }
        funColorP = JPanel().apply { background = Color.GREEN }
        diffColorP = JPanel().apply { background = Color.BLUE }

        chPoint.addItemListener{
            painters.removeIf {
                it is PointPainter}
            if(chPoint.isSelected)
                painters.add(p.apply { coLor=pointColorP.background })
            mainPanel.repaint()
        }

        chFunk.addItemListener{
            painters.removeIf {
                it == functionPainter}
            if(chFunk.isSelected) {
                painters.add(functionPainter)
                painters.removeIf { it is PointPainter}
                painters.add(p)
            }
            mainPanel.repaint()
        }

        chDiff.addItemListener{
            painters.removeIf {
                it == diffFunctionPainter}
            if(chDiff.isSelected)
                painters.add(diffFunctionPainter)
            mainPanel.repaint()
        }

        pointColorP.addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                e?.let {
                    var color = JColorChooser.showDialog(null,"",pointColorP.background)
                    pointColorP.background = color
                    p.coLor=color
                    painters.removeIf { it is PointPainter}
                    painters.add(p)
                    mainPanel.repaint()
                }
            }
        })

        funColorP.addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                e?.let {
                    var color = JColorChooser.showDialog(null,"",funColorP.background)
                    funColorP.background = color
                    functionPainter.funColor = color
                    mainPanel.repaint()
                }
            }
        })

        diffColorP.addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                e?.let {
                    var color = JColorChooser.showDialog(null,"",diffColorP.background)
                    diffColorP.background = color
                    diffFunctionPainter.funColor = color
                    mainPanel.repaint()
                }
            }
        })

        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(createSequentialGroup()
                .addGap(4)
                .addGroup(createParallelGroup()
                    .addComponent(mainPanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    .addComponent(controlPanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                )
                .addGap(4)
            )

            setVerticalGroup(createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                .addGap(4)
                .addComponent(controlPanel,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
            )
        }

        controlPanel.layout = GroupLayout(controlPanel).apply {
            setHorizontalGroup(createSequentialGroup()
                .addGroup(createParallelGroup()
                    .addGroup(createSequentialGroup()
                        .addComponent(xMinLabel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(sXMin,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                    .addGroup(createSequentialGroup()
                        .addComponent(yMinLabel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(sYMin,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                )
                .addGroup(createParallelGroup()
                    .addGroup(createSequentialGroup()
                        .addComponent(xMaxLabel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(sXMax,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                    .addGroup(createSequentialGroup()
                        .addComponent(yMaxLabel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(sYMax,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                )
                .addGroup(createParallelGroup()
                    .addGroup(createSequentialGroup()
                        .addComponent(chPoint,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(lPoint,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                    .addGroup(createSequentialGroup()
                        .addComponent(chFunk ,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(lFunk,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                    .addGroup(createSequentialGroup()
                        .addComponent(chDiff ,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(lDiff,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                )
                .addGroup(createParallelGroup()
                    .addComponent(pointColorP,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    .addComponent(funColorP,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    .addComponent(diffColorP,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                )
            )

            setVerticalGroup(createParallelGroup()
                .addGroup(createSequentialGroup()
                    .addGroup(createParallelGroup()
                        .addComponent(xMinLabel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(sXMin,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)

                        .addComponent(xMaxLabel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(sXMax,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                    .addGroup(createParallelGroup()
                        .addComponent(yMinLabel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(sYMin,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)

                        .addComponent(yMaxLabel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(sYMax,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                )
                .addGroup(createSequentialGroup()
                    .addGroup(createParallelGroup()
                        .addComponent(chPoint,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(lPoint,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                    .addGroup(createParallelGroup()
                        .addComponent(chFunk ,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(lFunk,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                    .addGroup(createParallelGroup()
                        .addComponent(chDiff ,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(lDiff,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                )
                .addGroup(createSequentialGroup()
                    .addComponent(pointColorP,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    .addComponent(funColorP,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    .addComponent(diffColorP,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                )
            )
        }
        pack()
        mainPlane.width = mainPanel.width
        mainPlane.height = mainPanel.height
    }

    private fun polin(x: Double) = polinom(x)

    private fun poldiff(x: Double) = polinom.diff()(x)
}