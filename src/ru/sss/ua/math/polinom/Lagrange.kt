package ru.sss.ua.math.polinom

import ru.sss.math.neq

class Lagrange(private val points: Map<Double, Double>) : Polinom() {

    init {
        val lagr = Polinom()
        points.keys.forEach{ lagr += fundamental(it) * points[it]!!}
        coeff = lagr.coeff
    }



    private fun fundamental(x: Double)=Polinom(1.0).apply {
        points.keys.forEach { if (x neq it) this *= Polinom(listOf(-it, 1.0)) / (x - it) }
    }
}