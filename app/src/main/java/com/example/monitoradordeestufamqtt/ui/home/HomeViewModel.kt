package com.example.monitoradordeestufamqtt.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoradordeestufamqtt.repository.MqttRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MqttRepository(application)

    val messages: LiveData<String>
        get() = repository.messageLiveData

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