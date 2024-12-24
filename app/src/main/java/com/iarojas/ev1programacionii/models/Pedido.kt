package com.iarojas.ev1programacionii.models

class Pedido {
    private val platillos = mutableMapOf<Platillo, Int>()

    fun agregarPlatillo(platillo: Platillo, cantidad: Int) {
        platillos[platillo] = cantidad
    }

    fun calcularSubtotal(): Int {
        return platillos.entries.sumOf { it.key.precio * it.value }
    }

    fun calcularTotalConPropina(): Int {
        val subtotal = calcularSubtotal()
        return (subtotal * 1.1).toInt() // Incrementa un 10%
    }

    fun calcularTotalSinPropina(): Int {
        return calcularSubtotal()
    }
}
