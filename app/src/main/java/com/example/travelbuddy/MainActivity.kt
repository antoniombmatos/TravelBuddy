package com.example.travelbuddy

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.travelbuddy.databinding.ActivityMainBinding
import com.example.travelbuddy.ui.badges.BadgesActivity
import com.example.travelbuddy.ui.countries.CountriesActivity
import com.example.travelbuddy.ui.settings.SettingsActivity
import java.util.Locale

// 1. Adicionamos a interface LocationListener
class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var binding: ActivityMainBinding

    // 2. Variáveis para o GPS (Vêm do PDF '5. Sensores.pdf', pág. 6)
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        // 3. Chamamos a função para iniciar o GPS logo que a app abre
        getLocation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_countries -> {
                    startActivity(Intent(this, CountriesActivity::class.java))
                    true
                }
                R.id.nav_badges -> {
                    startActivity(Intent(this, BadgesActivity::class.java))
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    // 4. Função para pedir permissão e iniciar updates (Baseado no PDF '5. Sensores.pdf', pág. 7)
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Verifica se já temos permissão
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            // Se não, pede permissão ao utilizador
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        } else {
            // Se sim, pede atualizações de GPS (Min: 5 segundos, 5 metros)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 5f, this)
        }
    }

    // 5. O que acontece quando o GPS deteta mudança (Baseado no PDF '5. Sensores.pdf', pág. 6)
    override fun onLocationChanged(location: Location) {
        // Temos as coordenadas!
        val lat = location.latitude
        val long = location.longitude

        // Extra: Converter números em Nome do País (Geocoder)
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(lat, long, 1)

            if (addresses != null && addresses.isNotEmpty()) {
                val country = addresses[0].countryName // Ex: "Portugal"

                // Mostra um aviso ao utilizador
                Toast.makeText(this, "Bem-vindo a $country!", Toast.LENGTH_LONG).show()

                // Opcional: Atualizar um TextView na tua Home se tiveres
                // binding.tvLocation.text = "Estás em: $country"

                // Para poupar bateria, paramos de pedir localização depois de encontrar a primeira vez
                locationManager.removeUpdates(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 6. Tratar a resposta do utilizador ao pedido de permissão (Baseado no PDF '5. Sensores.pdf', pág. 6)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão de GPS Concedida", Toast.LENGTH_SHORT).show()
                getLocation() // Tenta ligar de novo agora que já deixaram
            } else {
                Toast.makeText(this, "Permissão Negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Métodos obrigatórios da interface LocationListener (podem ficar vazios)
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
}