package com.example.geolocalizacion

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.android.volley.VolleyError

import org.json.JSONException

import com.android.volley.RequestQueue





class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    public var latitud:Double?=null
    public var longitud:Double?=null

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    @SuppressLint("RestrictedApi")
    fun createMarker() {
        var queue = Volley.newRequestQueue(this)
        val url = "http://192.168.137.1:8000/api/show"
        //RECIBIR UN JSON OBJECT

        val stringRequest = StringRequest(Request.Method.GET,url, { response ->
            val jsonArray = JSONArray(response)
            var j = 0
            while (j<jsonArray.length()){
                val jsonObject = JSONObject(jsonArray.getString(j))
                var text1 = jsonObject.get("latitud")
                var text2 = jsonObject.get("longitud")
                var latitud1 = text1.toString()
                var longitud2 = text2.toString()
                Log.i(LOG_TAG,"_______________________Response is: $response------------------------")
                Toast.makeText(this, "Latitud: $latitud1 Longutud: $longitud2",Toast.LENGTH_SHORT).show()
                var latitudd = latitud1.toDouble()
                var longitudd = longitud2.toDouble()
                Toast.makeText(this, "Latitud: $latitudd Longutud: $longitudd",Toast.LENGTH_SHORT).show()
                var favoritePlace = LatLng(latitudd,longitudd)
                map.addMarker(MarkerOptions().position(favoritePlace).title("Mi playa favorita!"))
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(favoritePlace, 8f),
                    4000,
                    null
                )
                // createMarker(latitud,longitud)
              j++
            }
        }, { error ->
            Log.i(LOG_TAG,"_______________________Error detectado------------------------")
            error.printStackTrace()
        }
        );
        queue.add(stringRequest)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createMapFragment()
    }
    private fun createMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    }

