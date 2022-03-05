package com.example.logtech

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.logtech.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.registerLocation.setOnClickListener {
            val sdf = SimpleDateFormat("dd-M-yyyy-hh-mm-ss")
            val currentDate = sdf.format(Date())
            if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                        )
            ) {
                ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
            } else {
                val location = getLocationByGps()
                Log.d("verbose", "Location: $location")
                val file = File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "${currentDate}.crd")
                val fos = FileOutputStream(file)
                fos.write(location.toString().toByteArray())
                fos.close()
                Toast.makeText(this, "Arquivo salvo", Toast.LENGTH_LONG).show()
                val intent = Intent(this, FilesActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getLocationByGps(): Location? {
        var location: Location? = null
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (isGpsEnabled) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000L,
                    0f,
                    this
                )
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            } else {
                requestPermissions(permissions, ALL_PERMISSIONS)
            }
        }
        return location
    }

    override fun onLocationChanged(location: Location) {

    }

    companion object {
        private const val ALL_PERMISSIONS = 100
        private val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}