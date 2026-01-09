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

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var binding: ActivityMainBinding

    // Variáveis para o GPS
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        // Iniciar GPS
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

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        } else {
            // 1. REQUISITO DO PROFESSOR (Hardware GPS) - Continua aqui!
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 5f, this)

            // 2. O TRUQUE PARA FUNCIONAR NO QUARTO (Network)
            // Se o provider de Rede estiver ligado, pedimos também a ele.
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 5f, this)
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        val lat = location.latitude
        val long = location.longitude
        val source = location.provider

        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(lat, long, 1)

            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]

                // --- DADOS BÁSICOS ---
                val country = address.countryName      // "Portugal"
                val place = address.locality ?: address.subLocality ?: address.adminArea // "Calvaria de Cima"

                // --- DETALHES EXTRA (PODEM SER NULL) ---
                val street = address.thoroughfare      // Ex: "Rua Principal"
                val number = address.subThoroughfare   // Ex: "12A" (Número da porta)
                val postalCode = address.postalCode    // Ex: "2480-000"

                // --- CONSTRUIR A FRASE ---
                // Vamos tentar fazer: "Rua Principal 12A, Calvaria de Cima"
                val fullLocation = StringBuilder()

                if (street != null) {
                    fullLocation.append(street)
                    if (number != null) fullLocation.append(" $number")
                    fullLocation.append(", ")
                }

                fullLocation.append(place)
                // Se quiseres ser muito detalhado, descomenta a linha de baixo:
                // if (postalCode != null) fullLocation.append(" ($postalCode)")

                // Atualiza o Ecrã
                binding.tvHome.text = "📍 $fullLocation"

                // Atualiza o Toast para mostrar o país também
                Toast.makeText(this, "GPS: $place, $country ($source)", Toast.LENGTH_LONG).show()

                locationManager.removeUpdates(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            binding.tvHome.text = "📍 $lat, $long"
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão de GPS Concedida", Toast.LENGTH_SHORT).show()
                getLocation()
            } else {
                Toast.makeText(this, "Permissão Negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
}