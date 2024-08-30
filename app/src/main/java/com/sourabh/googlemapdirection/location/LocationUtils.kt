package com.sourabh.googlemapdirection.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.tasks.await
import java.util.Locale

class LocationUtils {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    // Function to check if location services are enabled
    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
    }

    // Function to show a dialog box prompting the user to enable location services
    private fun showLocationSettingsDialog(activity: Activity) {
        val builder = android.app.AlertDialog.Builder(activity)
        builder.setTitle("Enable Location")
        builder.setMessage("Your locations setting is set to 'Off'.\nPlease enable location to use this app")
        builder.setPositiveButton("Location Settings") { dialog, _ ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            activity.startActivity(intent)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    // Function to show a rationale dialog for location permissions

    private fun showPermissionRationaleDialog(activity: Activity){
        MaterialAlertDialogBuilder(activity)
            .setTitle("Location Permission Needed")
            .setMessage("This app needs the Location permission, please accept to use location functionality")
            .setPositiveButton("OK") { dialog, _ ->
                ActivityCompat.requestPermissions(
                    activity, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), LOCATION_PERMISSION_REQUEST_CODE
                )
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    suspend fun getCurrentLocation(activity: Activity): String? {

        if (!isLocationEnabled(activity)) {
            showLocationSettingsDialog(activity)
            return null
        }

        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity)

        if (ContextCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            Log.d("LocationUtils", "Location permissions not granted.")

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                showPermissionRationaleDialog(activity)
            } else {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), LOCATION_PERMISSION_REQUEST_CODE
                )
            }

            return null
        }

        //Gives latitude and longitude
        val location = fusedLocationClient.lastLocation.await()
        Log.d("LocationUtils", "Fetched Location: $location")

        return location?.let {
            val address = getAddressFromLocation(it.latitude, it.longitude, activity)
            //Gives location: Sonipat, India
            Log.d("LocationUtils", "Fetched address: $address")
            address
        } ?: run {
            Log.d("LocationUtils", "Location is null.")
            null
        }
    }

    private fun getAddressFromLocation(
        latitude: Double, longitude: Double, context: Context
    ): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        val address = addresses?.get(0)

        //Gives complete address :Fetched Address: Address[addressLines=[0:"X2VR+24J, Sector 12,
        // Sonipat, Haryana 131001, India"],feature=X2VR+24J,admin=Haryana,sub-admin=Rohtak Division
        // ,locality=Sonipat,thoroughfare=null,postalCode=131001,countryCode=IN,countryName=India,
        // hasLatitude=true,latitude=28.9925605,hasLongitude=true,longitude=77.0400975,phone=null,
        // url=null,extras=null]
        Log.d("LocationUtils", "Fetched Address: $address")

        return if (address != null) {
            "${address.locality}, ${address.countryName}"
        } else {
            "Unknown location"
        }
    }
}