package com.iarojas.ev1programacionii

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.iarojas.ev1programacionii.models.Pedido
import com.iarojas.ev1programacionii.models.Platillo
import java.text.NumberFormat
import java.util.Currency

class MainActivity : AppCompatActivity() {

    private lateinit var pedido: Pedido
    private lateinit var pastelChoclo: Platillo
    private lateinit var cazuela: Platillo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar los platillos
        pastelChoclo = Platillo("Pastel de Choclo", 12000)
        cazuela = Platillo("Cazuela", 10000)
        pedido = Pedido()

        // Vincular vistas del diseño
        val etCantidadPastel = findViewById<EditText>(R.id.etCantidadPastel)
        val etCantidadCazuela = findViewById<EditText>(R.id.etCantidadCazuela)
        val swPropina = findViewById<Switch>(R.id.swPropina)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)

        // Escuchar cambios en las cantidades de los EditText
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                actualizarTotales(etCantidadPastel, etCantidadCazuela, swPropina, tvTotal)
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        etCantidadPastel.addTextChangedListener(textWatcher)
        etCantidadCazuela.addTextChangedListener(textWatcher)

        // Escuchar cambios en el Switch de propina
        swPropina.setOnCheckedChangeListener { _, _ ->
            actualizarTotales(etCantidadPastel, etCantidadCazuela, swPropina, tvTotal)
        }
    }

    private fun actualizarTotales(
        etCantidadPastel: EditText,
        etCantidadCazuela: EditText,
        swPropina: Switch,
        tvTotal: TextView
    ) {
        // Obtener las cantidades ingresadas
        val cantidadPastel = etCantidadPastel.text.toString().toIntOrNull() ?: 0
        val cantidadCazuela = etCantidadCazuela.text.toString().toIntOrNull() ?: 0

        // Actualizar el pedido
        pedido.agregarPlatillo(pastelChoclo, cantidadPastel)
        pedido.agregarPlatillo(cazuela, cantidadCazuela)

        // Calcular el total según el estado del Switch
        val total = if (swPropina.isChecked) {
            pedido.calcularTotalConPropina()
        } else {
            pedido.calcularTotalSinPropina()
        }

        // Formatear el total en pesos chilenos
        val formatoMoneda = NumberFormat.getCurrencyInstance()
        formatoMoneda.maximumFractionDigits = 0 // Sin decimales
        formatoMoneda.currency = Currency.getInstance("CLP") // Pesos chilenos

        // Actualizar el TextView con el total
        tvTotal.text = "Total: ${formatoMoneda.format(total)}"
    }
}
