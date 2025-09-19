package com.example.swimpool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.swimpool.data.SwimmingPoolRepository
import com.example.swimpool.databinding.ActivityMainBinding
import com.example.swimpool.model.SwimmingPool
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.MarkerIcons

class MainActivity : AppCompatActivity(), Overlay.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView
    private var naverMap: NaverMap? = null
    private val swimmingPools = SwimmingPoolRepository.getGuroPools()
    private var selectedMarker: Marker? = null
    private val infoWindow = InfoWindow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(getString(R.string.naver_map_client_id))

        mapView = binding.naverMapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            naverMap = map
            configureMap(map)
            placeSwimmingPoolMarkers(map)
        }

        binding.resetCameraButton.setOnClickListener {
            focusOnDefaultArea()
        }

        binding.poolInfoCard.isVisible = false
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                val pool = infoWindow.tag as? SwimmingPool ?: return ""
                return pool.name
            }
        }
    }

    private fun configureMap(map: NaverMap) {
        val center = LatLng(37.4935, 126.8635)
        map.cameraPosition = CameraPosition(center, 12.5)

        map.uiSettings.apply {
            isLocationButtonEnabled = false
            isZoomControlEnabled = false
            isScaleBarEnabled = false
        }
    }

    private fun placeSwimmingPoolMarkers(map: NaverMap) {
        swimmingPools.forEach { pool ->
            Marker().apply {
                position = LatLng(pool.latitude, pool.longitude)
                captionText = pool.name
                captionRequestedWidth = 200
                subCaptionText = pool.address
                map = map
                icon = MarkerIcons.BLUE
                tag = pool
                onClickListener = this@MainActivity
            }
        }
    }

    override fun onClick(overlay: Overlay): Boolean {
        val marker = overlay as? Marker ?: return false
        val pool = marker.tag as? SwimmingPool ?: return false
        naverMap?.let { map ->
            infoWindow.tag = pool
            infoWindow.open(marker)
            updateSelectedMarker(marker)
            updateInfoCard(pool)
            map.moveCamera(CameraUpdate.scrollTo(marker.position))
            map.moveCamera(CameraUpdate.zoomTo(14.0))
        }
        return true
    }

    private fun updateSelectedMarker(marker: Marker) {
        selectedMarker?.icon = MarkerIcons.BLUE
        marker.icon = MarkerIcons.YELLOW
        selectedMarker = marker
    }

    private fun updateInfoCard(pool: SwimmingPool) {
        binding.poolInfoCard.isVisible = true
        binding.poolNameTextView.text = pool.name
        binding.poolAddressTextView.text = pool.address
        binding.poolDescriptionTextView.text =
            pool.description ?: getString(R.string.info_description_not_available)
        binding.poolPhoneTextView.text = pool.phoneNumber ?: getString(R.string.info_phone_not_available)
    }

    private fun focusOnDefaultArea() {
        val map = naverMap ?: return
        val defaultCenter = LatLng(37.4935, 126.8635)
        map.moveCamera(CameraUpdate.scrollTo(defaultCenter))
        map.moveCamera(CameraUpdate.zoomTo(12.5))
        binding.poolInfoCard.isVisible = false
        infoWindow.close()
        selectedMarker?.icon = MarkerIcons.BLUE
        selectedMarker = null
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
