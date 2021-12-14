/*
 *
 * PROJECT LICENSE
 *
 * This project was submitted by Jason Jamieson as part of the Android Kotlin Developer Nanodegree At Udacity.
 *
 * As part of Udacity Honor code, your submissions must be of your own work.
 * Submission of this project will cause you to break the Udacity Honor Code
 * and possible suspension of your account.
 *
 * I, Jason Jamieson, the author of the project, allow you to check this code as reference, but if
 * used as submission, it's your responsibility if you are expelled.
 *
 * Copyright (c) 2021 Jason Jamieson
 *
 * Besides the above notice, the following license applies and this license notice
 * must be included in all works derived from this project.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.udacity.project4.locationreminders.savereminder.selectreminderlocation


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnPoiClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSelectLocationBinding
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import org.koin.android.ext.android.inject

class SelectLocationFragment : BaseFragment(), OnMapReadyCallback, OnMapClickListener,
    OnPoiClickListener {

    companion object {
        private const val TAG = "SelectLocationFragment"
    }

    private val runningQOrLater =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    //Use Koin to get the view model of the SaveReminder
    override val _viewModel: SaveReminderViewModel by inject()
    private lateinit var binding: FragmentSelectLocationBinding

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var marker: Marker? = null
    private var lastLocation: Location? = null
    private val startingLocation = LatLng(42.97419826734443, -85.68307958930033)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            enableMyLocation()
        } else {
            Toast.makeText(context, R.string.location_required_error, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_location, container, false)

        binding.viewModel = _viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(true)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.selectLocationMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.selectLocationSaveButton.setOnClickListener {
            if (marker != null)
                _viewModel.navigationCommand.postValue(NavigationCommand.Back)
            else
                Toast.makeText(requireContext(), R.string.err_select_location, Toast.LENGTH_SHORT)
                    .show()
        }

        return binding.root
    }

    private fun onLocationSelected(latLng: LatLng, name: String?) {
        val fromLocation = Geocoder(activity).getFromLocation(latLng.latitude, latLng.longitude, 2)
        _viewModel.reminderSelectedLocationStr.postValue(name ?: fromLocation[0].locality)
        _viewModel.latitude.postValue(latLng.latitude)
        _viewModel.longitude.postValue(latLng.longitude)
        _viewModel.reminderSelectedLocationStr
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onMapClick(latLng: LatLng) {
        // set position of marker to new latLng
        marker?.position = latLng
        // add the marker
        addMarker(latLng)
    }

    override fun onPoiClick(poi: PointOfInterest) {
        // add the marker
        addMarker(poi.latLng, poi.name)

        // add a circle around the poi
        map.addCircle(
            CircleOptions()
                .center(poi.latLng)
                .radius(150.0)
                .strokeWidth(5f)
                .fillColor(R.color.black)
        )

        // show the info window for the poi
        marker?.showInfoWindow()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val zoomLevel = 16f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(startingLocation, zoomLevel))

        map.setOnMapClickListener(this)
        map.setOnPoiClickListener(this)

        setMapStyle(map)
        enableMyLocation()
    }

    private fun addMarker(latLng: LatLng, name: String? = null) {
        // remove marker - clear map to do so
        map.clear()

        // create a marker and add to map
        marker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(name)
        )

        // focus camera and set location in viewmodel
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        onLocationSelected(latLng, name)
    }

    private fun setMapStyle(map: GoogleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    R.raw.map_style
                )
            )

            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    private fun isForegroundPermissionsApproved(): Boolean =
        PackageManager.PERMISSION_GRANTED ==
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

    private fun enableMyLocation() {
        when {
            isForegroundPermissionsApproved() -> {
                map.setMyLocationEnabled(true)
                setLocation()
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setLocation() {
        val location = fusedLocationClient.lastLocation
        location.addOnCompleteListener(requireActivity()) { locationTask ->
            if (locationTask.isSuccessful) {
                lastLocation = locationTask.result

                lastLocation?.let {
                    map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                lastLocation!!.latitude,
                                lastLocation!!.longitude
                            ), 16f
                        )
                    )

                    map.uiSettings.isMyLocationButtonEnabled = true
                } ?: run {
                    map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                startingLocation.latitude,
                                startingLocation.longitude,
                            ), 16f
                        )
                    )

                    map.uiSettings.isMyLocationButtonEnabled = false
                }
            }
        }
    }
}
