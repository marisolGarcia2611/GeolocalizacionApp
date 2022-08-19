package com.example.geolocalizacion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lanzartheah();

    }
    private fun lanzartheah() {
        //Aqui nos creamos el hilo
        val time: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally { //Aqui pase lo que pase pasara a la siguiente pantallla
                    val intent = Intent(this@Splash, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        //Aqui lo ejecuto
        time.start()
    }
}