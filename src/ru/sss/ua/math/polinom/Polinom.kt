package ru.sss.ua.math.polinom

import java.lang.StringBuilder
import kotlin.math.abs
import ru.sss.math.*
import kotlin.math.*
import kotlin.math.pow

open class Polinom(coeff: Collection<Double>) {

    //Набор коэффициентов, доступных внутри класса
    private val _coeff: MutableList<Double>

    //Неизменяемый список коэффициентов для получения извне
    var coeff: List<Double>
        get() = _coeff.toList()
        protected set(value){
            _coeff.clear()
            _coeff.addAll(value)
        }

    //Степень полинома
    val degree: Int
        get() = coeff.size - 1

    init{
        _coeff = coeff.toMutableList()
        removeZeros()
    }

    constructor(coeff: Array<Double>) : this(coeff.toList())
    //constructor(coeff: DoubleArray): this(coeff.toList()) //замена
    constructor(vararg c:Double) : this(c.toList())


    constructor(coeff: Double) : this(listOf(coeff))

    //Создание полинома нулевой степени с коэффициентом = 0
    constructor() : this(mutableListOf(0.0))

    private fun removeZeros(){
        var found = false
        val nc = coeff.reversed().filter{
            if (found || it neq 0.0) {found = true; true} else false
        }
        _coeff.clear()
        _coeff.addAll(nc.reversed())
        if (_coeff.size == 0) _coeff.add(0.0)
    }

    operator fun plus(other: Polinom) =
        Polinom(List(max(degree, other.degree)+1){
            (if (it <= degree && !(it<= other.degree && _coeff[it] - other._coeff[it] eq 0.0)) _coeff[it] else 0.0) +
                    if (it <= other.degree && !(it<= degree && _coeff[it] - other._coeff[it] eq 0.0)) other._coeff[it] else 0.0
        })

    operator fun plus(value: Double)  =
        Polinom(
            List(degree+1){ _coeff[it] + if (it == 0) value else 0.0}
        )

    operator fun plusAssign(other: Polinom){
        _coeff.indices.forEach {
            _coeff[it] += if (it <= other.degree) other._coeff[it] else 0.0
        }
        for (i in degree+1..other.degree){
            _coeff.add(other._coeff[i])
        }
        removeZeros()
    }

    operator fun plusAssign(value: Double){
        _coeff[0] += value
    }

    operator fun minus(other: Polinom) = this + other * -1.0

    operator fun minus(value: Double) = this + value * -1.0

    operator fun unaryMinus()=
        Polinom(List(degree+1){-_coeff[it]})

    operator fun minusAssign(other: Polinom){
        this.plusAssign(-other)
    }

    operator fun minusAssign(value: Double){
        this.plusAssign(-value)
    }

    operator fun times(value: Double) =
        Polinom(List(degree + 1){_coeff[it] * value})

    operator fun timesAssign(value: Double){
        coeff = _coeff.map{ it * value }
        //_coeff.indices.forEach { _coeff[it] *= value }
        removeZeros()
    }

    operator fun times(other: Polinom): Polinom
    {
        val c = mutableListOf(0.0)
        val p = Polinom(c)
        val t = this.degree
        val y = other.degree+1
        for(i in 0..t)
        {
            val s = Polinom(List(i+y){if (it < i ) 0.0 else _coeff[i]*other._coeff[it-i]})
            p+=s
        }
        return p
    }

    operator fun timesAssign(other: Polinom)
    {
        coeff = (this * other)._coeff
    }

    operator fun divAssign(value: Double){
        _coeff.indices.forEach { _coeff[it] /= value }
    }

    operator fun div(value: Double)=
        Polinom(List(degree + 1){_coeff[it] / value})

    override fun equals(other: Any?): Boolean {
        if(other === this) return true
        if(other !is Polinom) return false
        if(_coeff.size == other._coeff.size)
        {
            for(i in 0..(_coeff.size-1))
                if(_coeff[i] != other._coeff[i]) return false
            return true
        }
        else return false

        /*
        ...= (other is Polinom) && (_coeff == other._coeff)
        */
    }

    fun diff() =
        Polinom(List(degree){
            _coeff[it+1]*(it+1)})

    override fun hashCode(): Int {
        return _coeff.hashCode()
    }
    /*
        fun meaning(x: Double):Double{
            var f: Double = 0.0
            for(i in 0..(_coeff.size-1))
                f+=_coeff[i]* x.pow(i)
            return f
        }
    */
    operator  fun invoke(x: Double): Double{
        var p =1.0
        return _coeff.reduce{res, coef -> p *= x; res + coef * p}
    }

    override fun toString() =
        coeff.indices.reversed().joinToString(""){ ind ->
            val monStr = StringBuilder()
            val acoeff = abs(coeff[ind])
            if (coeff[ind] neq 0.0){
                if (ind < coeff.size - 1 && coeff[ind] > 0){
                    monStr.append("+")
                } else if (coeff[ind] < 0) monStr.append("-")
                if (acoeff neq 1.0 || ind == 0)
                    if (abs(acoeff - acoeff.toInt().toDouble()) eq 0.0)
                        monStr.append(acoeff.toInt())
                    else monStr.append(acoeff)
                if (ind > 0) {
                    monStr.append("x")
                    if (ind > 1) monStr.append("^$ind")
                }

            } else {
                if (coeff.size == 1) monStr.append("0")
            }
            monStr
        }
}