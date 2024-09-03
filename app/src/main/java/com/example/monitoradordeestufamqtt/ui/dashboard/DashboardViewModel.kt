package com.example.monitoradordeestufamqtt.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoradordeestufamqtt.MqttHandler
import com.example.monitoradordeestufamqtt.repository.MqttRepository
import org.eclipse.paho.android.service.MqttAndroidClient

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MqttRepository(application)

    val messages: LiveData<String>
        get() = repository.valueLuz

    fun connect() {
        repository.connect()
    }

    fun subscribe(topic: String) {
        repository.subscribe(topic)
    }

    fun disconnect() {
        repository.disconnect()
    }
}