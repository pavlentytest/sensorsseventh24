package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var lightText: TextView
    private lateinit var pressureText: TextView
    private lateinit var imageView: ImageView
    var lightSensor: Sensor? = null
    var pressureSensor: Sensor? = null
    var rotateSensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val list: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        println("Size=${list.size}")
        println(list.joinToString("\n"))

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if(lightSensor != null) {
            sensorManager.registerListener(this,lightSensor,2)
        }
        lightText = findViewById(R.id.lightText)

        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if(pressureSensor != null) {
            sensorManager.registerListener(this,pressureSensor,2)
        }
        pressureText = findViewById(R.id.pressureText)

        rotateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if(rotateSensor != null) {
            sensorManager.registerListener(this,rotateSensor,2)
        }
        imageView = findViewById(R.id.imageView)

    }

    override fun onSensorChanged(event: SensorEvent) {
        if(lightSensor == null) {
            lightText.text = "No light sensor!"
        } else if(event.sensor.type == Sensor.TYPE_LIGHT) {
            lightText.text = "Light: ${event.values[0]} Lux"
        }
        if(pressureSensor == null) {
            pressureText.text = "No pressure sensor!"
        } else if(event.sensor.type == Sensor.TYPE_PRESSURE) {
            pressureText.text = "Pressure: ${event.values[0]} hPa"
        }

        if(event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            imageView.rotationX = event.values[0]*10
            imageView.rotationY = event.values[1]*10
            imageView.rotation = event.values[2]*10
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}