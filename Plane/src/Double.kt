package ru.sss.math

import kotlin.math.abs
import kotlin.math.max

infix fun Double.eq(other: Double) =
    abs(this - other) < max(if (this != 0.0 ) Math.ulp(this) else Math.ulp(1.0), if (other!=0.0) Math.ulp(other) else Math.ulp(1.0)) * 2
infix fun Double.neq(other: Double) =
    abs(this - other) > max(if (this != 0.0 ) Math.ulp(this) else Math.ulp(1.0), if (other!=0.0) Math.ulp(other) else Math.ulp(1.0)) * 2
infix fun Double.ge(other: Double) =
    this > other || this.eq(other)
infix fun Double.le(other: Double) =
    this < other || this.eq(other)