package ru.sss.ua.math.polinom

class Newton(private var points: Map<Double, Double>) : Polinom() {

    private val razn = mutableMapOf<List<Double>, Double>()

    init {
        val k = points.keys.toList()
        val p = Polinom(points[k[0]]!!)
        for(i in 1..(points.size-1))
        {
            val m = mutableMapOf(k[0] to points[k[0]]!!)
            val p1 = Polinom(1.0)
            for(j in 0..(i-1))
            {
                p1*=Polinom(listOf<Double>(-k[j],1.0))
                m[k[j+1]] = points[k[j+1]]!!
            }
            p+=p1*raz(m)
        }
        coeff = p.coeff
    }

    private fun raz(map: Map<Double, Double>): Double
    {
        var f = 0.0
        var r = 0.0
        if (razn[map.keys.toList()] is Double) return razn[map.keys.toList()]!!
        else {
            if (map.size == 2) {
                var i = 0
                map.keys.forEach { f += map[it]!! * (if (i != 0) 1 else -1); r += it * (if (i != 0) 1 else -1);i++ }
                f /= r
            } else {
                var i = 0
                val e1 = mutableMapOf<Double, Double>()
                val e2 = mutableMapOf<Double, Double>()
                map.keys.forEach {
                    if (i != 0) e1[it] = map[it]!! else r -= it; if (i != map.size - 1) e2[it] =
                    map[it]!! else r += it;i++
                }
                val s1 = raz(e1.toMap())
                val s2 = raz(e2.toMap())
                f = s1 - s2
                f /= r
            }
            razn[map.keys.toList()] = f
            return f
        }
    }

    fun add(tochi: Map<Double, Double>)
    {
        val k1 = points.keys.toList()
        val r =tochi.keys.toList()
        val t= mutableMapOf(k1[0] to points[k1[0]]!!)
        for (i in 1..(r.size + k1.size - 1))
        {
            t[if(i<k1.size) k1[i] else r[i - k1.size]] = if(i<k1.size) points[k1[i]]!! else tochi[r[i - k1.size]]!!
        }
        val p = Polinom(0.0)
        points = t.toMap()
        val k = points.keys.toList()

        for(i in (points.size - tochi.size)..(points.size - 1))
        {
            val m = mutableMapOf(k[0] to points[k[0]]!!)
            val p1 = Polinom(1.0)
            for(j in 0..(i-1))
            {
                p1*=Polinom(listOf<Double>(-k[j],1.0))
                m[k[j+1]] = points[k[j+1]]!!
            }
            p+=p1*raz(m)
        }
        this+=p
    }
}