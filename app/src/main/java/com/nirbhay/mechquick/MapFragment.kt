package com.nirbhay.mechquick

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.nirbhay.mechquick.databinding.FragmentMapBinding


class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(mapplsMap: MapplsMap) {

        binding.mapView

        fusedLocationClient.lastLocation.addOnCompleteListener { task: Task<Location> ->
                if (task.isSuccessful && task.result != null) {
                    val location = task.result
                    val latitude = location.latitude
                    val longitude = location.longitude

//                    Toast.makeText(requireContext(), "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_SHORT).show()

                    val point = LatLng(latitude, longitude)
                    val cameraPosition = CameraPosition.Builder()
                        .target(point)
                        .zoom(10.0)
                        .tilt(0.0)
                        .build()
                    mapplsMap.cameraPosition = cameraPosition

                    val markerOptions: MarkerOptions = MarkerOptions().position(point)
                    mapplsMap.addMarker(markerOptions)

                } else {
                    Toast.makeText(requireContext(), "Failed to get current location.", Toast.LENGTH_SHORT).show()
                }
            }


    }

    override fun onMapError(p0: Int, p1: String?) {
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

}